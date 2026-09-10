# ADR-0001 — Arquitetura do ShopSphere

## Status
Aceito

## Contexto
O ShopSphere é um marketplace legado: diferentes vendedores cadastram produtos, o cliente monta um pedido, paga e recebe os produtos por serviços de entrega. O código herdado **compila e executa**, mas contém decisões propositalmente incompletas ou inadequadas (`docs/arquitetura_inicial.md`), que a equipe deve evoluir gradualmente.

O escopo inicial cobre produtos e vendedores, estoque simplificado, carrinho/pedido, descontos, pagamento, cálculo/contratação de frete e notificações (`README.md`).

Os requisitos levantados até o momento (`docs/requisitos_iniciais.md`), sem critérios de aceite formais, são:
- RF01 — o sistema deve permitir montar um pedido adicionando produtos com reserva de estoque.
- RF02 — o sistema deve permitir finalizar a compra (checkout), aplicando desconto, cobrando o pagamento, cotando o frete e notificando o cliente.
- RNF01 — o pagamento deve ser seguro.
- RNF02 — o frete deve ser rápido.
- RNF03 — o estoque deve estar correto.
- RNF04 — o cliente deve receber notificações.
- RNF05 — o sistema deve ser fácil de manter e de integrar a novas transportadoras.

A equipe é nova no projeto (`docs/adr/ADR-0001-arquitetura.md`), o prazo é de um semestre e não há hoje infraestrutura de mensageria, orquestração de containers ou múltiplos ambientes de deploy — apesar de a equipe anterior ter declarado (sem evidência) que o sistema seria um "monólito modular preparado para microsserviços" (`docs/arquitetura_inicial.md`).

## Alternativas consideradas
1. **Arquitetura em camadas simples, processo único** — fachada de acesso, serviço de aplicação orquestrando repositório e integrações, sem infraestrutura adicional. É a arquitetura já existente no legado; simples de entender e evoluir aos poucos, mas hoje concentra responsabilidades demais em `ShopSphereService`.
2. **Arquitetura orientada a eventos (message broker)** — desacoplaria pagamento, frete e notificação via publicação/assinatura de eventos de pedido. Adequada para cenários de alto volume e múltiplos consumidores, mas exige infraestrutura de mensageria e conhecimento que a equipe ainda não tem, para um volume de uso que ainda não foi dimensionado.
3. **Microsserviços por domínio** — isolaria catálogo/estoque, pedido, pagamento e frete em serviços independentes. Favorece escala e times múltiplos, mas é desproporcional ao tamanho da equipe e ao estágio atual do projeto (legado ainda em entendimento, escopo em refino).

## Decisão
Manter e evoluir a arquitetura em camadas simples, em processo único, já existente no ShopSphere:
- uma fachada (`ShopSphereFacade`) concentra o ponto de acesso externo ao sistema;
- um serviço de aplicação (`ShopSphereService`) orquestra criação de pedido, adição de itens, cálculo de desconto, pagamento, frete e notificação;
- um repositório dedicado (`ProductRepository`) guarda os produtos;
- fábricas (`OrderFactory`, `CommerceFamilyFactory`) encapsulam a criação de pedidos e de integrações com parceiros comerciais;
- uma estratégia de desconto (`DiscountService` + `DiscountStrategy`) calcula o desconto do pedido de forma substituível;
- um mecanismo observador (`OrderPublisher`/`OrderObserver`, com `CustomerObserver` e `SellerObserver`) permite reagir a eventos de pedido (criação, checkout) sem acoplar notificação ao serviço principal;
- adaptadores (`PaymentAdapter`, `FreightAdapter`) isolam a comunicação com o gateway de pagamento e a transportadora legados, para que a integração possa evoluir sem alterar o serviço de aplicação.

Conforme já registrado em `docs/adr/ADR-0001-arquitetura.md`, a evolução será incremental: o primeiro passo é reduzir o acoplamento de `ShopSphereService`, que hoje instancia `CardGatewayLegacy`, `FreightLegacyApi` e `MarketplaceMailApi` diretamente com `new` no corpo da classe, em vez de recebê-los por injeção de dependência.

## Consequências

### Positivas
- Entrega incremental do escopo, sem exigir infraestrutura de mensageria ou orquestração de serviços.
- Fachada e serviço de aplicação concentram as regras de negócio em um único lugar, facilitando o entendimento do legado por uma equipe que ainda está aprendendo o domínio.
- Adapters, factories e a estratégia de desconto isolam pontos de variação conhecidos (gateway de pagamento, transportadora, regra de desconto), preparando o sistema para evoluir sem reescrever o núcleo.

### Negativas / trade-offs
- `ShopSphereService` ainda concentra pedido, pagamento, frete, desconto e notificação em uma única classe, com dependências instanciadas diretamente (`new`) em vez de injetadas — dificulta testar `checkout()` isoladamente.
- RNF01 (segurança), RNF03 (integridade de estoque) e RNF05 (manutenibilidade) ainda não têm critérios de aceite, métricas ou forma de validação definidos.
- Se o volume de pedidos ou o número de vendedores/transportadoras crescer significativamente, esta decisão deverá ser revisitada.

## Evidências relacionadas
- **Requisito(s):** RF01–RF02, RNF01–RNF05 (`docs/requisitos_iniciais.md`).
- **Classe/pacote/componente:** `patterns/facade/ShopSphereFacade.java`, `service/ShopSphereService.java`, `patterns/factory/OrderFactory.java`, `patterns/abstractfactory/CommerceFamilyFactory.java`, `patterns/strategy/DiscountService.java`, `patterns/observer/OrderPublisher.java`, `patterns/adapter/PaymentAdapter.java`, `patterns/adapter/FreightAdapter.java`.
- **ADRs relacionados:** `docs/adr/ADR-0001-arquitetura.md` (decisão de refatorar `ShopSphereService` aos poucos), `docs/adr/ADR-0002-encapsulamento.md` (encapsulamento de `stock` em `Product` e correção de `addProduct`).
- **Issue/PR:** [Preencher: link da issue/branch/PR usada para registrar esta decisão no GitHub]
