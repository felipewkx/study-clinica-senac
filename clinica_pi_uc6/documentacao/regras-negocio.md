# UC6 — Aula 3: documentação técnica e regras de negócio

**Estudante:** Felipe Walker  
**Turma:** TDS  
**Data:** 18 / 09 / 2026

## Parte 1 — Glossário técnico

| Termo em inglês           | Tradução em português              | Onde aparece ou como é usado no projeto?                                                 |
| ------------------------- | ---------------------------------- | ---------------------------------------------------------------------------------------- |
| `table`                   | tabela                             | Onde os dados ficam guardados no banco, como as tabelas de paciente e consulta.          |
| `field`                   | campo / coluna                     | É uma informação ou coluna dentro da tabela, tipo o nome ou a data.                      |
| `primary key`             | chave primária                     | O código identificador que não se repete, como o `id_paciente` ou `id_consulta`.         |
| `foreign key`             | chave estrangeira                  | Campo que puxa dados de outra tabela, tipo o `id_convenio` dentro da tabela paciente.    |
| `required`                | obrigatório                        | Dado que não pode ficar em branco na hora de salvar (o `NOT NULL` do banco).             |
| `nullable`                | opcional / aceita nulo             | Campo que pode ficar vazio se a gente não tiver a informação, como o campo `observacao`. |
| `constraint`              | restrição / regra                  | Regra do banco para não deixar cadastrar dados errados ou fora do padrão.                |
| `appointment`             | consulta / agendamento             | Representa cada consulta marcada que fica registrada na tabela `consulta`.               |
| `attendance`              | atendimento                        | O momento em que o paciente é atendido de fato pelo médico.                              |
| `no-show rate`            | taxa de falta (não comparecimento) | Indicador para saber quantos pacientes faltaram, usando o status `FALTOU`.               |
| `healthcare professional` | profissional de saúde              | Os médicos e profissionais que atendem na clínica (tabela `profissional`).               |
| `insurance plan`          | plano de saúde / convênio          | Os convênios aceitos pela clínica cadastrados na tabela `convenio`.                      |

## Parte 2 — Resumo da documentação

Essa base de dados foi feita para guardar e organizar as informações de uma clínica médica, servindo para gerar relatórios e análises do negócio.
Ela armazena dados sobre os pacientes, os profissionais de saúde, as especialidades, os convênios, as salas e as consultas.
A tabela `consulta` é a mais importante de todas porque ela é o coração da clínica e junta tudo: mostra qual paciente foi atendido, quem foi o médico, em qual sala aconteceu e quanto custou.
Sem essa tabela a gente não conseguiria tirar métricas básicas do dia a dia, como saber quantos pacientes faltaram, quais médicos trabalham mais ou quanto a clínica faturou no mês.

## Parte 3 — Regras de negócio identificadas

| Regra de negócio em português                                                                               | Tabela/campo relacionado                                                       | Evidência encontrada                                                                                                                                    | O que pode acontecer se a regra for ignorada?                                                                                                           |
| ----------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| A data em que a consulta foi marcada não pode ser depois do dia/horário da própria consulta.                | Tabela `consulta`, campos `data_agendamento` e `data_hora_consulta`.           | Documentação (regra 2) e no banco: `CONSTRAINT ck_consulta_agendamento CHECK ((data_agendamento <= data_hora_consulta))`.                               | O sistema pode ter consultas marcadas com datas impossíveis (agendar para o passado), bagunçando os relatórios de agendamento.                          |
| Se preencher o início do atendimento, tem que preencher o fim também, e o fim não pode ser antes do início. | Tabela `consulta`, campos `inicio_atendimento` e `fim_atendimento`.            | Documentação (regras 3 e 4) e no banco: `CONSTRAINT ck_consulta_periodo CHECK (...)`.                                                                   | Vai dar erro na hora de calcular quanto tempo durou a consulta, podendo gerar duração em branco ou até tempo negativo.                                  |
| Toda consulta tem que ter obrigatoriamente um paciente, um profissional e uma sala cadastrados.             | Tabela `consulta`, campos `id_paciente`, `id_profissional` e `id_consultorio`. | Documentação (regra 1) e no banco todos esses campos estão como `NOT NULL` e são `FOREIGN KEY`.                                                         | Podem surgir consultas "fantasmas" no sistema sem saber quem é o paciente ou quem é o médico, quebrando as buscas do banco.                             |
| O status da consulta só aceita quatro opções: AGENDADA, REALIZADA, FALTOU ou CANCELADA.                     | Tabela `consulta`, campo `status`.                                             | Documentação (tabela de status) e no banco: `CONSTRAINT ck_consulta_status CHECK (...)`.                                                                | Cada funcionário pode digitar de um jeito diferente (como "concluída" ou "não veio"), e na hora de fazer o `SELECT` o filtro não vai funcionar direito. |
| O andar do consultório tem que ser entre o 1º e o 10º, e a capacidade da sala tem que ser maior que zero.   | Tabela `consultorio`, campos `andar` e `capacidade`.                           | No banco: `CONSTRAINT ck_consultorio_andar CHECK (((andar >= 1) AND (andar <= 10)))` e `CONSTRAINT ck_consultorio_capacidade CHECK ((capacidade > 0))`. | Podem cadastrar consultórios em andares que não existem (tipo andar -2 ou 50) ou sala com capacidade zero onde ninguém cabe.                            |

## Parte 4 — Verificação para as próximas consultas

1. Qual `status` deve ser usado para contar atendimentos concluídos?  
   Devemos usar o status `'REALIZADA'`.

2. Quais campos precisam estar preenchidos para calcular a duração real de um atendimento?  
   Os campos `inicio_atendimento` e `fim_atendimento` (nenhum dos dois pode estar nulo).

3. Escreva uma dúvida que você ainda tem sobre os dados da clínica.  
   Se o atendimento for online (`TELECONSULTA`), por que ainda é obrigatório preencher um `id_consultorio` físico, e o que impede dois médicos de marcarem a mesma sala no mesmo horário?

## Checklist de entrega

- [x] Preenchi 12 ou mais termos no glossário.
- [x] Produzi um resumo em português.
- [x] Registrei pelo menos cinco regras de negócio.
- [x] Relacionei cada regra a uma tabela ou campo.
- [x] Salvei o arquivo com meu nome (`aula-03-documentacao-felipe-walker.md`).
