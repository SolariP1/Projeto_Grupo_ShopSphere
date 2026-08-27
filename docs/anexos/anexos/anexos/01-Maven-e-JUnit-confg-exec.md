# Anexo 01 — Maven e JUnit: configuração e execução manual

## 1. Objetivo

Este roteiro serve para diferentes projetos dos grupos. Os nomes das classes e dos pacotes podem mudar.

Não copie uma estrutura de pacote de outro projeto. Use sempre o `package` real da classe que será testada.

---

# PARTE A — CONFERIR O PROJETO

## 2. Abrir a pasta correta no VSCode

Abra no VSCode a pasta raiz do projeto, isto é, a pasta onde está o arquivo:

```text
pom.xml
```

Exemplo:

```text
MeuProjeto/
├── pom.xml
├── README.md
├── docs/
└── src/
```

No terminal, confirme que está nessa pasta.

PowerShell:

```powershell
pwd
```

O caminho mostrado deve terminar com o nome da pasta do projeto.

---

## 3. Conferir Java

Execute:

```powershell
java -version
```

Depois:

```powershell
javac -version
```

Os dois comandos devem mostrar uma versão instalada do Java.

Se `java` funcionar e `javac` não funcionar, provavelmente há apenas um ambiente de execução ou o JDK não está corretamente configurado.

---

## 4. Conferir Maven

Execute:

```powershell
mvn -version
```

O resultado deve apresentar informações semelhantes a:

```text
Apache Maven ...
Java version: ...
```

Se aparecer a mensagem de que `mvn` não é reconhecido, o Maven não está instalado ou não está configurado no `PATH`.

---

# PARTE B — CRIAR A ESTRUTURA DE TESTES

## 5. Estrutura padrão Maven

O código principal deve ficar em:

```text
src/main/java
```

Os testes devem ficar em:

```text
src/test/java
```

Se `src/test/java` não existir, crie manualmente.

A estrutura básica ficará:

```text
MeuProjeto/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── ...
    └── test/
        └── java/
            └── ...
```

---

## 6. Repetir no teste o mesmo pacote da classe original

Imagine que a classe original esteja em:

```text
src/main/java/br/edu/projeto/service/PedidoService.java
```

e dentro do arquivo exista:

```java
package br.edu.projeto.service;
```

Crie a mesma estrutura em `src/test/java`:

```text
src/test/java/br/edu/projeto/service/
```

Depois crie:

```text
PedidoServiceTest.java
```

Resultado:

```text
src/
├── main/
│   └── java/
│       └── br/
│           └── edu/
│               └── projeto/
│                   └── service/
│                       └── PedidoService.java
└── test/
    └── java/
        └── br/
            └── edu/
                └── projeto/
                    └── service/
                        └── PedidoServiceTest.java
```

## Regra importante

O teste deve utilizar o mesmo `package` da classe testada:

```java
package br.edu.projeto.service;
```

O aluno deve substituir esse exemplo pelo pacote real do seu projeto.

---

# PARTE C — CONFERIR O pom.xml

## 7. Não substituir o pom.xml existente

Cada projeto pode possuir dependências próprias.

Por isso:

**não copie um `pom.xml` inteiro de outro projeto.**

Apenas confira se o projeto já possui JUnit.

Procure no `pom.xml` por:

```xml
org.junit.jupiter
```

Se já existir uma dependência JUnit 5, não crie outra igual.

---

## 8. Adicionar JUnit 5 manualmente, se necessário

Dentro do bloco:

```xml
<dependencies>
    ...
</dependencies>
```

adicione:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

Exemplo:

```xml
<project>
    ...

    <dependencies>

        <!-- outras dependências do projeto -->

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.4</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

</project>
```

Se o projeto já possuir `<dependencies>`, use o bloco existente.

Não crie dois blocos `<dependencies>` desnecessariamente.

---

## 9. Maven Surefire Plugin

O Maven usa o **Surefire Plugin** para executar testes na fase `test`.

Em muitos projetos Maven atuais ele funciona mesmo sem uma configuração explícita. Porém, para padronizar o ambiente da disciplina, pode ser acrescentado ao `pom.xml`.

Dentro de:

```xml
<build>
    <plugins>
        ...
    </plugins>
</build>
```

adicione, caso ainda não exista:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
</plugin>
```

Exemplo:

```xml
<build>
    <plugins>

        <!-- outros plugins do projeto -->

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.5.2</version>
        </plugin>

    </plugins>
