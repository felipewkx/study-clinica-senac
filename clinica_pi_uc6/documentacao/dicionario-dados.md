# UC6 — Modelo de dicionário de dados

**Projeto Integrador:** Análise de dados de uma clínica fictícia  
**Estudante:** Felipe Walker  
**Turma:** TDS  
**Data:** 11 / 09 / 2026

## Orientações

1. Use o DBeaver e o DER gerado a partir do schema `clinica_pi_uc6`.
2. Preencha o dicionário com suas próprias palavras; não basta copiar apenas o tipo técnico do campo.
3. Registre todos os campos das tabelas. Priorize a explicação dos campos que serão úteis nas análises do projeto.
4. Em **Chave/relacionamento**, informe `PK` para chave primária e `FK -> tabela(campo)` para chave estrangeira. Nos demais campos, use `—`.
5. Salve o arquivo com o nome `dicionario-dados-nome-sobrenome.md` e entregue junto ao DER exportado pelo DBeaver.

## Legenda rápida

| Sigla       | Significado                                                        |
| ----------- | ------------------------------------------------------------------ |
| PK          | Chave primária: identifica unicamente cada registro.               |
| FK          | Chave estrangeira: aponta para um registro de outra tabela.        |
| Obrigatório | Campo que não aceita valor vazio (`NOT NULL`).                     |
| Regra       | Restrição, valor padrão ou conjunto de valores aceitos pelo banco. |

## 1. Visão geral das tabelas

| Tabela          | Função da tabela no sistema                                                         | Classificação: operacional ou apoio? |
| --------------- | ----------------------------------------------------------------------------------- | ------------------------------------ |
| `especialidade` | Guarda as especialidades médicas dos profissionais (ex: Pediatria, Cardiologia).    | Apoio                                |
| `convenio`      | Cadastra os planos de saúde aceitos e a opção particular da clínica.                | Apoio                                |
| `consultorio`   | Armazena as salas físicas onde as consultas acontecem, seus andares e capacidade.   | Apoio                                |
| `profissional`  | Guarda os dados dos médicos e profissionais que atendem na clínica.                 | Operacional                          |
| `paciente`      | Guarda os dados cadastrais dos pacientes atendidos pela clínica.                    | Operacional                          |
| `consulta`      | Registra as consultas médicas, seus agendamentos, status e horários de atendimento. | Operacional                          |

## 2. Campos da tabela `especialidade`

| Campo              | Tipo no banco  | Obrigatório? | Chave/relacionamento | Significado para o negócio                         | Regra ou observação                |
| ------------------ | -------------- | ------------ | -------------------- | -------------------------------------------------- | ---------------------------------- |
| `id_especialidade` | `int4`         | Sim          | PK                   | Código identificador único da especialidade.       | Gerado automaticamente (Identity). |
| `nome`             | `varchar(80)`  | Sim          | —                    | Nome da área médica ou especialidade.              | Valor único (não pode repetir).    |
| `descricao`        | `varchar(255)` | Sim          | —                    | Breve explicação do que essa especialidade atende. | Preenchimento obrigatório.         |

## 3. Campos da tabela `convenio`

| Campo         | Tipo no banco | Obrigatório? | Chave/relacionamento | Significado para o negócio                         | Regra ou observação                                |
| ------------- | ------------- | ------------ | -------------------- | -------------------------------------------------- | -------------------------------------------------- |
| `id_convenio` | `int4`        | Sim          | PK                   | Identificador único de cada convênio.              | Gerado automaticamente (Identity).                 |
| `nome`        | `varchar(80)` | Sim          | —                    | Nome do plano ou empresa (ex: Unimed, Particular). | Valor único (não pode repetir).                    |
| `tipo`        | `varchar(20)` | Sim          | —                    | Categoria do convênio.                             | Só aceita: 'PARTICULAR', 'SAUDE' ou 'EMPRESARIAL'. |
| `ativo`       | `bool`        | Sim          | —                    | Diz se o convênio ainda é aceito na clínica.       | Padrão é `true` (ativo).                           |

## 4. Campos da tabela `consultorio`

