# 🏥 Sistema de Gestão Hospitalar — Módulo de Relatórios (UC6)

## 📌 Sobre o Projeto

O **Sistema de Gestão Hospitalar — Módulo de Relatórios** foi desenvolvido como parte da Unidade Curricular 6 (UC6) do curso de Tec. em Desenvolvimento de Sistemas (TDS).

O projeto tem como objetivo transformar os dados operacionais de uma clínica médica fictícia e anonimizada em indicadores e relatórios analíticos que auxiliam a gestão e a tomada de decisões.

A partir de consultas SQL estruturadas no PostgreSQL, a coordenação consegue analisar a demanda de pacientes por especialidade, o volume de atendimentos realizados por profissional, os índices de absenteísmo, o desempenho financeiro e a utilização dos consultórios.

Além das consultas SQL executadas no DBeaver, o projeto disponibiliza uma aplicação desenvolvida em Java, que se conecta ao banco de dados por meio de JDBC e oferece duas formas de interação:

- **Modo Terminal:** menu interativo com opções de consulta.
- **Interface Gráfica:** painel executivo desenvolvido com Java Swing, com interface visual em tema escuro.

O foco do projeto é fornecer informações claras e confiáveis para apoiar o planejamento de horários, a distribuição de profissionais, a contratação de especialistas e a utilização dos espaços físicos da clínica.

---

## 👨‍💻 Autor

- **Nome:** Felipe Walker
- **Turma:** TDS
- **Unidade Curricular:** UC6
- **Data de início:** 02/10/2026
- **Data de entrega finalizado:** 29/10/2026

---

## 🎯 Objetivos do Módulo

- Transformar dados brutos em informações úteis para a gestão clínica.
- Identificar as especialidades e os profissionais com maior demanda.
- Analisar os índices de faltas e ausências dos pacientes.
- Apurar o faturamento e o ticket médio dos atendimentos realizados.
- Identificar os consultórios mais utilizados e os horários de maior movimento.
- Desenvolver consultas SQL que apoiem decisões operacionais e financeiras.
- Disponibilizar os relatórios por meio de uma aplicação Java conectada ao PostgreSQL.

---

## 📊 Relatórios Analíticos Disponíveis

| Nº  | Relatório             | Pergunta de Negócio                                                                   | Situação  |
| :-: | :-------------------- | :------------------------------------------------------------------------------------ | :-------: |
|  1  | Demanda Operacional   | Quais especialidades e profissionais possuem maior volume de atendimentos realizados? | Concluído |
|  2  | Absenteísmo (Faltas)  | Qual é o índice de ausência de pacientes por convênio e especialidade?                | Concluído |
|  3  | Desempenho Financeiro | Qual é o faturamento total e o ticket médio das consultas concluídas?                 | Concluído |
|  4  | Ocupação de Espaços   | Quais consultórios são mais utilizados e quais são as faixas de horário de pico?      | Concluído |

### 1. Demanda Operacional

Analisa o volume de consultas realizadas por especialidade e profissional.

**Objetivo:** identificar os profissionais e as especialidades com maior demanda, auxiliando no planejamento de agendas, horários e distribuição de atendimentos.

### 2. Absenteísmo (Faltas)

Analisa as faltas dos pacientes, considerando sua distribuição por convênio e especialidade.

**Objetivo:** identificar padrões de ausência e apoiar ações para reduzir o absenteísmo e melhorar o aproveitamento dos horários disponíveis.

### 3. Desempenho Financeiro

Apura o faturamento total e o ticket médio das consultas realizadas.

**Objetivo:** fornecer indicadores financeiros que auxiliem na análise de receitas e no acompanhamento do desempenho da clínica.

### 4. Ocupação de Espaços

Analisa a utilização dos consultórios físicos e as faixas de horário com maior concentração de atendimentos.

**Objetivo:** apoiar a organização dos espaços, a distribuição das agendas e o planejamento da infraestrutura.

---

## 🛠️ Tecnologias e Ferramentas

