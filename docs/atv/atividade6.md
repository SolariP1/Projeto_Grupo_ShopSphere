# Aula 06 — Estilos arquiteturais, requisitos e trade-offs

## Objetivo

Comparar estilos arquiteturais aplicáveis ao ShopSphere e justificar a manutenção ou evolução da arquitetura atual considerando requisitos funcionais, requisitos não funcionais e trade-offs.

## 1. Situação identificada

O ShopSphere é um marketplace que possui funcionalidades relacionadas ao cadastro e gerenciamento de produtos, montagem de pedidos, descontos, pagamento, frete e notificações.

A arquitetura atual funciona como um monólito em processo único, organizado em camadas, com componentes responsáveis por diferentes partes do sistema.

Foi identificada a necessidade de avaliar se essa arquitetura deveria ser mantida ou se seria adequado evoluir para uma arquitetura baseada em eventos ou microsserviços.

## 2. Problema / necessidade

A equipe precisava avaliar possíveis caminhos arquiteturais para a evolução do ShopSphere sem introduzir complexidade desnecessária.

A decisão deveria considerar os requisitos do sistema, principalmente segurança, desempenho, manutenibilidade, confiabilidade, complexidade operacional e custo de mudança.

## 3. Requisitos funcionais

### RF01 — Montagem do pedido

O sistema deve permitir que o cliente monte um pedido adicionando produtos e realizando a reserva de estoque.

### RF02 — Finalização da compra

O sistema deve permitir finalizar a compra, aplicando desconto, realizando a cobrança do pagamento, calculando/contratando o frete e notificando o cliente.

## 4. Requisitos não funcionais

### RNF01 — Segurança do pagamento

O processamento do pagamento deve ser realizado de forma segura, isolando a comunicação com o gateway de pagamento.

### RNF02 — Desempenho do frete

A operação relacionada ao frete deve apresentar tempo de resposta adequado para não prejudicar a finalização da compra.

### RNF03 — Integridade do estoque

O sistema deve manter o estoque consistente durante a adição e reserva de produtos.

### RNF04 — Notificações

O sistema deve permitir que o cliente receba notificações relacionadas às operações do pedido.

### RNF05 — Manutenibilidade e integração

A arquitetura deve facilitar a manutenção do sistema e permitir a evolução das integrações, especialmente com diferentes transportadoras.

## 5. Estilos arquiteturais analisados

### A — Arquitetura em camadas, processo único

A primeira alternativa é manter a arquitetura atual.

O fluxo principal permanece organizado entre fachada, serviço de aplicação, repositórios e adapters.

```text
Main
  ↓
ShopSphereFacade
  ↓
ShopSphereService
  ↓
Repository / Adapters
```

**Benefícios:**

* estrutura simples de compreender;
* baixo custo de mudança;
* não exige infraestrutura adicional;
* aproveita o código existente;
* facilita a evolução incremental do projeto.

**Custos e riscos:**

* o `ShopSphereService` ainda concentra várias responsabilidades;
* o sistema continua em um único processo;
* o crescimento do sistema pode aumentar o acoplamento.

### B — Arquitetura orientada a eventos

A segunda alternativa seria evoluir o mecanismo de `OrderPublisher` e `OrderObserver` para utilizar eventos como forma de comunicação entre partes do sistema.

Um pedido poderia gerar eventos que seriam consumidos por diferentes componentes.

**Benefícios:**

* maior desacoplamento entre consumidores;
* facilidade para adicionar novos consumidores de eventos;
* possibilidade de melhorar a independência entre algumas operações;
* aproveitamento do mecanismo de eventos interno já existente.

**Custos e riscos:**

* maior complexidade de entendimento e depuração;
* necessidade de controlar eventos e suas dependências;
* utilização de infraestrutura de mensageria seria necessária caso a solução evoluísse para comunicação distribuída;
* o custo de mudança é maior que o da manutenção da arquitetura atual.

### C — Microsserviços por domínio

