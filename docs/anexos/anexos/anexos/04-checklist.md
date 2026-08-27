# Anexo 04 — Checklist de verificação

- [ ] O VSCode foi aberto na pasta que contém o `pom.xml`.
- [ ] `java -version` funciona.
- [ ] `javac -version` funciona.
- [ ] `mvn -version` funciona.
- [ ] Existe `src/main/java`.
- [ ] Foi criada `src/test/java`, caso não existisse.
- [ ] O pacote da classe de teste corresponde ao pacote da classe original.
- [ ] JUnit 5 está configurado no `pom.xml`.
- [ ] Não foi duplicada nenhuma dependência JUnit.
- [ ] `mvn clean compile` termina com `BUILD SUCCESS`.
- [ ] Foi criada uma classe `NomeDaClasseTest.java`.
- [ ] O teste possui pelo menos um método com `@Test`.
- [ ] `mvn clean test` executa os testes.
- [ ] O terminal mostra `Failures: 0` e `Errors: 0`.
- [ ] O terminal termina com `BUILD SUCCESS`.
