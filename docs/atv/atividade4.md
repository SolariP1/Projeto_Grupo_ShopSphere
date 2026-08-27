# ShopSphere — Aula 04

## 1. Problema identificado

Na análise do código , identificamos que a verificação de estoque estava sendo realizada no `ShopSphereService`.

Essa responsabilidade estava relacionada diretamente ao estado do produto, o que aumentava a quantidade de responsabilidades do serviço e diminuía sua coesão.

Assim, depois de analisar o problema decidimos iniciar as alterações, em aula fizemos essas duas mudanças.

## 2. Princípios relacionados

O problema está relacionado principalmente ao:

* **Responsabilidade:** cada classe deve possuir uma responsabilidade bem definida.
* **Coesão:** responsabilidades relacionadas ao produto devem permanecer próximas da representação do produto.
* **Acoplamento:** retirar a lógica de estoque do serviço reduz sua dependência direta de regras específicas do produto.

## 3. Refatoração realizada

A verificação de estoque foi retirada do `ShopSphereService` e transferida para a classe `Product`.

### Antes

```text
ShopSphereService
        |
        +-- verifica estoque do Product
        |
        +-- realiza outras operações do sistema
```

O serviço era responsável por uma regra que pertence ao domínio do produto.

### Depois

```text
ShopSphereService
        |
        +-- utiliza Product

Product
        |
        +-- controla sua disponibilidade/estoque
```

Dessa forma, a responsabilidade relacionada ao estoque fica mais próxima do objeto que possui esse estado.

### Alteração em `Order`

A classe `Order` também foi ajustada para melhorar sua estrutura.

Foi adicionado o construtor:

```java
public Order(String id, String customer) {
    this.id = id;
    this.customer = customer;
}
```

E o método responsável por adicionar produtos ao pedido:

```java
public void addProduct(String productId, double price) {
    productIds.add(productId);
    total += price;
}
```

Com isso, a própria classe `Order` passa a concentrar a responsabilidade de adicionar produtos e atualizar o total do pedido.

## 4. Problema observado

A lógica anterior concentrava regras de diferentes entidades dentro do serviço, tornando suas responsabilidades mais amplas.

A refatoração busca distribuir melhor essas responsabilidades entre `Product`, `Order` e `ShopSphereService`.

## 5.  Decisão

Mover a verificação de estoque para `Product` e concentrar em `Order` a operação de adicionar produtos e atualizar o total. Fizemos isso por conta das responsabilidades excessivas, mas dessa forma ficam mais próximas dos objetos aos quais pertencem, aumentando a coesão e reduzindo responsabilidades desnecessárias no serviço.

### Consequências

**Positivas:**

* maior coesão;
* melhor organização das responsabilidades;
* menor acoplamento;
* código mais fácil de manter e testar.

**Negativas:**

* algumas chamadas existentes precisaram ser ajustadas para utilizar as novas responsabilidades.

## 6.  Conclusão

A refatoração distribuiu melhor as responsabilidades entre as classes do domínio. A lógica relacionada ao estoque passou para `Product`, enquanto `Order` passou a controlar a adição de produtos e atualização do total.

A mudança aplica principalmente o princípio **SRP**, buscando maior coesão e menor acoplamento no código.
