# ADR-0002 — Encapsulamento

## Alunos
 - Alice Ferreira do Nascimento
 - Antonio Silveira Peres Neto
 - Heloisa Rodrigues Mota
 - Lucas Almeida Santos


## Decisão
Foi decidido refatorar as classes `service` e `produto`, aplicando melhor o encapsulamento das funções e tornando o stock uma classe privada. Posteriormente, as classes `service` e `order` também foram alteradas para corrigir o funcionamento da função `addProduct`, garantindo que as informações dos produtos adicionados aos pedidos fossem devidamente armazenadas.

## Motivo
O código apresentava problemas de design de software, como muitas funções desnecessárias e responsabilidades concentradas em uma única classe. Além disso, havia falhas de segurança e encapsulamento, principalmente relacionadas ao acesso ao `stock`. Também foi identificado que a função `addProduct` não estava armazenando corretamente as informações dos pedidos.

## Alternativas
- Nós pensamos em alterar o `Seller` mas optamos por não fazer isso no momento.

## Consequências
- Positivo: Um código mais bem estruturado e limpo.
- Positivo: As funções alteradas tiveram o resultado esperado e estão funcionando devidamente