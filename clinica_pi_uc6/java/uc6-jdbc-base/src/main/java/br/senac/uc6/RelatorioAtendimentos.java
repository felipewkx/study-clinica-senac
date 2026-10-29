package br.senac.uc6;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioAtendimentos {

    public static void executar(Connection conexao) throws SQLException {
        String sql = """
            SELECT
                e.nome AS especialidade,
                COUNT(c.id_consulta) AS atendimentos_realizados,
                SUM(c.valor_cobrado) AS faturamento_total,
                ROUND(AVG(c.valor_cobrado), 2) AS ticket_medio
            FROM consulta c
            JOIN profissional p ON p.id_profissional = c.id_profissional
            JOIN especialidade e ON e.id_especialidade = p.id_especialidade
            WHERE c.status = 'REALIZADA'
            GROUP BY e.nome
            ORDER BY faturamento_total DESC;
        """;

        System.out.println("\n=========================================================================================");
        System.out.println("                 RELATÓRIO 3: ATENDIMENTOS, FATURAMENTO E TICKET MÉDIO                   ");
        System.out.println("=========================================================================================");
        System.out.printf("%-25s | %-12s | %-18s | %-15s%n",
                "ESPECIALIDADE", "REALIZADOS", "FATURAMENTO TOTAL", "TICKET MÉDIO");
        System.out.println("-----------------------------------------------------------------------------------------");

        int totalAtendimentos = 0;
        BigDecimal faturamentoGeral = BigDecimal.ZERO;

        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                String especialidade = resultado.getString("especialidade");
                int atendimentos = resultado.getInt("atendimentos_realizados");
                BigDecimal faturamento = resultado.getBigDecimal("faturamento_total");
                BigDecimal ticketMedio = resultado.getBigDecimal("ticket_medio");

                if (faturamento != null) {
                    faturamentoGeral = faturamentoGeral.add(faturamento);
                }

                totalAtendimentos += atendimentos;

                System.out.printf("%-25s | %-12d | R$ %15.2f | R$ %12.2f%n",
                        especialidade,
                        atendimentos,
                        faturamento != null ? faturamento.doubleValue() : 0.0,
                        ticketMedio != null ? ticketMedio.doubleValue() : 0.0);
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("Total geral atendimentos: %d | Faturamento acumulado: R$ %.2f%n",
                totalAtendimentos, faturamentoGeral.doubleValue());
    }
}