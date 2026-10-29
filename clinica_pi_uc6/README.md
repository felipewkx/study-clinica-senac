# 🏥 Sistema de Gestão Hospitalar — Módulo de Relatórios (UC6)

Módulo desenvolvido para transformar dados operacionais de uma clínica fictícia em indicadores e relatórios analíticos de apoio à tomada de decisão. A aplicação em Java conecta-se ao banco de dados PostgreSQL via JDBC e disponibiliza modo terminal interativo e interface gráfica executiva.

---

## 📊 Relatórios Analíticos Disponíveis

|  Nº   | Relatório                 | Pergunta / Decisão de Negócio Atendida                                               |
| :---: | :------------------------ | :----------------------------------------------------------------------------------- |
| **1** | **Demanda Operacional**   | Quais especialidades e médicos possuem maior volume de atendimentos realizados?      |
| **2** | **Absenteísmo (Faltas)**  | Qual o índice e a taxa de ausência de pacientes por convênio e por especialidade?    |
| **3** | **Desempenho Financeiro** | Qual o faturamento total apurado e o ticket médio das consultas concluídas?          |
| **4** | **Ocupação de Espaços**   | Quais consultórios físicos são mais demandados e quais as faixas de horário de pico? |

---

## ⚙️ Pré-requisitos

1. **Java JDK 17 ou superior** instalado no computador.
2. **PostgreSQL** ativo com a base da clínica carregada.
3. Se necessário, confira o usuário e a senha do seu banco de dados local no arquivo:  
   `clinica_pi_uc6/java/uc6-jdbc-base/src/main/java/br/senac/uc6/ConfigBanco.java`.

---

## 🚀 Como Executar o Projeto

Você tem duas maneiras práticas de rodar a aplicação:

---

### Opção 1: Pelo Terminal do VS Code (Direto da Raiz — Sem `cd`)

Ao abrir a pasta `study-clinica-senac` no VS Code, abra o terminal integrado (`Ctrl + '` ou menu **Terminal > New Terminal**) e execute os comandos abaixo diretamente:

#### 1. Compilar o projeto:

```bash
mkdir -p clinica_pi_uc6/java/uc6-jdbc-base/target/classes
javac -cp "clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" -d clinica_pi_uc6/java/uc6-jdbc-base/target/classes clinica_pi_uc6/java/uc6-jdbc-base/src/main/java/br/senac/uc6/*.java
```

#### 2. Executar a aplicação:

- **Modo Terminal Interativo (Menu com opções 0 a 5):**

  ```bash
  java -cp "clinica_pi_uc6/java/uc6-jdbc-base/target/classes;clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" br.senac.uc6.Main
  ```

- **Modo Interface Gráfica Dark (Painel Executivo Swing):**
  ```bash
  java -cp "clinica_pi_uc6/java/uc6-jdbc-base/target/classes;clinica_pi_uc6/java/uc6-jdbc-base/lib/postgresql-42.7.11.jar" br.senac.uc6.Main --gui
  ```

---

### Opção 2: Execução Rápida via `.bat` (Windows Explorer)

Se preferir não usar o terminal para abrir o painel gráfico:

1. No explorador de arquivos do Windows, navegue até a pasta:
   ```text
   clinica_pi_uc6\java\uc6-jdbc-base\
   ```
2. Dê um duplo clique no arquivo **`executar-interface.bat`**.
   > _O script compila os arquivos automaticamente (caso ainda não estejam compilados) e abre a tela da clínica diretamente._

---

## 🗄️ Estrutura das Consultas SQL e Documentação

- **Scripts SQL Analíticos:** pasta `clinica_pi_uc6/sql/` (prontos para execução no DBeaver).
- **Dicionário de Dados e Regras de Negócio:** pasta `clinica_pi_uc6/documentacao/`.

### Regras de Negócio Aplicadas nas Consultas:

- **Consultas Realizadas:** Filtradas estritamente por `status = 'REALIZADA'`.
- **Faltas / Absenteísmo:** Identificadas por `status = 'FALTOU'`.
- **Faturamento e Ticket Médio:** Calculados somente com base em atendimentos realizados com valor válido, ignorando nulos gerados por faltas e cancelamentos.
- **Turnos de Atendimento:** Manhã (07h às 11h), Tarde (12h às 17h) e Noite (18h às 22h).
