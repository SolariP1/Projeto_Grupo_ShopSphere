# Justificativas das Notas da Matriz — ShopSphere (Aula 06)

## Objetivo

Registrar a justificativa de cada nota atribuída na matriz de decisão arquitetural do ShopSphere (arquivo `MATRIZ-DECISAO.md`).

Alternativas avaliadas:
- **Alternativa A** — manter a arquitetura atual (camadas simples).
- **Alternativa B** — arquitetura orientada a eventos (message broker).
- **Alternativa C** — microsserviços por domínio (catálogo/estoque, pedido, pagamento, frete).

---

## Tabela de justificativas

| Critério | Alternativa | Nota | Justificativa | Evidência no projeto |
|---|---|---:|---|---|
| Desempenho | Alternativa A | 5 | Todas as chamadas hoje são locais e síncronas, sem I/O de rede real, o que mantém a latência baixa. | `ShopSphereService.checkout(...)` chama diretamente `DiscountService`, `payment` (`CardGatewayLegacy`), `freight` (`FreightLegacyApi`) e `mail` (`MarketplaceMailApi`), todos locais. |
| Desempenho | Alternativa B | 3 | Mensageria assíncrona desacoplaria picos de carga, mas introduziria latência de publish/consume e overhead de serialização que hoje não existem. | Nenhum broker está implementado no projeto; não há dependência de mensageria declarada no `pom.xml`. |
| Desempenho | Alternativa C | 2 | Comunicação entre serviços via rede aumentaria a latência e os pontos de falha de cada chamada (ex.: checkout dependeria de rede até o serviço de pagamento e de frete). | Hoje `PaymentAdapter`/`FreightAdapter` chamam classes locais (`CardGatewayLegacy`, `FreightLegacyApi`) sem rede; em microsserviços essas chamadas passariam a ser remotas. |
| Segurança | Alternativa A | 3 | A arquitetura em camadas não impede aplicar TLS e mascarar dados sensíveis nos logs, mas isso ainda não foi implementado, e o adapter de pagamento herda a API legada em vez de encapsulá-la. | `MarketplaceMailApi.send(to, text)` imprime destinatário e texto em texto plano via `System.out`; `PaymentAdapter extends CardGatewayLegacy` expõe o método legado por herança. |
| Segurança | Alternativa B | 3 | Um broker permitiria isolar credenciais e usar TLS no canal de mensageria, mas adiciona uma nova superfície de ataque (broker, tópicos, controle de acesso a filas) que o projeto não gerencia hoje. | Não há nenhuma configuração de segurança para mensageria no projeto (sem broker declarado no `pom.xml`). |
| Segurança | Alternativa C | 3 | Fronteiras de serviço permitiriam controle de acesso por serviço (ex.: só o serviço de pagamento acessa `CardGatewayLegacy`), mas exigiria gestão de segredos e autenticação entre serviços, inexistente hoje. | `PaymentAdapter` e `FreightAdapter` rodam hoje no mesmo processo, sem fronteira de rede a proteger. |
| Manutenibilidade | Alternativa A | 2 | O código está dividido em pacotes (`model`, `service`, `repository`, `patterns`), mas `ShopSphereService` concentra pedido, pagamento, frete, desconto e notificação em uma única classe, com dependências instanciadas diretamente. | `ShopSphereService` declara `payment`, `freight`, `mail`, `discounts` e `publisher` com `new` no corpo da classe (não injetados); `PaymentAdapter extends CardGatewayLegacy` (herança em vez de composição). |
| Manutenibilidade | Alternativa B | 3 | Eventos tendem a desacoplar produtores e consumidores a longo prazo, mas exigiriam reescrever `OrderPublisher`/observers como contratos de evento estruturados e mover a lógica de `checkout()` para consumidores assíncronos. | `OrderObserver.update(String id, String event)` usa apenas *strings* soltas, sem payload estruturado — teria que ser redesenhado para eventos reais. |
| Manutenibilidade | Alternativa C | 2 | Múltiplos serviços/repositórios aumentariam o esforço de manutenção para uma equipe pequena e recém-chegada ao legado. | O projeto é hoje um único módulo (pacote `br.edu.shopsphere`), sem separação de deploys; dividir em microsserviços multiplicaria esse esforço. |
| Disponibilidade/Confiabilidade | Alternativa A | 3 | O `OrderPublisher` já notifica corretamente todos os observers cadastrados (ponto positivo), mas `checkout()` não trata falha de pagamento/frete e sempre notifica um e-mail fixo em vez do cliente real. | `OrderPublisher.publish(...)` itera sobre `List<OrderObserver> observers`, notificando `CustomerObserver` e `SellerObserver` corretamente; porém `checkout()` chama `mail.send("cliente@exemplo.com", ...)` em vez de `o.customer`, e não há `try/catch` em torno de `payment.cobrar(...)`/`freight.cotar(...)`. |
| Disponibilidade/Confiabilidade | Alternativa B | 4 | Uma fila permitiria reentrega em caso de falha e desacoplaria a falha de um consumidor (ex.: envio de e-mail) do fluxo de negócio principal. | Não há implementação hoje; é o comportamento característico de sistemas de mensageria com fila e reentrega. |
| Disponibilidade/Confiabilidade | Alternativa C | 3 | O isolamento de falha por serviço ajudaria, mas exigiria mecanismos como *circuit breaker* e orquestração, que o projeto não possui. | Nenhuma implementação de tolerância a falha existe hoje; `PaymentAdapter`/`FreightAdapter` não tratam exceção nem timeout. |
| Complexidade operacional | Alternativa A | 5 | O sistema roda com um único comando de build e execução, sem infraestrutura adicional. | `README.md`: `javac -d out $(find src/main/java -name "*.java")` seguido de `java -cp out br.edu.shopsphere.Main`. |
| Complexidade operacional | Alternativa B | 2 | Exigiria broker de mensagens, monitoramento de filas/tópicos e configuração adicional de infraestrutura, hoje inexistentes. | Nenhuma infraestrutura de mensageria consta no projeto. |
| Complexidade operacional | Alternativa C | 2 | Múltiplos serviços implicariam múltiplos deploys, containers e descoberta de serviço. | O projeto é hoje um único pacote Java, sem qualquer configuração de orquestração/contêineres. |
| Custo/esforço de migração | Alternativa A | 5 | Não há custo de migração: é a arquitetura já existente e funcional, alvo de evolução incremental desde `docs/adr/ADR-0001-arquitetura.md`. | `docs/adr/ADR-0001-arquitetura.md` já registra a decisão de refatorar aos poucos, começando por `ShopSphereService`, em vez de reescrever tudo de uma vez. |
| Custo/esforço de migração | Alternativa B | 2 | Exigiria reescrever `OrderPublisher`/observers como produtores/consumidores de eventos e introduzir um broker do zero. | `OrderPublisher.java` hoje não tem nenhuma abstração de fila/tópico — a mudança partiria do zero. |
| Custo/esforço de migração | Alternativa C | 2 | Exigiria separar `model`/`service`/`repository` por domínio e criar contratos de API entre os novos serviços. | Hoje `Product`, `Order` e `Seller` compartilham o mesmo módulo e o mesmo `ProductRepository`. |

