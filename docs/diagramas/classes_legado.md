# Classes — visão parcial

```mermaid
classDiagram
 class ShopSphereService
 class Product
 class Order
 class CardGatewayLegacy
 class FreightLegacyApi
 ShopSphereService --> Product
 ShopSphereService --> Order
 ShopSphereService --> CardGatewayLegacy
 ShopSphereService --> FreightLegacyApi
```
# Adicionar produto no carrinho
## Diagrama de Classes

```mermaid
classDiagram

class Product {
    +String id
    +String sellerId
    +String name
    +double price
    -int stock
}

class Order {
    +String id
    +String customer
    +String status
    +double total
    +List~String~ productIds
    +addProduct(productId: String, price: double) void
}

class ProductRepository {
    -Map~String,Product~ data
    +save(p: Product) void
    +find(id: String) Product
    +all() Collection~Product~
}

class ShopSphereService {
    +createOrder(id: String, customer: String) Order
    +addItem(orderId: String, productId: String) void
}

ShopSphereService "1" --> "*" Order : gerencia
ShopSphereService --> ProductRepository : possui
ProductRepository "1" --> "*" Product : armazena
```
> `"1" --> "*"` significa que uma instância se relaciona com várias instâncias. 
1 = um * = vários (zero ou mais). O nome disso em UML é multiplicidade.


## Diagrama de Sequência

```mermaid
sequenceDiagram

    actor Cliente
    participant S as ShopSphereService
    participant R as ProductRepository
    participant P as Product
    participant O as Order

    Cliente->>S: addItem(orderId, productId)

    S->>S: orders.get(orderId)
    S->>R: find(productId)
    R-->>S: Product ou null

    alt Order ou Product não encontrado
        S-->>Cliente: Encerra operação
    else Order e Product encontrados
        S->>P: reserve()
        S->>O: addProduct(productId, price)
    end
```

## Diagrama de Atividades

```mermaid
flowchart TD

    A([Início]) --> B[Cliente deseja adicionar produto ao carrinho]

    B --> C[Obter orderId do pedido]
    C --> D[Obter productId do produto selecionado]

    D --> E[ShopSphereService recebe orderId e productId]

    E --> F[Buscar Order pelo orderId]
    F --> G[Buscar Product no ProductRepository pelo productId]

    G --> H{Order ou Product não encontrado?}

    H -- Sim --> I[Encerrar operação]
    I --> Z([Fim])

    H -- Não --> J[Reservar Product]
    J --> K[Adicionar productId ao Order]
    K --> L[Somar preço do Product ao total do Order]
    L --> M[Produto adicionado ao pedido]

    M --> Z
```