| Tecnologia / Ferramenta | Aplicação                                      |
| :---------------------- | :--------------------------------------------- |
| Java JDK 17 ou superior | Desenvolvimento e execução da aplicação        |
| Java Swing              | Construção da interface gráfica executiva      |
| JDBC                    | Conexão da aplicação Java com o banco de dados |
| PostgreSQL              | Sistema gerenciador de banco de dados          |
| SQL                     | Consultas e análises dos dados operacionais    |
| DBeaver                 | Execução e análise dos scripts SQL             |
| Visual Studio Code      | Ambiente de desenvolvimento e execução         |
| Git                     | Controle de versão do projeto                  |

### Banco de Dados

- **SGBD:** PostgreSQL
- **Schema:** `clinica_pi_uc6`
- **Origem dos dados:** base fictícia e anonimizada disponibilizada para a UC6.
- **Tabela fato principal:** `consulta`

A tabela `consulta` registra os agendamentos e a execução dos atendimentos, sendo a principal fonte para os relatórios analíticos.

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, verifique se o ambiente possui:

1. Java JDK 17 ou superior instalado.
2. PostgreSQL ativo e acessível.
3. Base de dados da clínica carregada.
4. Driver JDBC do PostgreSQL disponível no projeto.
5. Visual Studio Code ou outro ambiente compatível com Java, caso utilize a execução pelo terminal.

### Configuração da conexão com o banco

Se necessário, confira as credenciais de acesso ao banco de dados no arquivo:

`clinica_pi_uc6/java/uc6-jdbc-base/src/main/java/br/senac/uc6/ConfigBanco.java`

Verifique se o endereço do servidor, a porta, o nome do banco, o usuário e a senha estão configurados corretamente.

---

## 🚀 Como Executar o Projeto Java

O projeto oferece duas opções de execução.

### Opção 1 — Pelo Terminal Integrado do VS Code

Ao abrir a pasta `study-clinica-senac` no VS Code, abra o terminal integrado utilizando:

`Ctrl + '`

Ou pelo menu:

**Terminal > New Terminal**

Os comandos abaixo devem ser executados diretamente da raiz do projeto, sem a necessidade de utilizar `cd`.

#### 1. Compilar o projeto

```bash
mkdir -p clinica_pi_uc6/java/uc6-jdbc-base/target/classes

javac -cp "clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" -d clinica_pi_uc6/java/uc6-jdbc-base/target/classes clinica_pi_uc6/java/uc6-jdbc-base/src/main/java/br/senac/uc6/*.java
```

#### 2. Executar a aplicação

**Modo Terminal Interativo — Menu com opções de 0 a 5**

```bash
java -cp "clinica_pi_uc6/java/uc6-jdbc-base/target/classes;clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" br.senac.uc6.Main
```

**Modo Interface Gráfica — Painel Executivo Swing**

```bash
java -cp "clinica_pi_uc6/java/uc6-jdbc-base/target/classes;clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" br.senac.uc6.Main --gui
```

> **Importante:** os comandos de execução acima utilizam o separador `;` do classpath do Windows. Em sistemas Linux ou macOS, substitua-o por `:`.

### Opção 2 — Execução Rápida pelo Windows

Para abrir diretamente a interface gráfica, sem precisar executar os comandos manualmente:

1. Abra o Explorador de Arquivos do Windows.
2. Navegue até a pasta:

   `clinica_pi_uc6/java/uc6-jdbc-base/`

3. Localize o arquivo `executar-interface.bat`.
4. Dê um duplo clique no arquivo.

O script realiza a compilação dos arquivos Java automaticamente, caso ainda não estejam compilados, e abre a interface gráfica da clínica.

---

## 🗄️ Como Executar os Relatórios SQL

Os scripts SQL analíticos estão disponíveis na pasta:

`clinica_pi_uc6/sql/`

A documentação complementar, incluindo o dicionário de dados e as regras de negócio, está disponível em:

`clinica_pi_uc6/documentacao/`

### Execução pelo DBeaver

1. Abra o DBeaver.
2. Conecte-se ao PostgreSQL disponibilizado para a turma.
3. Abra o arquivo SQL desejado, localizado na pasta `sql/`.
4. Antes de executar qualquer consulta, defina o schema correto:

```sql
SET search_path TO clinica_pi_uc6, public;
```

5. Execute a consulta utilizando `Ctrl + Enter`.
6. Para executar o script completo, utilize `Alt + X`.
7. Analise os resultados apresentados na grade de dados.
8. Consulte os prints de exemplo disponíveis na pasta `evidencias/`, quando necessário.

