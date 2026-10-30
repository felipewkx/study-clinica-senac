package br.senac.uc6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RelatorioClinica {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("   CLÍNICA PI UC6 - RELATÓRIO 1: DEMANDA POR ESPECIALIDADE   ");
        System.out.println("   Desenvolvido por: Felipe Walker - Turma TDS                ");
        System.out.println("===============================================================\n");

        String queryRelatorio = """
            SELECT
                e.nome AS especialidade,
                COUNT(c.id_consulta) AS total_consultas_realizadas
            FROM consulta AS c
            INNER JOIN profissional AS p ON p.id_profissional = c.id_profissional
            INNER JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade
            WHERE c.status = 'REALIZADA'
            GROUP BY e.nome
            ORDER BY total_consultas_realizadas DESC, e.nome;
        """;

        try (Connection conn = ConexaoBanco.abrir();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET search_path TO " + ConfigBanco.SCHEMA + ", public;");

            ResultSet rs = stmt.executeQuery(queryRelatorio);

            System.out.printf("%-30s | %-20s%n", "ESPECIALIDADE", "TOTAL REALIZADAS");
            System.out.println("---------------------------------------------------------------");

            int totalGeral = 0;
            while (rs.next()) {
                String especialidade = rs.getString("especialidade");
                int total = rs.getInt("total_consultas_realizadas");
                totalGeral += total;
                System.out.printf("%-30s | %-20d%n", especialidade, total);
            }

            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-30s | %-20d%n", "TOTAL GERAL", totalGeral);
            System.out.println("\nConsulta finalizada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao conectar ou executar no PostgreSQL:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Dica: verifique se o PostgreSQL está ligado e se a senha/porta estão certas.");
        }
    }
}