A terceira alternativa seria separar o sistema em serviços independentes.

Uma possível divisão seria:

* catálogo/estoque;
* pedidos;
* pagamento;
* frete.

Os serviços se comunicariam por APIs.

**Benefícios:**

* independência entre os domínios;
* possibilidade de escalar serviços individualmente;
* maior isolamento entre responsabilidades;
* possibilidade de evolução independente dos serviços.

**Custos e riscos:**

* maior complexidade operacional;
* necessidade de comunicação entre serviços;
* maior custo de desenvolvimento e manutenção;
* necessidade de infraestrutura adicional;
* maior esforço para uma equipe que ainda está compreendendo o sistema legado.

## 6. Matriz de decisão

Foram considerados os seguintes critérios:

* segurança;
* complexidade operacional;
* custo de mudança;
* manutenibilidade;
* confiabilidade;
* desempenho.

Os critérios receberam pesos de 1 a 3, de acordo com sua importância para o projeto. Cada alternativa recebeu notas de 1 a 5.

| Alternativa                | Pontuação |
| -------------------------- | --------: |
| A — Arquitetura em camadas |    **54** |
| B — Eventos internos       |    **47** |
| C — Microsserviços         |    **27** |

### Resultado

A alternativa A apresentou a maior pontuação, seguida pela alternativa B e pela alternativa C.

A diferença entre A e B é relativamente pequena. A alternativa B apresenta benefícios principalmente relacionados à manutenibilidade e confiabilidade, porém aumenta a complexidade operacional e o custo de mudança.

A alternativa C apresenta uma mudança estrutural maior e, considerando o estágio atual do projeto, possui custos e complexidade superiores às necessidades identificadas.

## 7. Decisão

A equipe decidiu **manter e evoluir a arquitetura em camadas, em processo único**.

A decisão considera que a arquitetura atual já atende às necessidades identificadas e permite evolução incremental sem exigir infraestrutura adicional.

A manutenção dessa arquitetura também está alinhada à realidade atual do projeto: equipe em aprendizado sobre o sistema legado, prazo de um semestre e ausência de infraestrutura de mensageria, orquestração de containers ou múltiplos ambientes de deploy.

A escolha não impede futuras mudanças. Caso o volume de pedidos, quantidade de vendedores, integrações ou requisitos de desempenho aumentem significativamente, a decisão poderá ser revisitada.

## 8. Trade-offs

A escolha da arquitetura em camadas apresenta o seguinte equilíbrio:

| Critério                 | Camadas                                 | Eventos                                  | Microsserviços                   |
| ------------------------ | --------------------------------------- | ---------------------------------------- | -------------------------------- |
| Complexidade operacional | Menor                                   | Maior                                    | Muito maior                      |
| Custo de mudança         | Menor                                   | Médio                                    | Maior                            |
| Manutenibilidade         | Adequada                                | Maior potencial                          | Alta, mas com maior complexidade |
| Confiabilidade           | Adequada                                | Pode aumentar com desacoplamento         | Depende da infraestrutura        |
| Desempenho               | Adequado ao cenário atual               | Pode beneficiar processamento assíncrono | Permite escala independente      |
| Segurança                | Adequada com isolamento das integrações | Depende da implementação                 | Permite isolamento por serviço   |

## 9. Conclusão

A análise mostrou que não existe necessidade atual de realizar uma migração para eventos distribuídos ou microsserviços apenas para evoluir a arquitetura.

A arquitetura em camadas apresenta a maior pontuação na matriz de decisão e permite que o ShopSphere continue sendo evoluído de forma incremental.

Os padrões e componentes existentes, como `PaymentAdapter`, `FreightAdapter`, `DiscountStrategy` e `OrderPublisher`/`OrderObserver`, permitem que pontos específicos do sistema sejam desacoplados gradualmente sem exigir uma mudança completa da arquitetura.

A decisão poderá ser revisitada caso novos requisitos ou mudanças de escala alterem os trade-offs considerados nesta análise.
