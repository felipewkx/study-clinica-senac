package br.senac.uc6;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Modo Gráfico Automático (clique duplo no Windows ou parâmetro --gui)
        boolean modoGrafico = (args.length > 0 && args[0].equalsIgnoreCase("--gui")) || (System.console() == null);

        Connection conexao;
        try {
            conexao = ConexaoBanco.abrir();
        } catch (SQLException e) {
            String mensagemErro = "Falha ao conectar no PostgreSQL:\n" + e.getMessage() +
                    "\n\nCertifique-se de que o serviço do PostgreSQL está ativo.";
            if (modoGrafico) {
                JOptionPane.showMessageDialog(null, mensagemErro, "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            } else {
                System.err.println(mensagemErro);
            }
            return;
        }

        if (modoGrafico) {
            TelaRelatorios.abrir(conexao);
            return;
        }

        // Modo Terminal
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("✓ Conexão realizada com sucesso com o PostgreSQL.");
            System.out.println("Banco: postgres | Schema: " + ConfigBanco.SCHEMA);

            boolean continuar = true;

            while (continuar) {
                exibirMenu();
                System.out.print("Escolha uma opção: ");
                String entrada = scanner.nextLine().trim();

                switch (entrada) {
                    case "1":
                        RelatorioDemanda.executar(conexao);
                        break;
                    case "2":
                        RelatorioFaltas.executar(conexao);
                        break;
                    case "3":
                        RelatorioAtendimentos.executar(conexao);
                        break;
                    case "4":
                        RelatorioConsultorios.executar(conexao);
                        break;
                    case "5":
                        System.out.println("\n[!] Abrindo Interface Gráfica (Swing)...");
                        TelaRelatorios.abrir(conexao);
                        break;
                    case "0":
                        System.out.println("\nEncerrando a aplicação. Até logo!");
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n[!] Opção inválida. Digite de 0 a 5.\n");
                        break;
                }

                if (continuar) {
                    System.out.println("\nPressione <ENTER> para voltar ao menu principal...");
                    scanner.nextLine();
                }
            }
        } catch (Exception ex) {
            System.err.println("Erro na execução: " + ex.getMessage());
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=======================================================");
        System.out.println("               CLÍNICA PI - RELATÓRIOS                 ");
        System.out.println("=======================================================");
        System.out.println(" 1 - Demanda por especialidade e profissional");
        System.out.println(" 2 - Taxa de faltas por convênio/especialidade");
        System.out.println(" 3 - Atendimentos, faturamento e ticket médio");
        System.out.println(" 4 - Uso dos consultórios por horário");
        System.out.println(" 5 - Abrir Interface Gráfica Dark (Swing)");
        System.out.println(" 0 - Encerrar a aplicação");
        System.out.println("=======================================================");
    }
}