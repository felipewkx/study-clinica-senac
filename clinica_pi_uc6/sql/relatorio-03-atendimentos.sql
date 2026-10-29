-- Projeto Integrador UC6 - Módulo de Relatórios da Clínica
-- Relatório 3: Volume realizado, faturamento e ticket médio
-- Autor: Felipe Walker
-- Turma: TDS
-- Pergunta de negócio:
-- Qual especialidade, profissional ou convênio gera maior faturamento e volume realizado?
-- Regra de negócio:
-- Considerar apenas consultas com status = 'REALIZADA' e valor_cobrado preenchido.

SET search_path TO clinica_pi_uc6, public;

-- ============================================================================
-- Parte A: Faturamento e volume por Especialidade
-- ============================================================================
SELECT
    e.nome AS especialidade,
    COUNT(c.id_consulta) AS atendimentos_realizados,
    SUM(c.valor_cobrado) AS faturamento_total,
    ROUND(AVG(c.valor_cobrado), 2) AS ticket_medio
FROM consulta c
JOIN profissional p 
    ON p.id_profissional = c.id_profissional
JOIN especialidade e 
    ON e.id_especialidade = p.id_especialidade
WHERE c.status = 'REALIZADA'
GROUP BY e.nome
ORDER BY faturamento_total DESC;

-- ============================================================================
-- Parte B: Faturamento e volume por Profissional
-- ============================================================================
SELECT
    p.nome AS profissional,
    e.nome AS especialidade,
    COUNT(c.id_consulta) AS atendimentos_realizados,
    SUM(c.valor_cobrado) AS faturamento_total,
    ROUND(AVG(c.valor_cobrado), 2) AS ticket_medio
FROM consulta c
JOIN profissional p 
    ON p.id_profissional = c.id_profissional
JOIN especialidade e 
    ON e.id_especialidade = p.id_especialidade
WHERE c.status = 'REALIZADA'
GROUP BY p.nome, e.nome
ORDER BY faturamento_total DESC;

-- ============================================================================
-- Parte C: Faturamento e volume por Convênio
-- ============================================================================
SELECT
    conv.nome AS convenio,
    conv.tipo AS tipo_convenio,
    COUNT(c.id_consulta) AS atendimentos_realizados,
    SUM(c.valor_cobrado) AS faturamento_total,
    ROUND(AVG(c.valor_cobrado), 2) AS ticket_medio
FROM consulta c
JOIN paciente pac 
    ON pac.id_paciente = c.id_paciente
JOIN convenio conv 
    ON conv.id_convenio = pac.id_convenio
WHERE c.status = 'REALIZADA'
GROUP BY conv.nome, conv.tipo
ORDER BY faturamento_total DESC;

-- ============================================================================
-- Conclusão do estudante (Felipe Walker):
-- A especialidade de Clínica Geral tem o maior volume de atendimentos, mas
-- especialidades como Cardiologia geram um faturamento proporcionalmente muito alto
-- por terem valor de consulta mais elevado (ticket médio maior).
--
-- Limitação ou cuidado de interpretação:
-- Os valores registrados representam o valor bruto cobrado pela consulta.
-- A query não deduz repasses médicos, custos de sala ou impostos da clínica.
-- ============================================================================