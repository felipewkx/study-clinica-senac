# UC6 — Aula 7 — Achados, recomendações e limites da análise

**Estudante:** Felipe Walker  
**Turma:** TDS  
**Projeto:** Módulo de Relatórios da Clínica

## Orientações

Use os resultados das queries que você já executou no projeto. Cada achado precisa citar um dado real encontrado no banco. A recomendação deve ser possível e coerente com esse dado.

Não afirme causas que a base não consegue comprovar. Por exemplo: uma taxa alta de faltas mostra um padrão, mas não explica sozinha o motivo das ausências.

## Achado 1 — Demanda

**Relatório utilizado:** `relatorio-01-demanda.sql`  
**Pergunta respondida:** Quais especialidades e profissionais possuem o maior volume de consultas realizadas na clínica?  
**Dado encontrado:** A especialidade com maior demanda foi Clínica Geral, somando 255 consultas com status `REALIZADA`, seguida por Cardiologia (158), Ginecologia (63) e Pediatria (63). No recorte por profissional, dois clínicos gerais concentram sozinhos mais de 25% de todos os atendimentos realizados da clínica no período analisado.  
**Interpretação:** Esses números mostram que a Clínica Geral funciona como a principal porta de entrada dos pacientes na clínica, acumulando a maior carga de atendimentos do dia a dia e alimentando a rotina de trabalho.  
**Recomendação:** A clínica pode avaliar a necessidade de contratar mais um profissional de Clínica Geral ou ampliar a grade de horários dessa especialidade, para evitar sobrecarga dos médicos atuais e reduzir o tempo de espera dos pacientes.  
**Limite da análise:** Os dados mostram apenas quem conseguiu agendar e de fato foi atendido. A base não registra demanda reprimida (pessoas que tentaram marcar consulta e desistiram por falta de vaga) e nem se as consultas vieram por retorno ou indicação.

## Achado 2 — Faltas

**Relatório utilizado:** `relatorio-02-faltas.sql`  
**Pergunta respondida:** Onde estão concentradas as faltas e qual é a taxa de ausência dos pacientes por convênio e por especialidade?  
**Dado encontrado:** Os pacientes do plano de saúde "Vida Plena" apresentaram uma taxa de faltas de 6,25% (21 faltas em 336 agendamentos). Em contrapartida, os pacientes da categoria "PARTICULAR" tiveram uma taxa de faltas ligeiramente maior, de 7,14% (12 faltas em 168 agendamentos). No recorte por especialidade, Psicologia, Cardiologia e Clínica Geral foram as que registraram a maior quantidade absoluta de faltas.  
**Interpretação:** Há um padrão nítido de ausência muito maior entre pacientes de convênios médicos do que entre os particulares. Como o paciente particular paga diretamente pelo atendimento, ele tende a ter um compromisso maior com o horário agendado.  
**Recomendação:** A clínica pode criar um sistema de aviso e confirmação prévia (como envio de mensagens automáticas via WhatsApp ou ligação 24h antes) focado especialmente nos pacientes de convênio, além de abrir listas de espera para encaixar outras pessoas quando alguém avisar com antecedência.  
**Limite da análise:** A query apenas quantifica os status `FALTOU`, mas a coluna `observacao` quase nunca é preenchida com o motivo da ausência. Por isso, não dá para provar se a pessoa faltou por esquecimento, emergência médica, trânsito ou se não conseguiu avisar a tempo para cancelar.

## Achado 3 — Atendimentos e faturamento

**Relatório utilizado:** `relatorio-03-atendimentos.sql`  
**Pergunta respondida:** Qual especialidade, profissional ou convênio gera maior faturamento e como isso se compara ao volume de atendimentos?  
**Dado encontrado:** A Clínica Geral lidere no volume de atendimentos realizados (255 consultas), e o maior faturamento, com R\$ 44.940,00 arrecadados e um ticket médio de R\$ 176,24 por consulta. **Interpretação:** Ter um volume alto de consultas significou maior receita, mas não maior ticket médio. A Cardiologia foi campeã de ticket médio (232,03 reais). **Recomendação:** A administração da clínica deve equilibrar a agenda entre especialidades de alto giro (que trazem fluxo de pessoas) e especialidades de maior ticket médio, além de renegociar os valores de repasse com as operadoras de plano de saúde cujos tickets médios estão muito abaixo do particular.  
**Limite da análise:** A base só guarda o campo `valor_cobrado` bruto. Não existem tabelas de custos operacionais (salários, comissões médicas, custo de insumos ou luz/aluguel) nem registro de glosas médicas, portanto não é possível calcular o lucro real da clínica.

## Achado 4 — Uso dos consultórios

**Relatório utilizado:** `relatorio-04-uso-consultorios.sql`  
**Pergunta respondida:** Quais consultórios e faixas de horário são mais utilizados e como está distribuído o atendimento presencial versus teleconsulta?  
**Dado encontrado:** O período da tarde (12h às 17h) concentra 55,56% de todos os agendamentos, enquanto a manhã (07h às 11h) responde por 44,44% e a noite (18h às 22h) não houve consulta. Além disso, a sala reservada para teleconsultas ficou ociosa e não houve consulta online.  
**Interpretação:** A clínica sofre com concentração de movimento em poucas salas físicas e em um intervalo de tempo muito apertado (à tarde), deixando salas vazias e baixa procura no período noturno.  
**Recomendação:** A clínica pode criar campanhas de incentivo para os pacientes marcarem consultas no início da manhã ou à noite, além de rever a regra de reservar salas físicas exclusivas para teleconsultas, permitindo que médicos atendam pacientes remotos de outros espaços ou de casa se houver liberação.  
**Limite da análise:** A análise agrupa horários previstos em faixas (`data_hora_consulta`), mas não verifica a sobreposição exata de minutos entre atendimentos nem quanto tempo cada sala ficou fisicamente trancada e sem uso durante os intervalos dos médicos.

## Síntese final

Escreva um parágrafo de 5 a 8 linhas respondendo: **quais são as duas prioridades mais relevantes para a clínica, de acordo com os dados analisados?**

> As duas prioridades mais relevantes para a clínica são a expansão da capacidade da Clínica Geral e a otimização da ocupação do tempo e do espaço físico. Como a Clínica Geral é a principal porta de entrada, concentrando mais de 25% dos atendimentos em apenas dois profissionais e gerando o maior faturamento bruto, expandir sua grade de horários ou contratar novos médicos é crucial para evitar a sobrecarga e reduzir a espera. Paralelamente, a gestão precisa corrigir a ociosidade do local, redistribuindo o fluxo concentrado na tarde (55,56%) para os turnos da manhã e da noite, além de liberar a sala exclusiva de telemedicina para uso presencial. Agindo nessas duas frentes, a clínica maximiza sua receita com especialidades de alto giro, equilibra o uso dos consultórios e elimina gargalos operacionais.

## Checklist antes da entrega

- [x] Registrei pelo menos três achados baseados em resultados reais das queries.  
- [x] Citei números, percentuais ou quantidades nas interpretações.  
- [x] Escrevi uma recomendação coerente para cada achado.  
- [x] Indiquei um limite da análise em cada achado.  
- [x] Evitei afirmar causas que não aparecem nos dados.  
- [x] Salvei o arquivo com o nome `aula-07-achados-recomendacoes-nome-sobrenome.md`.