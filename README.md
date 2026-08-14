# ShopSphere — Projeto Semestral

O ShopSphere simula um marketplace/loja online com produtos de diferentes vendedores, carrinho, pedidos, pagamento, frete, descontos e notificações.

O código inicial **compila e executa**, mas representa um legado com decisões propositalmente incompletas ou inadequadas. O aluno deverá evoluí-lo conforme os conteúdos trabalhados em cada aula.

## Escopo inicial
- produtos e vendedores;
- estoque simplificado;
- carrinho/pedido;
- descontos;
- pagamento;
- cálculo/contratação de frete;
- notificações;
- integrações externas.

## Execução
Requer Java 17.

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out br.edu.shopsphere.Main
```

As atividades estão em `atividades/README_ShopSphere_AulaXX.md`.

> Classes com nomes de padrões não garantem que o padrão esteja corretamente aplicado. O aluno deve justificar necessidade, problema e solução.