</build>
```

Se o projeto já possuir `<build>` ou `<plugins>`, utilize os blocos existentes.

Não duplique o `maven-surefire-plugin`.

---

# PARTE D — PRIMEIRO TESTAR O MAVEN

## 10. Limpar arquivos anteriores

Na raiz do projeto:

```powershell
mvn clean
```

Quando funcionar corretamente, deverá aparecer ao final:

```text
BUILD SUCCESS
```

---

## 11. Compilar o projeto

Execute:

```powershell
mvn compile
```

ou diretamente:

```powershell
mvn clean compile
```

O resultado esperado é:

```text
BUILD SUCCESS
```

## Se aparecer BUILD FAILURE

Não continue para o JUnit ainda.

Primeiro leia as linhas de erro exibidas acima de:

```text
BUILD FAILURE
```

Erros comuns:

- classe que não compila;
- import incorreto;
- versão do Java incompatível;
- dependência não encontrada;
- erro no `pom.xml`;
- nome de pacote incompatível com a estrutura do projeto.

A etapa Maven deve funcionar antes de analisar os testes JUnit.

---

# PARTE E — CRIAR O TESTE JUNIT

## 12. Criar a classe de teste

Para uma classe:

```text
PedidoService.java
```

crie:

```text
PedidoServiceTest.java
```

O padrão recomendado é:

```text
NomeDaClasseTest.java
```

---

## 13. Modelo mínimo

```java
package br.edu.projeto.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PedidoServiceTest {

    @Test
    void deveCriarObjeto() {
        PedidoService service = new PedidoService();

        assertNotNull(service);
    }
}
```

Esse teste é apenas um primeiro exemplo.

Dependendo da classe, o construtor pode exigir parâmetros. Nesse caso, adapte a criação do objeto ao código real.

---

## 14. Testar comportamento real

O ideal é testar uma regra do sistema.

Exemplo hipotético:

```java
package br.edu.projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CalculadoraDescontoTest {

    @Test
    void deveAplicarDezPorCentoDeDesconto() {
        CalculadoraDesconto calculadora = new CalculadoraDesconto();

        double resultado = calculadora.calcular(100.0);

        assertEquals(90.0, resultado);
    }
}
```

A estrutura geral é:

```text
PREPARAR → EXECUTAR → VERIFICAR
```

ou:

```java
// preparar
Objeto objeto = new Objeto();

// executar
Resultado resultado = objeto.metodo();

// verificar
assertEquals(esperado, resultado);
```

---

## 15. Assertions mais utilizadas

### Verificar igualdade

```java
assertEquals(valorEsperado, valorObtido);
```

### Verificar verdadeiro

```java
assertTrue(condicao);
```

### Verificar falso

```java
assertFalse(condicao);
```

### Verificar se não é nulo

```java
assertNotNull(objeto);
```

### Verificar exceção

```java
assertThrows(IllegalArgumentException.class, () -> {
    objeto.metodoInvalido();
});
```

---

# PARTE F — EXECUTAR O JUNIT PELO MAVEN

## 16. Executar todos os testes

Na raiz do projeto:

```powershell
mvn test
```

Ou, preferencialmente:

```powershell
mvn clean test
```

O Maven:

1. compila o código principal;
2. compila as classes de teste;
3. executa os testes JUnit;
4. apresenta o resultado no terminal.

---

## 17. Como saber se deu certo

Procure por algo semelhante a:

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

e, principalmente:

```text
BUILD SUCCESS
```

Isso significa que:

- o projeto compilou;
- o código de teste compilou;
- o JUnit foi encontrado;
- o teste foi executado;
- nenhum teste falhou.

---

## 18. Exemplo de teste com falha

Pode aparecer:

```text
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
```

e:

```text
BUILD FAILURE
```

Nesse caso, o ambiente de teste pode estar funcionando corretamente, mas o valor esperado no teste foi diferente do valor produzido pelo código.

Leia:

```text
expected: <...>
but was: <...>
```

para comparar o esperado com o resultado real.

---

## 19. Diferença entre Failure e Error

### Failure

O teste executou, mas a afirmação não foi satisfeita.

Exemplo:

```java
assertEquals(100, resultado);
```

mas o método retornou `90`.

### Error

O teste não conseguiu terminar normalmente.

Pode ocorrer por:

- `NullPointerException`;
- configuração incorreta;
- problema ao criar o objeto;
- exceção não prevista;
- erro de integração.

---

# PARTE G — SEQUÊNCIA CORRETA COMPLETA

## 20. Ordem recomendada

Na raiz do projeto:

```powershell
java -version
javac -version
mvn -version
```

Depois confira/crie:

```text
src/test/java
```

Depois confira o `pom.xml`.

Então:

```powershell
mvn clean compile
```

Se aparecer:

```text
BUILD SUCCESS
```

crie o arquivo:

```text
NomeDaClasseTest.java
```

no pacote correspondente dentro de:

```text
src/test/java
```

Depois:

```powershell
mvn clean test
```

Resultado final esperado:

```text
Tests run: X, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

# PARTE H — APÓS ALTERAR O CÓDIGO

Sempre que houver refatoração ou alteração de regra, execute novamente:

```powershell
mvn clean test
```

O teste ajuda a verificar se o comportamento esperado foi preservado.

---

# RESUMO

```text
PROJETO
  ↓
conferir Java
  ↓
conferir Maven
  ↓
criar src/test/java
  ↓
reproduzir o package da classe
  ↓
conferir JUnit no pom.xml
  ↓
mvn clean compile
  ↓
BUILD SUCCESS?
  ├── NÃO → corrigir o projeto
  └── SIM
        ↓
   criar NomeDaClasseTest.java
        ↓
   mvn clean test
        ↓
   verificar testes
        ↓
   BUILD SUCCESS
```