| Campo            | Tipo no banco | Obrigatório? | Chave/relacionamento | Significado para o negócio                                    | Regra ou observação                |
| ---------------- | ------------- | ------------ | -------------------- | ------------------------------------------------------------- | ---------------------------------- |
| `id_consultorio` | `int4`        | Sim          | PK                   | Identificador único da sala/consultório.                      | Gerado automaticamente (Identity). |
| `nome`           | `varchar(50)` | Sim          | —                    | Nome ou número de identificação da sala (ex: Consultório 01). | Valor único (não pode repetir).    |
| `andar`          | `int2`        | Sim          | —                    | Andar do prédio onde fica a sala.                             | Deve ser entre 1 e 10.             |
| `capacidade`     | `int2`        | Sim          | —                    | Quantidade de pessoas que cabem na sala.                      | Padrão é 1 e deve ser maior que 0. |
| `ativo`          | `bool`        | Sim          | —                    | Informa se a sala pode ser usada para atendimentos.           | Padrão é `true` (ativo).           |

## 5. Campos da tabela `profissional`

| Campo                   | Tipo no banco  | Obrigatório? | Chave/relacionamento                  | Significado para o negócio                                 | Regra ou observação                     |
| ----------------------- | -------------- | ------------ | ------------------------------------- | ---------------------------------------------------------- | --------------------------------------- |
| `id_profissional`       | `int4`         | Sim          | PK                                    | Identificador único do profissional de saúde.              | Gerado automaticamente (Identity).      |
| `nome`                  | `varchar(120)` | Sim          | —                                     | Nome completo do médico ou profissional.                   | Não aceita nulo.                        |
| `registro_profissional` | `varchar(30)`  | Sim          | —                                     | Número do CRM, CRO ou registro no conselho.                | Valor único (não pode ter repetido).    |
| `id_especialidade`      | `int4`         | Sim          | FK -> especialidade(id_especialidade) | Código da especialidade que o profissional atua.           | Deve existir na tabela `especialidade`. |
| `data_admissao`         | `date`         | Sim          | —                                     | Data em que o profissional começou a trabalhar na clínica. | Formato de data obrigatório.            |
| `ativo`                 | `bool`         | Sim          | —                                     | Diz se o profissional ainda faz atendimentos na clínica.   | Padrão é `true` (ativo).                |

## 6. Campos da tabela `paciente`

| Campo             | Tipo no banco  | Obrigatório? | Chave/relacionamento        | Significado para o negócio                                 | Regra ou observação                |
| ----------------- | -------------- | ------------ | --------------------------- | ---------------------------------------------------------- | ---------------------------------- |
| `id_paciente`     | `int4`         | Sim          | PK                          | Identificador único do paciente.                           | Gerado automaticamente (Identity). |
| `nome`            | `varchar(120)` | Sim          | —                           | Nome completo do paciente.                                 | Não aceita nulo.                   |
| `data_nascimento` | `date`         | Sim          | —                           | Data em que o paciente nasceu (para calcular a idade).     | Deve ser menor que a data atual.   |
| `sexo`            | `bpchar(1)`    | Sim          | —                           | Sexo biológico registrado do paciente.                     | Só aceita 'F', 'M' ou 'X'.         |
| `cidade`          | `varchar(80)`  | Sim          | —                           | Cidade onde o paciente mora (ajuda na análise geográfica). | Preenchimento obrigatório.         |
| `id_convenio`     | `int4`         | Sim          | FK -> convenio(id_convenio) | Convênio padrão do paciente.                               | Deve existir na tabela `convenio`. |
| `data_cadastro`   | `date`         | Sim          | —                           | Data em que o paciente se cadastrou na clínica.            | Formato de data obrigatório.       |
| `ativo`           | `bool`         | Sim          | —                           | Indica se o cadastro do paciente está ativo.               | Padrão é `true` (ativo).           |

## 7. Campos da tabela `consulta`

