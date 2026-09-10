# Matriz de Decisão Arquitetural — ShopSphere (Aula 06)

## Como utilizar

1. Foram levantadas 3 alternativas arquiteturais viáveis para o ShopSphere.
2. Os critérios partiram dos problemas reais encontrados em `service/ShopSphereService.java` e `patterns/adapter/PaymentAdapter.java`, mais dois critérios de contexto do projeto (complexidade operacional e custo/esforço de mudança).
3. Cada critério recebeu um peso.
4. Cada alternativa recebeu uma nota por critério.
5. Peso × Nota foi calculado célula a célula.
6. Os totais de cada alternativa foram somados.
7. As justificativas de todas as notas estão em `JUSTIFICATIVAS-NOTAS.md`.

---

## Escala utilizada para os pesos

| Peso | Significado |
|---:|---|
| 1 | Baixa importância para o projeto |
| 2 | Importância média |
| 3 | Alta importância |

---

## Escala utilizada para as notas

| Nota | Significado |
|---:|---|
| 1 | Atende fracamente ao critério |
| 2 | Atende parcialmente |
| 3 | Atende de forma adequada |
| 4 | Atende bem |
| 5 | Atende muito bem |

---

## Alternativas analisadas

- **Alternativa A:** manter a arquitetura atual do ShopSphere — monólito em camadas, processo único, `Main → ShopSphereFacade → ShopSphereService → Repositório/Adapters`, com chamadas síncronas.
- **Alternativa B:** evoluir o `OrderPublisher`/`OrderObserver` já existente para um barramento de eventos interno (mesmo processo, sem broker externo), fazendo pagamento, frete e notificação reagirem a eventos do pedido em vez de serem chamados em sequência dentro de `checkout()`.
- **Alternativa C:** dividir o sistema em microsserviços por domínio (catálogo/estoque, pedido, pagamento, frete), comunicando-se via API.

---

## Matriz

| Critério | Peso | Alternativa A | P×N | Alternativa B | P×N | Alternativa C | P×N |
|---|---:|---:|---:|---:|---:|---:|---:|
| Segurança | 3 | 3 | 9 | 4 | 12 | 3 | 9 |
| Complexidade operacional | 3 | 5 | 15 | 3 | 9 | 1 | 3 |
| Custo/esforço de mudança | 3 | 5 | 15 | 2 | 6 | 1 | 3 |
| Manutenibilidade | 2 | 2 | 4 | 4 | 8 | 2 | 4 |
| Confiabilidade | 2 | 3 | 6 | 4 | 8 | 3 | 6 |
| Desempenho | 1 | 5 | 5 | 4 | 4 | 2 | 2 |
| **TOTAL** | | | **54** | | **47** | | **27** |

> Para "Complexidade operacional" e "Custo/esforço de mudança", nota alta significa **menos** complexidade/custo, não o contrário.

---

## Exemplo do cálculo

```text
Peso × Nota = Resultado

Exemplo (Custo/esforço de mudança, Alternativa A):
3 × 5 = 15
```

---

## Resultado

- **Total da Alternativa A (manter arquitetura atual):** 54
- **Total da Alternativa B (barramento de eventos interno):** 47
- **Total da Alternativa C (microsserviços):** 27

### Alternativa com maior pontuação

**Alternativa A — manter a arquitetura em camadas simples.**

### Observação

A maior pontuação não decidiu tudo sozinha. O grupo também levou em conta:

- **Custos:** a Alternativa B exigiria reescrever `OrderObserver` como contrato de evento estruturado e mover parte da lógica de `checkout()` para consumidores assíncronos; a Alternativa C exigiria separar `Product`/`Order`/`Seller` em serviços com deploys próprios — nenhuma das duas cabe com folga no prazo do semestre.
- **Riscos:** os problemas mais graves hoje (notificação enviada para um e-mail fixo em vez de `o.customer`, ausência de tratamento de falha em `payment.cobrar(...)`/`freight.cotar(...)`, herança do `PaymentAdapter` sobre `CardGatewayLegacy`) são falhas de implementação corrigíveis dentro da própria Alternativa A, não limitações do estilo em camadas.
- **Complexidade:** mesmo sem broker externo, a Alternativa B já exigiria desenhar contratos de evento; a Alternativa C exigiria orquestração e comunicação de rede que a equipe não opera hoje.
- **Contexto do projeto:** equipe pequena e recém-chegada ao legado, prazo de um semestre, evolução incremental já adotada em `docs/adr/ADR-0001-arquitetura.md` — a Alternativa B fica registrada como evolução natural futura, não como pendência desta aula.

A decisão final (manter a arquitetura atual com as correções internas descritas) está registrada e justificada na atividade principal (`README-Aula06.md`, seção 10).