---

## Modelo individual de justificativa (exemplo)

### Critério

Disponibilidade/Confiabilidade

### Alternativa

Alternativa A — manter a arquitetura atual (camadas simples)

### Nota atribuída

3

### Justificativa

O padrão Observer está corretamente implementado (`OrderPublisher` guarda uma lista de observers e notifica todos), então esse ponto não é um risco hoje. No entanto, o método `checkout()` de `ShopSphereService` sempre envia a notificação de resultado do pedido para um e-mail fixo (`"cliente@exemplo.com"`) em vez do e-mail do cliente real (`o.customer`), e nenhuma chamada ao gateway de pagamento ou à API de frete está protegida por tratamento de exceção — uma falha nessas integrações interromperia o fluxo sem registro claro do motivo.

### Evidência utilizada

`service/ShopSphereService.java`, método `checkout(String id)`:
```java
mail.send(
    "cliente@exemplo.com",
    "Pedido " + id + " status " + o.status
);
```
Nenhum `try/catch` envolve `payment.cobrar(...)` ou `freight.cotar(...)` no mesmo método.

---

## O que foi considerado evidência

Foram utilizadas, ao longo da tabela acima:

- classes e métodos específicos (ex.: `ShopSphereService.checkout(...)`, `OrderPublisher.publish(...)`);
- pacotes (ex.: `patterns/adapter`, `patterns/observer`);
- documentação do próprio projeto (`README.md`, `docs/adr/ADR-0001-arquitetura.md`, `docs/adr/ADR-0002-encapsulamento.md`, `docs/arquitetura_inicial.md`);
- ausência de implementação (ex.: nenhuma dependência de broker declarada) como evidência de que um requisito daquela alternativa ainda não existe;
- comportamento observável do código-fonte atual (ex.: e-mail fixo em `checkout()`, herança em `PaymentAdapter`).

## Observação sobre o "Não fazer"

As justificativas acima evitam frases genéricas como "é mais seguro" ou "é mais moderno" isoladas: cada nota está amarrada a uma classe, método, arquivo de configuração ou comportamento observável do próprio ShopSphere, seguindo a orientação de `atividades/README-Aula-06.md` ("Não copiar nomes de classes de outro projeto apenas para parecer igual ao exemplo").