| Campo                | Tipo no banco    | Obrigatório? | Chave/relacionamento                | Significado para o negócio                               | Regra ou observação                                            |
| -------------------- | ---------------- | ------------ | ----------------------------------- | -------------------------------------------------------- | -------------------------------------------------------------- |
| `id_consulta`        | `int8`           | Sim          | PK                                  | Identificador único da consulta/atendimento.             | Gerado automaticamente (Identity, bigint).                     |
| `id_paciente`        | `int4`           | Sim          | FK -> paciente(id_paciente)         | Código do paciente atendido.                             | Deve existir na tabela `paciente`.                             |
| `id_profissional`    | `int4`           | Sim          | FK -> profissional(id_profissional) | Código do médico/profissional que atendeu.               | Deve existir na tabela `profissional`.                         |
| `id_consultorio`     | `int4`           | Sim          | FK -> consultorio(id_consultorio)   | Código da sala onde ocorreu o atendimento.               | Deve existir na tabela `consultorio`.                          |
| `data_agendamento`   | `timestamp`      | Sim          | —                                   | Data e hora de quando a consulta foi marcada no sistema. | Deve ser menor ou igual à data da consulta.                    |
| `data_hora_consulta` | `timestamp`      | Sim          | —                                   | Data e horário previstos para a consulta acontecer.      | Base para a agenda do médico.                                  |
| `status`             | `varchar(15)`    | Sim          | —                                   | Situação atual da consulta.                              | Aceita apenas: 'AGENDADA', 'REALIZADA', 'FALTOU', 'CANCELADA'. |
| `tipo_atendimento`   | `varchar(20)`    | Sim          | —                                   | Formato em que a consulta ocorreu.                       | Aceita apenas: 'PRESENCIAL' ou 'TELECONSULTA'.                 |
| `valor_cobrado`      | `numeric(10, 2)` | Não          | —                                   | Valor cobrado pela consulta em reais.                    | Opcional (pode ser nulo).                                      |
| `inicio_atendimento` | `timestamp`      | Não          | —                                   | Data e hora exata em que o médico começou a atender.     | Pode ser nulo; se preenchido, exige `fim_atendimento`.         |
| `fim_atendimento`    | `timestamp`      | Não          | —                                   | Data e hora exata em que o atendimento acabou.           | Pode ser nulo; deve ser igual ou maior que o início.           |
| `observacao`         | `varchar(255)`   | Não          | —                                   | Anotações adicionais ou justificativas da consulta.      | Opcional (aceita nulo).                                        |

## 8. Relacionamentos identificados

| Tabela de origem | Campo FK           | Tabela de destino | Campo referenciado | Cardinalidade percebida | Explicação em linguagem de negócio                                                                                     |
| ---------------- | ------------------ | ----------------- | ------------------ | ----------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `paciente`       | `id_convenio`      | `convenio`        | `id_convenio`      | N:1                     | Vários pacientes podem ter o mesmo convênio cadastrado, mas cada paciente aponta para um único convênio.               |
| `profissional`   | `id_especialidade` | `especialidade`   | `id_especialidade` | N:1                     | Vários profissionais podem ter a mesma especialidade, mas cada profissional possui uma especialidade principal.        |
| `consulta`       | `id_paciente`      | `paciente`        | `id_paciente`      | N:1                     | Um paciente pode ter várias consultas marcadas na clínica ao longo do tempo, mas a consulta pertence a um só paciente. |
| `consulta`       | `id_profissional`  | `profissional`    | `id_profissional`  | N:1                     | Um profissional realiza diversas consultas na rotina, mas cada consulta é conduzida por um profissional específico.    |
| `consulta`       | `id_consultorio`   | `consultorio`     | `id_consultorio`   | N:1                     | Uma sala (consultório) recebe muitas consultas ao longo dos dias, mas a consulta é alocada em uma sala.                |

## 9. Análise inicial

1. Qual tabela registra o fato principal que será analisado no projeto? Por quê?

   **Resposta:** É a tabela `consulta`. Porque ela é a tabela onde ficam guardados todos os atendimentos do dia a dia da clínica. É por ela que dá para ver a data da consulta, o médico, o paciente, o preço, o tempo de duração e se a pessoa foi ou faltou.

2. Cite três campos que podem ajudar a responder perguntas sobre atendimentos, faltas ou demanda.

   **Resposta:**
   - `status`: Serve para contar quantas pessoas foram, quantas cancelaram e ver a taxa de faltas.
   - `data_hora_consulta`: Ajuda a descobrir os dias e horários de pico que dão mais movimento na clínica.
   - `tipo_atendimento`: Mostra a diferença de procura entre as consultas presenciais e as por teleconsulta.

3. Cite duas dúvidas, inconsistências ou regras da base que precisam ser verificadas antes de analisar os dados.

   **Resposta:**
   - O campo valor_cobrado aceita valor nulo (NULL): Tem que ver se esses nulos são só de consultas canceladas ou se as consultas por plano de saúde também estão ficando em branco.
   - O campo id_consultorio é obrigatório (NOT NULL): Precisa checar o que acontece quando a consulta é online (TELECONSULTA), para ver se o sistema está gastando uma sala física à toa ou se criaram uma sala virtual de mentira.

## Checklist antes da entrega

- [x] Preenchi todos os campos das seis tabelas.
- [x] Marquei corretamente as PKs e FKs.
- [x] Expliquei os relacionamentos em linguagem de negócio.
- [x] Registrei regras importantes, como valores permitidos e obrigatoriedade.
- [x] Anexei ou salvei o DER exportado pelo DBeaver.
