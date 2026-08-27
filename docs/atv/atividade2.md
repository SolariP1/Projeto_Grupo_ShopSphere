# ShopSphere — Aula 02

## 1. Contexto

O ShopSphere é um marketplace que conecta **clientes e vendedores**, envolvendo também operações internas e sistemas externos necessários para o funcionamento da plataforma.

## 2. Stakeholders e sistemas

| Elemento                 | Papel no sistema                                                               |
| ------------------------ | ------------------------------------------------------------------------------ |
| **Cliente**              | Consulta produtos e realiza pedidos.                                           |
| **Vendedor**             | Cadastra e comercializa produtos, podendo possuir políticas próprias.          |
| **Operação**             | Gerencia processos relacionados aos pedidos e ao funcionamento do marketplace. |
| **Sistema de pagamento** | Processa ou autoriza pagamentos.                                               |
| **Sistema de frete**     | Calcula ou processa informações relacionadas à entrega.                        |

## 3. Mapa de contexto

## Mapeamento de contexto

O **cliente** é o usuário que utiliza o ShopSphere para consultar produtos, realizar pedidos e acompanhar suas compras.

Os **vendedores** utilizam a plataforma para disponibilizar e comercializar seus produtos. Cada vendedor pode possuir políticas próprias relacionadas à venda, preços, descontos, estoque e entrega.

A **operação do marketplace** é responsável pelo gerenciamento dos processos internos da plataforma, como acompanhamento de pedidos e suporte ao funcionamento do sistema.

O **sistema de pagamento** representa um serviço externo utilizado para processar as transações realizadas pelos clientes. A comunicação com esse sistema deve ser considerada uma integração externa.

O **sistema de frete** representa um serviço externo relacionado ao cálculo ou processamento das entregas dos pedidos.

Dessa forma, o contexto do ShopSphere pode ser resumido como:

**Cliente → ShopSphere ← Vendedor**

**ShopSphere → Sistema de Pagamento**

**ShopSphere → Sistema de Frete**

A relação entre esses elementos demonstra que o ShopSphere atua como intermediário entre clientes e vendedores e também depende de serviços externos para determinadas operações.


## 4. Restrições

* Vendedores podem possuir políticas comerciais diferentes.
* Pagamentos dependem de serviços externos.
* Processos de entrega dependem de informações e serviços de frete.
* Alterações no sistema devem preservar as regras específicas de cada participante.

## 5. Hipóteses

> Cada vendedor pode definir suas próprias políticas de venda.
>O pagamento será realizado por meio de um sistema externo.
>O cálculo ou processamento do frete poderá depender de um serviço externo.

## 6. Dúvidas relevantes

* Quais políticas podem ser definidas individualmente por cada vendedor?
* Quais sistemas externos de pagamento e frete serão utilizados?
* A operação poderá alterar pedidos realizados pelos clientes?
* Como conflitos entre políticas do vendedor e regras do marketplace serão tratados?

## 7. Problema observado

O projeto possui decisões e elementos legados cuja finalidade e adequação ao contexto atual precisam ser analisadas antes de alterações.

## 8. Alteração realizada

Foi criado este documento para registrar o contexto do ShopSphere, seus principais stakeholders, sistemas externos, restrições, hipóteses e dúvidas identificadas.

Não foram realizadas alterações funcionais no código nesta etapa.

## 9.  Conclusão

O contexto do ShopSphere foi mapeado considerando seus principais stakeholders, sistemas externos, restrições e hipóteses. As dúvidas identificadas deverão ser validadas antes que hipóteses sejam transformadas em requisitos ou decisões de implementação.
