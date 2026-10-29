# UC6 — Projeto-base Java e JDBC

Este projeto já contém o driver PostgreSQL e a classe de conexão. Ao executar sem alterações, ele confirma se a conexão com o banco funcionou.

O projeto inicia pela classe:

```text
src/main/java/br/senac/uc6/Main.java
```

Depois de confirmar a conexão, crie a classe `RelatorioDemanda.java`. Ela executará a primeira query do projeto. Em seguida, `Main.java` chamará essa classe.

Para executar, use:

```bash
mvn compile exec:java
```

Antes de executar, confirme que PostgreSQL está em funcionamento e que o dump da clínica foi carregado.

\_

# Atualização do `README.md` Principal

## Execução via Java (JDBC)

O projeto conta agora com um módulo em Java para automação e extração direta dos relatórios a partir da aplicação:

- **Projeto:** `java/uc6-jdbc-base`
- **Relatório migrado e automatizado:** `Relatório 1 — Demanda por Especialidade` (Classe `RelatorioDemanda.java`).
- **Ponto de entrada:** `Main.java` estabelece a conexão e orquestra a consulta.
- **Forma de execução:** Via terminal na pasta do projeto Maven:
  ```bash
  mvn compile exec:java
  ```
