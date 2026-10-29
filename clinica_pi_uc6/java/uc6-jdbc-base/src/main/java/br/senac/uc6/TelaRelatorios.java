package br.senac.uc6;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class TelaRelatorios extends JFrame {

    // Paleta de Cores Dark + Azul Hospitalar
    private static final Color BG_JANELA = new Color(13, 17, 23);         // Preto Grafite / Slate Dark
    private static final Color BG_PAINEL = new Color(22, 27, 34);         // Fundo de cards/laterais
    private static final Color BG_AREA_TEXTO = new Color(10, 14, 20);     // Preto Profundo
    private static final Color AZUL_HOSPITALAR = new Color(2, 132, 199);   // Azul Cirúrgico / Hospitalar
    private static final Color AZUL_HOVER = new Color(14, 165, 233);       // Azul Ciano iluminado
    private static final Color AZUL_ESCURO = new Color(12, 74, 110);      // Borda sutil
    private static final Color TEXTO_BRANCO = new Color(248, 250, 252);   // Branco puro
    private static final Color TEXTO_CINZA = new Color(148, 163, 184);     // Cinza claro para subtítulos

    private final JTextArea areaTexto;
    private final JLabel labelStatus;

    public TelaRelatorios(Connection conexao) {

        setTitle("Clínica PI — Sistema de Análise e Gestão Integrada");
        setSize(1100, 750);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerra o processo completamente ao fechar
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_JANELA);
        setLayout(new BorderLayout());

        // 1. Cabeçalho Superior Dark com Friso Azul Hospitalar
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(BG_PAINEL);
        painelTopo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, AZUL_HOSPITALAR),
                new EmptyBorder(18, 25, 18, 25)
        ));

        JLabel titulo = new JLabel("PAINEL EXECUTIVO DA CLÍNICA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Fonte Maior
        titulo.setForeground(TEXTO_BRANCO);

        JLabel subtitulo = new JLabel("Módulo de Inteligência de Dados e Tomada de Decisão (UC6)");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(TEXTO_CINZA);

        JPanel painelTitulos = new JPanel(new GridLayout(2, 1, 0, 4));
        painelTitulos.setOpaque(false);
        painelTitulos.add(titulo);
        painelTitulos.add(subtitulo);
        painelTopo.add(painelTitulos, BorderLayout.WEST);

        // Badge estilizada no topo direito
        JLabel badge = new JLabel("PostgreSQL Ativo");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        badge.setForeground(AZUL_HOVER);
        badge.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AZUL_HOSPITALAR, 1, true),
                new EmptyBorder(6, 14, 6, 14)
        ));
        painelTopo.add(badge, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // 2. Painel Lateral com Botões Modernos e Fontes Grandes
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setBackground(BG_PAINEL);
        painelLateral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, AZUL_ESCURO),
                new EmptyBorder(25, 18, 20, 18)
        ));
        painelLateral.setPreferredSize(new Dimension(300, 0));

        JLabel rotuloSecao = new JLabel("CONSULTAS ANALÍTICAS");
        rotuloSecao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        rotuloSecao.setForeground(AZUL_HOVER);
        rotuloSecao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelLateral.add(rotuloSecao);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botões com layout amplo e fonte 14
        JButton btnDemanda = criarBotaoDark("1. Demanda por Especialidade");
        JButton btnFaltas = criarBotaoDark("2. Índice de Faltas");
        JButton btnAtendimentos = criarBotaoDark("3. Atendimentos & Faturamento");
        JButton btnConsultorios = criarBotaoDark("4. Uso dos Consultórios");
        JButton btnLimpar = criarBotaoSecundarioDark("Limpar Visualização");

        painelLateral.add(btnDemanda);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelLateral.add(btnFaltas);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelLateral.add(btnAtendimentos);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelLateral.add(btnConsultorios);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 25)));
        painelLateral.add(btnLimpar);

        add(painelLateral, BorderLayout.WEST);

        // 3. Área Central (Exibição dos Dados com Fundo Preto e Fonte Alta Definição)
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        // Fonte Monospaced ampla (tamanho 15) para garantir visualização nítida
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 15));
        areaTexto.setBackground(BG_AREA_TEXTO);
        areaTexto.setForeground(new Color(224, 242, 254)); // Azul gelo luminoso
        areaTexto.setCaretColor(Color.WHITE);
        areaTexto.setMargin(new Insets(20, 20, 20, 20));
        areaTexto.setText(">>> Selecione um relatório no menu lateral para visualizar os dados da clínica...");

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        // 4. Rodapé Dark com Status
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, AZUL_ESCURO),
                new EmptyBorder(10, 20, 10, 20)
        ));
        painelRodape.setBackground(BG_PAINEL);

        labelStatus = new JLabel("● Conectado ao PostgreSQL | Schema: clinica_pi_uc6");
        labelStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelStatus.setForeground(new Color(74, 222, 128)); // Verde esmeralda
        painelRodape.add(labelStatus, BorderLayout.WEST);

        JLabel rodapeInfo = new JLabel("Clínica Fictícia • TDS");
        rodapeInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rodapeInfo.setForeground(TEXTO_CINZA);
        painelRodape.add(rodapeInfo, BorderLayout.EAST);

        add(painelRodape, BorderLayout.SOUTH);

        // Eventos
        btnDemanda.addActionListener(e -> executarRelatorio("Demanda por Especialidade", () -> RelatorioDemanda.executar(conexao)));
        btnFaltas.addActionListener(e -> executarRelatorio("Taxa de Faltas", () -> RelatorioFaltas.executar(conexao)));
        btnAtendimentos.addActionListener(e -> executarRelatorio("Atendimentos e Faturamento", () -> RelatorioAtendimentos.executar(conexao)));
        btnConsultorios.addActionListener(e -> executarRelatorio("Uso dos Consultórios", () -> RelatorioConsultorios.executar(conexao)));
        btnLimpar.addActionListener(e -> areaTexto.setText(">>> Visualização limpa. Selecione um relatório ao lado."));
    }

    private JButton criarBotaoDark(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(265, 50));
        btn.setPreferredSize(new Dimension(265, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Fonte Maior
        btn.setBackground(new Color(30, 41, 59));
        btn.setForeground(TEXTO_BRANCO);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(AZUL_HOSPITALAR, 1, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Efeito Hover luminoso nos botões
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(AZUL_HOSPITALAR);
                btn.setBorder(new LineBorder(AZUL_HOVER, 2, true));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 41, 59));
                btn.setBorder(new LineBorder(AZUL_HOSPITALAR, 1, true));
            }
        });

        return btn;
    }

    private JButton criarBotaoSecundarioDark(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(265, 40));
        btn.setPreferredSize(new Dimension(265, 40));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(new Color(15, 23, 42));
        btn.setForeground(TEXTO_CINZA);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(51, 65, 85), 1, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(TEXTO_BRANCO);
                btn.setBorder(new LineBorder(AZUL_HOVER, 1, true));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(TEXTO_CINZA);
                btn.setBorder(new LineBorder(new Color(51, 65, 85), 1, true));
            }
        });

        return btn;
    }

    private void executarRelatorio(String nome, TarefaRelatorio tarefa) {
        areaTexto.setText(">>> Consultando o banco PostgreSQL e processando dados de " + nome + "...\n>>> Aguarde um instante...");
        labelStatus.setText("● Carregando dados...");
        labelStatus.setForeground(AZUL_HOVER);

        new Thread(() -> {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            try {
                PrintStream customOut = new PrintStream(buffer, true, StandardCharsets.UTF_8);
                System.setOut(customOut);

                tarefa.executar();

                String resultado = buffer.toString(StandardCharsets.UTF_8);

                SwingUtilities.invokeLater(() -> {
                    areaTexto.setText(resultado);
                    areaTexto.setCaretPosition(0);
                    labelStatus.setText("✓ " + nome + " gerado com sucesso.");
                    labelStatus.setForeground(new Color(74, 222, 128));
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    areaTexto.setText("Erro ao executar relatório: " + ex.getMessage());
                    labelStatus.setText("✖ Erro na consulta SQL.");
                    labelStatus.setForeground(new Color(248, 113, 113));
                });
            } finally {
                System.setOut(originalOut);
            }
        }).start();
    }

    @FunctionalInterface
    private interface TarefaRelatorio {
        void executar() throws Exception;
    }

    public static void abrir(Connection conexao) {
        SwingUtilities.invokeLater(() -> {
            TelaRelatorios tela = new TelaRelatorios(conexao);
            tela.setVisible(true);
        });
    }
}