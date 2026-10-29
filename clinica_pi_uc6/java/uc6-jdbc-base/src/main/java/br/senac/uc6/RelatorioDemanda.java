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
            FROM clinica_pi_uc6.consulta AS c
            INNER JOIN clinica_pi_uc6.profissional AS p ON p.id_profissional = c.id_profissional
            INNER JOIN clinica_pi_uc6.especialidade AS e ON e.id_especialidade = p.id_especialidade
            WHERE
                c.status = 'REALIZADA'
            GROUP BY
                e.nome
            ORDER BY
                total_consultas DESC,
                e.nome;
        """;

        System.out.println("\n=== Resultado do relatório: Demanda por Especialidade ===");

        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            int totalGeral = 0;

            while (resultado.next()) {
                String especialidade = resultado.getString("especialidade");
                int totalConsultas = resultado.getInt("total_consultas");
                totalGeral += totalConsultas;

                System.out.printf("especialidade: %-25s | total_consultas: %d%n", especialidade, totalConsultas);
            }

            System.out.println("---------------------------------------------------------");
            System.out.printf("Total acumulado de consultas realizadas: %d%n", totalGeral);
        }
    }
}