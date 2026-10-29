# UC6 — Questionário da Aula 1: exploração inicial da base

**Projeto Integrador:** análise de dados de uma clínica fictícia  
**Entrega:** individual  
**Formato:** preencher este arquivo e entregar na pasta individual do projeto.

## Identificação

- Nome: Felipe Walker
- Turma: TDS
- Data: 04/09/26

## 1. Acesso à base

1. Você conseguiu conectar-se ao PostgreSQL e executar o dump?
   - (x) Sim
   - ( ) Não — descreva o problema encontrado:

2. Qual é o nome do schema criado pelo dump?

   Resposta: clinica_pi_uc6

3. Inclua uma captura de tela que mostre a conexão e o schema aberto no DBeaver.

   Evidência: (https://i.postimg.cc/wB9z0tK4/img.png)

## 2. Inventário inicial das tabelas

Tabela: especialidade
O que armazena: As areas medicas da clinica, tipo Clinica Geral, Cardiologia e Psicologia.
Chave primaria: id_especialidade
Relacionamento percebido: Nao puxa chave de ninguem, ela é a base para as outras.

Tabela: convenio
O que armazena: Os planos de saude que a clinica aceita, como Particular, Vida Plena e Bem Estar.
Chave primaria: id_convenio
Relacionamento percebido: Tambem é independente, nao tem chave estrangeira.

Tabela: consultorio
O que armazena: As salas físicas onde os medicos atendem e a sala de teleconsulta.
Chave primaria: id_consultorio
Relacionamento percebido: Limpa tambem, serve de referencia para outras.

Tabela: profissional
O que armazena: Os medicos e psicologos que trabalham la e o CRM ou CRP deles.
Chave primaria: id_profissional
Relacionamento percebido: Se conecta com a tabela especialidade pelo campo id_especialidade.

Tabela: paciente
O que armazena: O cadastro das pessoas, com nome, data de nascimento, sexo, cidade e convenio.
Chave primaria: id_paciente
Relacionamento percebido: Se conecta com a tabela convenio pelo campo id_convenio.

Tabela: consulta
O que armazena: O historico de todas as consultas agendadas, realizadas, faltas ou canceladas.
Chave primaria: id_consulta
Relacionamento percebido: é a que mais tem nós, se ligando com paciente, profissional e consultorio ao mesmo tempo.

## 3. Exploração dos registros

1. Escolha duas tabelas principais e escreva abaixo o comando SQL que você utilizou para consultar cinco registros de cada uma.

**Tabela 1:**

Tabela 1: profissional
Comando SQL: SELECT \* FROM clinica_pi_uc6.profissional LIMIT 5;

**O que você observou nos resultados?**

Resposta: Vi que tem o nome dos doutores e das doutoras, a data em que começaram a trabalhar na clinica e o registro deles, que muda se for medico (CRM) ou psicologo (CRP). O campo id_especialidade vem só o numero, entao sozinho nao da para saber a especialidade.

**Tabela 2:**

Tabela 2: consulta
Comando SQL: SELECT \* FROM clinica_pi_uc6.consulta LIMIT 5;

**O que você observou nos resultados?**

Resposta: Essa tabela é enorme e cheia de colunas. Vi que quando o status é FALTOU ou CANCELADA, os campos de inicio_atendimento, fim_atendimento e valor_cobrado ficam todos em branco (null), o que faz sentido porque ninguém pagou e nem foi atendido.

2. Escolha uma coluna que pareça importante para uma análise futura. Explique por quê.

Resposta: Eu escolhi a coluna 'status' da tabela 'consulta'. Acho ela muito importante porque se a clinica tiver muitas consultas com status FALTOU, ela está perdendo dinheiro e deixando a sala vazia a toa. Analisar isso ajuda a decidir se precisam ligar antes para confirmar.

## 4. Perguntas de análise

Escreva três perguntas que a gestão da clínica poderia responder usando os dados disponíveis. Não responda às perguntas ainda: nesta etapa, o foco é formular perguntas úteis e indicar quais dados seriam necessários.

Pergunta de negocio 1: Qual e a especialidade medica que mais gera faturamento para a clinica?
Tabelas e campos: consulta (valor_cobrado, status), profissional (id_especialidade) e especialidade (nome).
Por que essa resposta seria util: Para saber onde vale a pena investir mais, contratar mais profissionais ou aumentar o espaco fisico.

Pergunta de negocio 2: Os pacientes de qual plano de saúde são os que mais faltam às consultas?
Tabelas e campos: consulta (status), paciente (id_convenio) e convenio (nome).
Por que essa resposta seria util: Para ver se algum convenio especifico tem um indice de falta muito alto e tentar entender o motivo.

Pergunta de negocio 3: Qual e o tempo medio de duracao das consultas presenciais em comparacao com as teleconsultas?
Tabelas e campos: consulta (inicio_atendimento, fim_atendimento, tipo_atendimento, status).
Por que essa resposta seria util: Ajuda a gerenciar a agenda dos consultorios. Se a teleconsulta for mais rapida, da para encaixar mais pacientes na mesma sala virtual.

## 5. Organização do projeto

Confirme a criação das pastas abaixo no seu projeto individual:

- [x] `documentacao/`
- [x] `sql/`
- [x] `evidencias/`
- [x] `java/`

## Checklist antes da entrega

- [x] Identificação preenchida.
- [x] Schema e conexão comprovados por evidência.
- [x] Inventário das seis tabelas preenchido.
- [x] Dois comandos SQL registrados.
- [x] Três perguntas de análise propostas.
- [x] Pastas iniciais do projeto criadas.
