-- Projeto Integrador UC6 - Módulo de Relatórios da Clínica
-- Relatório 2: Análise de faltas e taxa de ausência (No-show)
-- Autor: Felipe Walker
-- Turma: TDS
-- Pergunta de negócio:
-- Onde estão concentradas as faltas e qual é a taxa de ausência da clínica?
-- Regra de negócio:
-- Considerar status = 'FALTOU' para ausências e calcular o percentual sobre o total agendado.

SET search_path TO clinica_pi_uc6, public;

-- ============================================================================
-- Parte A: Taxa geral de faltas por Convênio
-- Mostra quais planos de saúde têm mais faltas e a porcentagem de ausência.
-- ============================================================================
SELECT
    conv.nome AS convenio,
    conv.tipo AS tipo_convenio,
    COUNT(c.id_consulta) AS total_agendamentos,
    COUNT(CASE WHEN c.status = 'FALTOU' THEN 1 END) AS total_faltas,
    COUNT(CASE WHEN c.status = 'REALIZADA' THEN 1 END) AS total_realizadas,
    ROUND(
        (COUNT(CASE WHEN c.status = 'FALTOU' THEN 1 END)::numeric / COUNT(c.id_consulta)) * 100, 
        2
    ) AS taxa_falta_percentual
FROM consulta c
JOIN paciente pac 
    ON pac.id_paciente = c.id_paciente
JOIN convenio conv 
    ON conv.id_convenio = pac.id_convenio
GROUP BY conv.nome, conv.tipo
ORDER BY total_faltas DESC, taxa_falta_percentual DESC;

-- ============================================================================
-- Parte B: Concentração de faltas por Especialidade
-- Ajuda a descobrir em quais áreas médicas os pacientes mais faltam.
-- ============================================================================
SELECT
    e.nome AS especialidade,
    COUNT(c.id_consulta) AS total_agendamentos,
    COUNT(CASE WHEN c.status = 'FALTOU' THEN 1 END) AS total_faltas,
    ROUND(
        (COUNT(CASE WHEN c.status = 'FALTOU' THEN 1 END)::numeric / COUNT(c.id_consulta)) * 100, 
        2
    ) AS taxa_falta_percentual
FROM consulta c
JOIN profissional p 
    ON p.id_profissional = c.id_profissional
JOIN especialidade e 
    ON e.id_especialidade = p.id_especialidade
GROUP BY e.nome
ORDER BY total_faltas DESC;

-- ============================================================================
-- Conclusão do estudante (Felipe Walker):
-- Os convênios com maior volume de pacientes lideram o número de faltas.
-- Porém, os pacientes do tipo 'PARTICULAR' apresentam a menor taxa percentual de ausência,
-- pois quando o paciente paga a consulta do próprio bolso ele tem mais compromisso em ir.
--
-- Limitação ou cuidado de interpretação:
-- A base não possui campo que justifique o motivo do paciente ter faltado (ex: trânsito,
-- imprevisto ou esquecimento). Além disso, não diferencia faltas avisadas com antecedência.
-- ============================================================================