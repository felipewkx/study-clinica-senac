# Sistema de Gestão Hospitalar - Módulo de Relatórios Clínicos

Este módulo foi desenvolvido para transformar dados operacionais da clínica em relatórios analíticos de apoio à gestão hospitalar. A solução consolida consultas estruturadas em banco de dados e uma interface interativa no terminal em Java para fornecer indicadores claros que auxiliam no planejamento de agendas, contratações, faturamento e otimização de salas.

## 📊 Relatórios Disponíveis

| Relatório                    | Indicadores e Perguntas de Negócio                                                        |
| :--------------------------- | :---------------------------------------------------------------------------------------- |
| **1. Demanda Operacional**   | Identificação de especialidades e profissionais com maior volume de consultas realizadas. |
| **2. Absenteísmo (Faltas)**  | Concentração e taxa de ausência de pacientes por convênio e especialidade.                |
| **3. Desempenho Financeiro** | Análise de faturamento total, volume de atendimentos e ticket médio.                      |
| **4. Ocupação de Espaços**   | Mapeamento dos consultórios mais utilizados e seus respectivos horários de pico.          |

## 🛠️ Tecnologias Utilizadas

- **Linguagem Principal:** Java 21
- **Gerenciador de Dependências:** Maven (opcional para execução)
- **Banco de Dados:** PostgreSQL
- **Driver de Conexão:** JDBC

## 🚀 Como Executar o Projeto

A aplicação já acompanha todas as dependências necessárias e um script de inicialização rápida automatizado para o Windows.

### Execução Rápida (Recomendado)

1. Navegue até o diretório do projeto: `java/uc6-jdbc-base/`.
2. Dê um duplo clique no arquivo executável **`executar-interface.bat`**.
3. O painel hospitalar será iniciado instantaneamente no terminal, realizando a conexão segura com o banco de dados.

### Execução Manual via Terminal (Alternativa)

Se preferir rodar manualmente pelo Git Bash ou Prompt de Comando, certifique-se de estar dentro da pasta `java/uc6-jdbc-base` e execute:

```bash
java -jar target/relatorios-clinica.jar
```

---

## 🗄️ Estrutura do Banco de Dados e Scripts SQL

Para fins de auditoria, consultas manuais ou geração de relatórios diretos no cliente de banco de dados (como DBeaver), os arquivos fonte estão localizados na pasta `sql/`.

### Regras de Negócio Implementadas

- **Consultas Efetivadas:** Filtradas estritamente através do estado `status = 'REALIZADA'`.
- **Ausências e Absenteísmo:** Identificadas e calculadas pelo estado `status = 'FALTOU'`.
- **Cálculo de Produtividade:** Métricas de tempo e duração de atendimento desconsideram registros onde os campos de marcação temporal (`inicio_atendimento` e `fim_atendimento`) são nulos.
- **Faturamento Real:** Consultas canceladas ou faltas não contabilizam valores financeiros (`valor_cobrado IS NULL`).
