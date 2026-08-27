# Anexo 02 — Trechos para conferência do pom.xml

Use somente quando a configuração ainda não existir.

## JUnit 5

Dentro de `<dependencies>`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

## Maven Surefire

Dentro de `<build><plugins>`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
</plugin>
```

## Atenção

Não substitua o `pom.xml` completo do projeto.

Não duplique:

- `<dependencies>`;
- `<build>`;
- `<plugins>`;
- JUnit;
- `maven-surefire-plugin`.

Preserve todas as dependências e configurações próprias do projeto.
