# ADR-0001 — Arquitetura

## Alunos
 - Alice Ferreira do Nascimento
 - Antonio Silveira Peres Neto
 - Heloisa Rodrigues Mota
 - Lucas Almeida Santos


## Decisão
Como estamos chegando agora no projeto e ainda não conhecemos todo o código, vamos refatorar aos poucos, em passos pequenos, em vez de reescrever tudo de uma vez. O primeiro alvo é o `ShopSphereService`, que hoje cria diretamente `CardGatewayLegacy`, `FreightLegacyApi` e `MarketplaceMailApi` com `new` dentro do construtor. A ideia é começar reduzindo esse acoplamento (por exemplo, recebendo essas dependências de fora, via construtor), sem mudar comportamento nem mexer em tudo de uma vez.

## Motivo
`ShopSphereService` concentra pedido, pagamento, frete, desconto e notificação numa classe só, e depende diretamente das implementações legadas. Isso deixa o código difícil de entender e de testar (não dá pra testar `checkout()` sem os serviços reais), e qualquer alteração num desses pontos arrisca quebrar os outros. Como o grupo é novo no projeto, preferimos entender e melhorar uma parte pequena a mexer em tudo sem ter certeza do impacto.

## Alternativas
- Reescrever a arquitetura inteira agora: descartada, porque ainda não conhecemos todas as regras de negócio e o risco de quebrar algo é alto.
- Não mexer em nada até entender 100% do sistema: descartada, porque a disciplina exige evolução incremental a cada aula.
- Refatorar pouco a pouco, começando pelo acoplamento mais óbvio (`ShopSphereService`): escolhida, por ser reversível e permitir aprender o sistema enquanto ele melhora.

## Consequências
- Positivo: menor risco de regressão, já que cada mudança é pequena e revisável; o time aprende o domínio enquanto refatora.
- Positivo: abre caminho para testar `ShopSphereService` sem depender das classes legadas reais.
- Negativo: o projeto continua com vários outros pontos acoplados/inadequados por mais algum tempo, já que a melhoria é gradual e não imediata.
- Próximo passo natural: repetir esse mesmo raciocínio para outras classes acopladas nas próximas aulas.

> ADR legado propositalmente insuficiente.
