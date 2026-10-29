package br.senac.uc6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioConsultorios {

    public static void executar(Connection conexao) throws SQLException {
        System.out.println("\n=========================================================================================");
        System.out.println("                 RELATÓRIO 4: USO DOS CONSULTÓRIOS E FAIXA DE HORÁRIO                   ");
        System.out.println("=========================================================================================");
        System.out.printf("%-18s | %-6s | %-13s | %-11s | %-10s | %-12s%n",
                "CONSULTÓRIO", "ANDAR", "AGENDAMENTOS", "REALIZADAS", "PRESENCIAL", "TELECONSULTA");
        System.out.println("-----------------------------------------------------------------------------------------");

        String sqlConsultorios = """
            SELECT
                cons.nome AS consultorio,
                cons.andar,
                COUNT(c.id_consulta) AS total_agendamentos,
                COUNT(CASE WHEN c.status = 'REALIZADA' THEN 1 END) AS consultas_realizadas,
                COUNT(CASE WHEN c.tipo_atendimento = 'PRESENCIAL' THEN 1 END) AS qtd_presencial,
                COUNT(CASE WHEN c.tipo_atendimento = 'TELECONSULTA' THEN 1 END) AS qtd_teleconsulta
            FROM consultorio cons
            LEFT JOIN consulta c ON c.id_consultorio = cons.id_consultorio
            GROUP BY cons.id_consultorio, cons.nome, cons.andar
            ORDER BY total_agendamentos DESC;
        """;

        try (PreparedStatement comando = conexao.prepareStatement(sqlConsultorios);
             ResultSet rs = comando.executeQuery()) {

            int totalAgendamentosGeral = 0;
            int totalRealizadasGeral = 0;

            while (rs.next()) {
                String consultorio = rs.getString("consultorio");
                int andar = rs.getInt("andar");
                int totalAgendamentos = rs.getInt("total_agendamentos");
                int totalRealizadas = rs.getInt("consultas_realizadas");
                int presencial = rs.getInt("qtd_presencial");
                int teleconsulta = rs.getInt("qtd_teleconsulta");

                totalAgendamentosGeral += totalAgendamentos;
                totalRealizadasGeral += totalRealizadas;

                System.out.printf("%-18s | %-6d | %-13d | %-11d | %-10d | %-12d%n",
                        consultorio, andar, totalAgendamentos, totalRealizadas, presencial, teleconsulta);
            }

            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.printf("Total acumulado: %d agendamentos | %d consultas realizadas%n",
                    totalAgendamentosGeral, totalRealizadasGeral);
        }

        System.out.println("\n--- Distribuição por Faixa de Horário ---");
        System.out.printf("%-25s | %-16s | %-10s%n", "FAIXA DE HORÁRIO", "TOTAL CONSULTAS", "USO (%)");
        System.out.println("---------------------------------------------------------");

        String sqlHorarios = """
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
        """;

        try (PreparedStatement comando = conexao.prepareStatement(sqlHorarios);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                String faixa = rs.getString("faixa_horario");
                int total = rs.getInt("total_consultas");
                double percentual = rs.getDouble("percentual_uso");

                System.out.printf("%-25s | %-16d | %6.2f%%%n", faixa, total, percentual);
            }
        }
        System.out.println("=========================================================================================\n");
    }
}