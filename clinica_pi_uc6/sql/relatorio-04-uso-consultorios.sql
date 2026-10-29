-- Projeto Integrador UC6 - Módulo de Relatórios da Clínica
-- Relatório 4: Utilização de consultórios físicos e horários de pico
-- Autor: Felipe Walker
-- Turma: TDS
-- Pergunta de negócio:
-- Quais consultórios são mais utilizados e em quais horários?
-- Regra de negócio:
-- Considerar consultas marcadas/realizadas e extrair o turno/hora da consulta.

SET search_path TO clinica_pi_uc6, public;

-- ============================================================================
-- Parte A: Ocupação geral por Consultório físico
-- ============================================================================
SELECT
    cons.nome AS consultorio,
    cons.andar,
    COUNT(c.id_consulta) AS total_agendamentos,
    COUNT(CASE WHEN c.status = 'REALIZADA' THEN 1 END) AS consultas_realizadas,
    COUNT(CASE WHEN c.tipo_atendimento = 'PRESENCIAL' THEN 1 END) AS qtd_presencial,
    COUNT(CASE WHEN c.tipo_atendimento = 'TELECONSULTA' THEN 1 END) AS qtd_teleconsulta
FROM consultorio cons
LEFT JOIN consulta c 
    ON c.id_consultorio = cons.id_consultorio
GROUP BY cons.id_consultorio, cons.nome, cons.andar
ORDER BY total_agendamentos DESC;

-- ============================================================================
-- Parte B: Distribuição das consultas por Faixa de Horário (Turno)
-- ============================================================================
SELECT
    CASE
        WHEN EXTRACT(HOUR FROM c.data_hora_consulta) BETWEEN 7 AND 11 THEN 'Manhã (07h às 11h)'
        WHEN EXTRACT(HOUR FROM c.data_hora_consulta) BETWEEN 12 AND 17 THEN 'Tarde (12h às 17h)'
        ELSE 'Noite (18h às 22h)'
    END AS faixa_horario,
    COUNT(c.id_consulta) AS total_consultas,
    ROUND((COUNT(c.id_consulta)::numeric / (SELECT COUNT(*) FROM consulta)) * 100, 2) AS percentual_uso
FROM consulta c
GROUP BY faixa_horario
ORDER BY total_consultas DESC;

-- ============================================================================
-- Conclusão do estudante (Felipe Walker):
-- A maior concentração de consultas acontece no período da tarde, gerando o pico
-- de movimento na recepção e nas salas de espera. Consultórios dos primeiros andares
-- têm maior frequência de alocação de agenda.
--
-- Limitação ou cuidado de interpretação:
-- As teleconsultas também estão vinculadas a um consultório na base (porque o campo
-- id_consultorio é NOT NULL), então a sala pode parecer ocupada no sistema mesmo sendo um
-- atendimento realizado pelo computador do profissional.
-- ============================================================================