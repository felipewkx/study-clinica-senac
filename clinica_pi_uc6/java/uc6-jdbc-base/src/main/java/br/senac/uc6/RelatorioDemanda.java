package br.senac.uc6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioDemanda {

    public static void executar(Connection conexao) throws SQLException {
        String sql = """
            SELECT
                e.nome AS especialidade,
                COUNT(c.id_consulta) AS total_consultas
            FROM consulta AS c
            INNER JOIN profissional AS p ON p.id_profissional = c.id_profissional
            INNER JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade
            WHERE c.status = 'REALIZADA'
            GROUP BY e.nome
            ORDER BY total_consultas DESC, e.nome;
        """;

        System.out.println("\n=========================================================================================");
        System.out.println("                 RELATÓRIO 1: DEMANDA DE ATENDIMENTOS POR ESPECIALIDADE                  ");
        System.out.println("=========================================================================================");
        System.out.printf("%-35s | %-20s%n", "ESPECIALIDADE MÉDICA", "CONSULTAS REALIZADAS");
        System.out.println("-----------------------------------------------------------------------------------------");

        int totalGeral = 0;

        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                String especialidade = resultado.getString("especialidade");
                int totalConsultas = resultado.getInt("total_consultas");
                totalGeral += totalConsultas;

                System.out.printf("%-35s | %-20d%n", especialidade, totalConsultas);
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("Total acumulado de consultas realizadas: %d%n", totalGeral);
        System.out.println("=========================================================================================\n");
    }
}