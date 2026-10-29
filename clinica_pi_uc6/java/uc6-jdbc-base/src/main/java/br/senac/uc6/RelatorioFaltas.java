package br.senac.uc6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioFaltas {

    public static void executar(Connection conexao) throws SQLException {
        String sql = """
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
            JOIN paciente pac ON pac.id_paciente = c.id_paciente
            JOIN convenio conv ON conv.id_convenio = pac.id_convenio
            GROUP BY conv.nome, conv.tipo
            ORDER BY total_faltas DESC, taxa_falta_percentual DESC;
        """;

        System.out.println("\n=========================================================================================");
        System.out.println("                          RELATÓRIO 2: ÍNDICE DE FALTAS POR CONVÊNIO                     ");
        System.out.println("=========================================================================================");
        System.out.printf("%-22s | %-13s | %-12s | %-8s | %-10s | %-8s%n",
                "CONVÊNIO", "TIPO", "AGENDAMENTOS", "FALTAS", "REALIZADAS", "TAXA (%)");
        System.out.println("-----------------------------------------------------------------------------------------");

        int totalGeralAgendamentos = 0;
        int totalGeralFaltas = 0;

        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                String convenio = resultado.getString("convenio");
                String tipo = resultado.getString("tipo_convenio");
                int totalAgendamentos = resultado.getInt("total_agendamentos");
                int totalFaltas = resultado.getInt("total_faltas");
                int totalRealizadas = resultado.getInt("total_realizadas");
                double taxaFalta = resultado.getDouble("taxa_falta_percentual");

                totalGeralAgendamentos += totalAgendamentos;
                totalGeralFaltas += totalFaltas;

                System.out.printf("%-22s | %-13s | %-12d | %-8d | %-10d | %6.2f%%%n",
                        convenio, tipo, totalAgendamentos, totalFaltas, totalRealizadas, taxaFalta);
            }
        }

        double taxaGeralFaltas = totalGeralAgendamentos > 0 
                ? ((double) totalGeralFaltas / totalGeralAgendamentos) * 100 
                : 0.0;

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("Total acumulado de agendamentos: %d | Total de faltas: %d (Taxa geral: %.2f%%)%n",
                totalGeralAgendamentos, totalGeralFaltas, taxaGeralFaltas);
    }
}