-- ==============================================================================
-- Projeto Integrador UC6 - Módulo de Relatórios da Clínica
-- Relatório 1: Demanda por especialidade e profissional
-- Autor: Felipe Walker | Turma: TDS
--
-- Pergunta de negócio:
-- Quais especialidades e profissionais possuem maior volume de consultas realizadas?
--
-- Regra de negócio: 
-- Considerar somente consultas com status REALIZADA.
-- ==============================================================================

SET search_path TO clinica_pi_uc6, public;

-- ------------------------------------------------------------------------------
-- Parte A: Demanda por especialidade
-- Agrupa todas as consultas com status REALIZADA pelo nome da especialidade médica.
-- ------------------------------------------------------------------------------
SELECT 
    e.nome AS especialidade, 
    COUNT(c.id_consulta) AS total_consultas_realizadas 
FROM consulta AS c 
INNER JOIN profissional AS p ON p.id_profissional = c.id_profissional 
INNER JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade 
WHERE 
    c.status = 'REALIZADA' 
GROUP BY 
    e.nome 
ORDER BY 
    total_consultas_realizadas DESC, 
    e.nome;

-- ------------------------------------------------------------------------------
-- Parte B: Demanda por profissional
-- Mostra individualmente a quantidade de atendimentos feitos por cada médico/psicólogo.
-- ------------------------------------------------------------------------------
SELECT 
    p.nome AS profissional, 
    e.nome AS especialidade, 
    COUNT(c.id_consulta) AS total_consultas_realizadas 
FROM consulta AS c 
INNER JOIN profissional AS p ON p.id_profissional = c.id_profissional 
INNER JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade 
WHERE 
    c.status = 'REALIZADA' 
GROUP BY 
    p.nome, 
    e.nome 
ORDER BY 
    total_consultas_realizadas DESC, 
    p.nome;

-- ==============================================================================
-- Conclusão do estudante:
-- Registre aqui a especialidade e o profissional com maior demanda,
-- citando os números retornados pela sua execução.
--
-- Resposta: A Especialidade com maior demanda é Clinica Geral (com 255 consultas 
-- no total), e o profissional com maior demanda é a Dra Helena Martins - Clinica 
-- Geral (com 159 consultas no total).
-- 
-- Limitação ou cuidado de interpretação:
-- Esta consulta conta estritamente o volume de atendimentos que tiveram status 
-- 'REALIZADA'. Ela NÃO mede faturamento (pois uma consulta de Clínica Geral pode 
-- ser mais barata que uma de Cardiologia), NÃO mede o tempo gasto em sala e NÃO 
-- indica se a procura foi presencial ou teleconsulta. Além disso, médicos 
-- admitidos recentemente terão menos consultas no total simplesmente por terem 
-- menos tempo de casa na clínica.
-- ==============================================================================
