import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Projeto Integrador UC6 - Módulo de Relatórios da Clínica
 * Programa Java simples para conectar no PostgreSQL e gerar o Relatório 1 via console.
 * 
 * @author Felipe Walker
 * Turma: TDS
 */
public class RelatorioClinica {

    // Configurações de conexão com o banco local
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // Ajuste o nome do banco se necessário
    private static final String USUARIO = "postgres";
    private static final String SENHA = "admin"; // Coloque a senha do seu PostgreSQL

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("   CLÍNICA PI UC6 - RELATÓRIO 1: DEMANDA POR ESPECIALIDADE   ");
        System.out.println("   Desenvolvido por: Felipe Walker - Turma TDS                ");
        System.out.println("===============================================================\n");

        // Query do Relatório 1 (Parte A)
        String sql = "SET search_path TO clinica_pi_uc6, public; " +
                     "SELECT " +
                     "    e.nome AS especialidade, " +
                     "    COUNT(c.id_consulta) AS total_consultas_realizadas " +
                     "FROM consulta AS c " +
                     "JOIN profissional AS p ON p.id_profissional = c.id_profissional " +
                     "JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade " +
                     "WHERE c.status = 'REALIZADA' " +
                     "GROUP BY e.nome " +
                     "ORDER BY total_consultas_realizadas DESC, e.nome;";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {

            // Configura o schema de trabalho
            stmt.execute("SET search_path TO clinica_pi_uc6, public;");

            // Executa a busca
            String queryRelatorio = "SELECT " +
                                    "    e.nome AS especialidade, " +
                                    "    COUNT(c.id_consulta) AS total_consultas_realizadas " +
                                    "FROM consulta AS c " +
                                    "JOIN profissional AS p ON p.id_profissional = c.id_profissional " +
                                    "JOIN especialidade AS e ON e.id_especialidade = p.id_especialidade " +
                                    "WHERE c.status = 'REALIZADA' " +
                                    "GROUP BY e.nome " +
                                    "ORDER BY total_consultas_realizadas DESC, e.nome;";

            ResultSet rs = stmt.executeQuery(queryRelatorio);

            // Cabeçalho da tabela no terminal
            System.out.printf("%-30s | %-20s%n", "ESPECIALIDADE", "TOTAL REALIZADAS");
            System.out.println("---------------------------------------------------------------");

            int totalGeral = 0;

            // Percorre os resultados retornados
            while (rs.next()) {
                String especialidade = rs.getString("especialidade");
                int total = rs.getInt("total_consultas_realizadas");
                totalGeral += total;

                System.out.printf("%-30s | %-20d%n", especialidade, total);
            }

            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-30s | %-20d%n", "TOTAL GERAL", totalGeral);
            System.out.println("===============================================================");
            System.out.println("Consulta finalizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou executar no PostgreSQL:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Dica: verifique se o PostgreSQL está ligado e se a senha/porta estão certas.");
        }
    }
}