# ADR-0004— Responsabilidades na adição de produtos ao pedido

## Status

Aceito

## Contexto

O ShopSphere precisa apresentar diagramas do requisito que o cliente adiciona produtos a um pedido. Durante a modelagem da funcionalidade, foi necessário definir como as responsabilidades seriam distribuídas entre as classes envolvidas. A operação utiliza o `ShopSphereService` como ponto de orquestração, o `ProductRepository` para localizar o produto e a classe `Order` para armazenar o produto no pedido e atualizar o valor total.

A funcionalidade deve permitir que, a partir de um `orderId` e de um `productId`, o sistema localize o pedido e o produto, verifique se ambos existem, reserve o produto e então adicione o item ao pedido.

## Alternativas consideradas

1. **Centralizar toda a operação no `ShopSphereService`** — o serviço buscaria o produto, alteraria diretamente o pedido, controlaria a lista de produtos e realizaria o cálculo do total. É uma implementação simples, porém aumenta a concentração de responsabilidades no serviço.

2. **Distribuir as responsabilidades entre as classes do domínio** — o `ShopSphereService` fica responsável por orquestrar a operação, o `ProductRepository` fica responsável pela busca dos produtos e o `Order` fica responsável por adicionar o produto e atualizar seu total. Essa alternativa mantém as responsabilidades mais separadas e coerentes com a arquitetura atual.

3. **Criar um novo componente específico para o carrinho** — uma nova classe poderia concentrar exclusivamente as operações de carrinho. Essa alternativa poderia ser útil em um sistema com regras de carrinho mais complexas, mas adicionaria uma nova abstração sem uma necessidade atual do projeto.

## Decisão

Adotar a segunda alternativa, mantendo as responsabilidades distribuídas entre as classes existentes.

O `ShopSphereService` será responsável por orquestrar a operação de adição do produto ao pedido:

* localizar o `Order` pelo `orderId`;
* solicitar ao `ProductRepository` a busca do produto pelo `productId`;
* verificar se o pedido e o produto foram encontrados;
* solicitar a reserva do produto;
* adicionar o produto ao pedido.

O `ProductRepository` será responsável pelo armazenamento e busca dos produtos.

A classe `Order` será responsável por adicionar o produto ao pedido e atualizar o valor total por meio de `addProduct(productId, price)`.

Essa decisão mantém a organização em camadas adotada na arquitetura do ShopSphere e evita concentrar responsabilidades de persistência e domínio no `ShopSphereService`.

## Consequências

### Positivas

* Mantém as responsabilidades das classes mais bem separadas.
* O `ShopSphereService` continua atuando como orquestrador da operação.
* O `ProductRepository` concentra a responsabilidade de localizar produtos.
* A classe `Order` mantém a responsabilidade relacionada ao próprio pedido.
* Facilita a compreensão do fluxo de adição de produtos.
* Mantém coerência com a arquitetura em camadas definida na ADR-0001.

### Negativas / trade-offs

* A operação depende da interação entre `ShopSphereService`, `ProductRepository`, `Product` e `Order`.
* O fluxo possui mais de uma classe envolvida, exigindo que os desenvolvedores conheçam essas responsabilidades.

## Evidências relacionadas

* **Requisito:** RF01 — o sistema deve permitir montar um pedido adicionando produtos com reserva de estoque.
* **Classes:** `ShopSphereService`, `Product`, `Order`, `ProductRepository`.
* **Operação principal:** `ShopSphereService.addItem(orderId, productId)`.
* **Operação do pedido:** `Order.addProduct(productId, price)`.
* **Diagrama de Classes:** modelo da funcionalidade “Adicionar produto no carrinho”.
* **Diagrama de Sequência:** fluxo entre Cliente, `ShopSphereService`, `ProductRepository`, `Product` e `Order`.
* **Diagrama de Atividades:** fluxo de busca do pedido e produto, reserva e adição do item.
* **ADR relacionada:** `docs/adr/ADR-0001-arquitetura.md`.
* **Evidência GitHub:** /docs/diagramas-aula08