---

## 📋 Regras de Negócio Aplicadas

As consultas SQL seguem as regras de negócio definidas para a base de dados da clínica.

### Status das Consultas

| Situação           | Status no Banco        | Interpretação                                           |
| :----------------- | :--------------------- | :------------------------------------------------------ |
| Consulta realizada | `REALIZADA`            | Atendimento concluído com sucesso                       |
| Paciente faltou    | `FALTOU`               | Atendimento não realizado devido à ausência do paciente |
| Consulta cancelada | Status de cancelamento | Agendamento cancelado                                   |

### Regras para os Indicadores

- **Consultas realizadas:** devem ser filtradas estritamente por `status = 'REALIZADA'`.
- **Faltas e absenteísmo:** devem ser identificados por `status = 'FALTOU'`.
- **Faturamento:** deve considerar somente atendimentos realizados com valor válido.
- **Ticket médio:** deve ser calculado com base nos atendimentos realizados com valor cobrado válido.
- **Consultas canceladas e faltas:** não possuem valor cobrado, sendo representadas por `valor_cobrado IS NULL`.
- **Duração dos atendimentos:** somente pode ser calculada quando `inicio_atendimento` e `fim_atendimento` forem diferentes de `NULL`.

### Turnos de Atendimento

Para a classificação dos horários, são utilizadas as seguintes faixas:

| Turno | Horário    |
| :---- | :--------- |
| Manhã | 07h às 11h |
| Tarde | 12h às 17h |
| Noite | 18h às 22h |

> Os intervalos acima representam as faixas definidas nas regras de negócio do projeto.

---

## 📚 Glossário Técnico

| Termo                | Tradução                              | Aplicação no Projeto                                                                       |
| :------------------- | :------------------------------------ | :----------------------------------------------------------------------------------------- |
| **Table**            | Tabela                                | Estrutura onde os dados são armazenados em linhas e colunas, como `paciente` e `consulta`. |
| **Field**            | Campo / Coluna                        | Atributo de uma tabela, como o nome do paciente ou a data de agendamento.                  |
| **Primary Key (PK)** | Chave Primária                        | Identificador único de cada registro, como `id_consulta`.                                  |
| **Foreign Key (FK)** | Chave Estrangeira                     | Campo que referencia a chave primária de outra tabela, estabelecendo relacionamentos.      |
| **JDBC**             | Java Database Connectivity            | API utilizada pela aplicação Java para se conectar ao PostgreSQL.                          |
| **SGBD**             | Sistema Gerenciador de Banco de Dados | Software responsável pelo armazenamento e gerenciamento dos dados.                         |
| **SQL**              | Structured Query Language             | Linguagem utilizada para consultar e analisar os dados armazenados.                        |
| **Schema**           | Esquema                               | Estrutura lógica que organiza os objetos do banco de dados, como tabelas e consultas.      |
| **Ticket Médio**     | Indicador Financeiro                  | Valor médio cobrado por atendimento realizado com valor válido.                            |
| **Absenteísmo**      | Índice de Ausência                    | Indicador que representa a proporção de pacientes que faltaram aos atendimentos agendados. |

---

## 📈 Resultados Esperados

Com a implementação do módulo, espera-se que a gestão da clínica consiga:

- Identificar especialidades com maior procura.
- Avaliar a distribuição de atendimentos entre profissionais.
- Monitorar o índice de faltas dos pacientes.
- Acompanhar o faturamento e o ticket médio.
- Planejar melhor a utilização dos consultórios.
- Apoiar decisões relacionadas à agenda, infraestrutura e recursos humanos.

---

## 📌 Considerações Finais

O Módulo de Relatórios da UC6 integra conhecimentos de banco de dados, SQL, programação Java e análise de informações.

A utilização do PostgreSQL como fonte de dados, das consultas SQL para análise e da aplicação Java com JDBC permite disponibilizar informações operacionais e financeiras de maneira organizada.

O projeto demonstra como os dados de uma clínica podem ser transformados em indicadores úteis para apoiar a gestão e a tomada de decisões, contribuindo para uma melhor organização dos atendimentos e dos recursos disponíveis.

---

**Projeto acadêmico desenvolvido por Felipe Walker para a UC6 — Tec. em Desenvolvimento de Sistemas (TDS).**
