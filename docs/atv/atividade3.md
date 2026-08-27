# ShopSphere — Aula 03

## 1. Problemas de design identificados

Durante a análise do código do ShopSphere, foram identificados  problemas relacionados a alto acoplamento, baixa coesão e responsabilidades confusas.

### Alto acoplamento

A classe `ShopSphereService` concentra diferentes operações do sistema e possui responsabilidades relacionadas a diferentes partes do marketplace.

Essa concentração aumenta o acoplamento, pois alterações em uma funcionalidade podem afetar outras responsabilidades presentes no mesmo serviço.

### Baixa coesão

A concentração de diferentes operações em `ShopSphereService` também pode reduzir sua coesão, pois a classe deixa de possuir um único foco bem definido.

### Responsabilidades confusas

Foram observados componentes com responsabilidades distintas dentro do sistema, especialmente nas áreas de pedidos, integrações e serviços.

Os adapters de pagamento e frete possuem responsabilidades de integração, enquanto o `ShopSphereService` concentra regras e operações do sistema.

---

## 2. Evidências no código

Os principais pontos analisados foram:

* `src/main/java/br/edu/shopsphere/service/ShopSphereService.java` — concentra diferentes operações do sistema, podendo gerar alto acoplamento e baixa coesão.
* `src/main/java/br/edu/shopsphere/repository/ProductRepository.java` — concentra responsabilidades relacionadas ao acesso aos produtos.
* `src/main/java/br/edu/shopsphere/patterns/adapter/PaymentAdapter.java` — responsável pela adaptação da integração de pagamento.
* `src/main/java/br/edu/shopsphere/patterns/adapter/FreightAdapter.java` — responsável pela adaptação da integração de frete.
* `src/main/java/br/edu/shopsphere/model/Order.java` — representa a entidade relacionada aos pedidos.

A análise considera que o nome de um padrão de projeto não garante, por si só, que sua utilização esteja adequada. É necessário observar as responsabilidades e dependências presentes no código.

---

## 3. Problema priorizado

O principal problema priorizado foi a **concentração de responsabilidades em `ShopSphereService`**:

`src/main/java/br/edu/shopsphere/service/ShopSphereService.java`

Essa concentração pode dificultar a manutenção, os testes e a evolução do sistema, além de aumentar o acoplamento entre diferentes funcionalidades.

---

## 4. Alteração realizada

Nesta etapa, foi realizada a **análise e documentação dos problemas de design**, sem realizar uma refatoração imediata.

O problema priorizado foi registrado para que uma possível solução possa ser analisada e implementada posteriormente.

---

## 5. Conclusão

A análise do código legado identificou pontos que podem dificultar a manutenção do ShopSphere, principalmente pela concentração de responsabilidades em `ShopSphereService`.

O problema foi documentado e priorizado antes de qualquer alteração, seguindo a proposta da atividade de **analisar antes de modificar**.
