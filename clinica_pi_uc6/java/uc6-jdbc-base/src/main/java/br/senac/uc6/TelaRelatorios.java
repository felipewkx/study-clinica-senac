package br.senac.uc6;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaRelatorios extends JFrame {

    private static final Color BG_APP          = new Color(11, 15, 25);
    private static final Color BG_SIDEBAR      = new Color(18, 24, 38);
    private static final Color BG_CARD         = new Color(24, 32, 50);
    private static final Color BG_TERMINAL     = new Color(13, 18, 30);
    private static final Color BORDA_CARD      = new Color(38, 48, 74);
    private static final Color ACCENT_PRIMARY  = new Color(14, 165, 233);
    private static final Color ACCENT_HOVER    = new Color(56, 189, 248);
    private static final Color ACCENT_ACTIVE   = new Color(2, 132, 199);
    private static final Color TEXTO_TITULO    = new Color(248, 250, 252);
    private static final Color TEXTO_MUTED     = new Color(148, 163, 184);
    private static final Color VERDE_ONLINE    = new Color(34, 197, 94);
    private static final Color VERMELHO_ERRO   = new Color(248, 113, 113);

    private final Connection conexao;
    private final JTextPane areaTexto;
    private JLabel labelStatus;
    private JLabel labelUltimaAtualizacao;
    private JLabel labelCardTitulo;
    private JLabel labelCardSubtitulo;

    private TarefaRelatorio tarefaAtual = null;
    private String nomeRelatorioAtual = null;

    public TelaRelatorios(Connection conexao) {
        this.conexao = conexao;

        configurarJanela();

        JPanel containerGeral = new JPanel(new BorderLayout(0, 0));
        containerGeral.setBackground(BG_APP);

        containerGeral.add(criarCabecalhoSuperior(), BorderLayout.NORTH);
        containerGeral.add(criarMenuLateral(), BorderLayout.WEST);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 16));
        painelCentral.setBackground(BG_APP);
        painelCentral.setBorder(new EmptyBorder(20, 24, 20, 24));

        painelCentral.add(criarCardCabecalhoSecao(), BorderLayout.NORTH);

        areaTexto = new JTextPane();
        areaTexto.setContentType("text/html");
        areaTexto.setEditable(false);
        areaTexto.setBackground(BG_TERMINAL);
        areaTexto.setMargin(new Insets(15, 20, 15, 20));
        areaTexto.setText(formatarParaHtml(">>> Bem-vindo ao Painel Executivo da Clínica.\n>>> Selecione um relatório no menu lateral para extrair os indicadores analíticos do PostgreSQL."));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createLineBorder(BORDA_CARD, 1, true));
        scroll.getViewport().setBackground(BG_TERMINAL);
        painelCentral.add(scroll, BorderLayout.CENTER);

        containerGeral.add(painelCentral, BorderLayout.CENTER);
        containerGeral.add(criarRodape(), BorderLayout.SOUTH);

        setContentPane(containerGeral);
    }

    private void configurarJanela() {
        setTitle("Clínica PI — Business Intelligence & Gestão Hospitalar");
        setSize(1180, 780);
        setMinimumSize(new Dimension(980, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel criarCabecalhoSuperior() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(BG_SIDEBAR);
        topo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA_CARD),
                new EmptyBorder(16, 28, 16, 28)
        ));

        JPanel boxTitulos = new JPanel();
        boxTitulos.setLayout(new BoxLayout(boxTitulos, BoxLayout.Y_AXIS));
        boxTitulos.setOpaque(false);

        JLabel titulo = new JLabel("CLÍNICA PI • SISTEMA DE GESTÃO ANALÍTICA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
        titulo.setForeground(TEXTO_TITULO);

        JLabel subtitulo = new JLabel("Unidade de Inteligência Operacional, Finanças e Espaço Clínico (UC6)");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(TEXTO_MUTED);

        boxTitulos.add(titulo);
        boxTitulos.add(Box.createRigidArea(new Dimension(0, 4)));
        boxTitulos.add(subtitulo);
        topo.add(boxTitulos, BorderLayout.WEST);

        JPanel badgeStatus = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 197, 94, 25));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(new Color(34, 197, 94, 80));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        badgeStatus.setOpaque(false);
        badgeStatus.setBorder(new EmptyBorder(8, 16, 8, 16));
        badgeStatus.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));

        JLabel pontoVerde = new JLabel("●");
        pontoVerde.setForeground(VERDE_ONLINE);
        pontoVerde.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel textoBadge = new JLabel("PostgreSQL Online");
        textoBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        textoBadge.setForeground(new Color(220, 252, 231));

        badgeStatus.add(pontoVerde);
        badgeStatus.add(textoBadge);

        topo.add(badgeStatus, BorderLayout.EAST);
        return topo;
    }

    private JPanel criarCardCabecalhoSecao() {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(BORDA_CARD);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 20, 14, 20));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        labelCardTitulo = new JLabel("Visão Geral");
        labelCardTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelCardTitulo.setForeground(TEXTO_TITULO);

        labelCardSubtitulo = new JLabel("Aguardando seleção de relatório analítico...");
        labelCardSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelCardSubtitulo.setForeground(TEXTO_MUTED);

        textos.add(labelCardTitulo);
        textos.add(Box.createRigidArea(new Dimension(0, 3)));
        textos.add(labelCardSubtitulo);

        card.add(textos, BorderLayout.WEST);

        labelUltimaAtualizacao = new JLabel("Sincronização: --:--:--");
        labelUltimaAtualizacao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelUltimaAtualizacao.setForeground(TEXTO_MUTED);
        card.add(labelUltimaAtualizacao, BorderLayout.EAST);

        return card;
    }

    private JPanel criarMenuLateral() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, BORDA_CARD),
                new EmptyBorder(24, 18, 20, 18)
        ));

        JLabel secaoAnalises = new JLabel("RELATÓRIOS DO NEGÓCIO");
        secaoAnalises.setFont(new Font("Segoe UI", Font.BOLD, 11));
        secaoAnalises.setForeground(ACCENT_PRIMARY);
        secaoAnalises.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(secaoAnalises);

        sidebar.add(Box.createRigidArea(new Dimension(0, 14)));

        JButton btnDemanda = criarBotaoMenu("1. Demanda Operacional", "Especialidade e médicos");
        JButton btnFaltas = criarBotaoMenu("2. Taxa de Absenteísmo", "Ausências por convênio");
        JButton btnFinanceiro = criarBotaoMenu("3. Faturamento & Ticket", "Receita total e ticket médio");
        JButton btnEspacos = criarBotaoMenu("4. Uso dos Consultórios", "Salas físicas e horários pico");

        sidebar.add(btnDemanda);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnFaltas);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnFinanceiro);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnEspacos);

        sidebar.add(Box.createRigidArea(new Dimension(0, 24)));

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDA_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);

        sidebar.add(Box.createRigidArea(new Dimension(0, 18)));

        JLabel secaoAcoes = new JLabel("CONTROLES");
        secaoAcoes.setFont(new Font("Segoe UI", Font.BOLD, 11));
        secaoAcoes.setForeground(TEXTO_MUTED);
        secaoAcoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(secaoAcoes);

        sidebar.add(Box.createRigidArea(new Dimension(0, 14)));

        // BOTÃO ATUALIZAR COM ÍCONE VETORIAL LIMPO E MODERNO
        JButton btnAtualizar = criarBotaoAtualizarComIcone();
        btnAtualizar.addActionListener(e -> acaoAtualizar());
        sidebar.add(btnAtualizar);

        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnLimpar = criarBotaoAcao("Limpar Painel", new Color(30, 41, 59), TEXTO_MUTED);
        btnLimpar.addActionListener(e -> {
            tarefaAtual = null;
            nomeRelatorioAtual = null;
            areaTexto.setText(formatarParaHtml(">>> Painel limpo. Selecione um relatório ao lado."));
            labelCardTitulo.setText("Visão Geral");
            labelCardSubtitulo.setText("Aguardando seleção de relatório analítico...");
            labelUltimaAtualizacao.setText("Sincronização: --:--:--");
            labelStatus.setText("● Pronto para consulta");
            labelStatus.setForeground(TEXTO_MUTED);
        });
        sidebar.add(btnLimpar);

        sidebar.add(Box.createVerticalGlue());

        btnDemanda.addActionListener(e -> dispararRelatorio(
                "Demanda por Especialidade e Profissional",
                "Volume total de consultas concluídas agrupadas por especialidade",
                () -> RelatorioDemanda.executar(conexao)
        ));

        btnFaltas.addActionListener(e -> dispararRelatorio(
                "Taxa de Faltas e Absenteísmo",
                "Percentual de ausência e impacto operacional por convênio e especialidade",
                () -> RelatorioFaltas.executar(conexao)
        ));

        btnFinanceiro.addActionListener(e -> dispararRelatorio(
                "Atendimentos, Faturamento e Ticket Médio",
                "Receita acumulada e valor médio por atendimento realizado",
                () -> RelatorioAtendimentos.executar(conexao)
        ));

        btnEspacos.addActionListener(e -> dispararRelatorio(
                "Uso dos Consultórios e Horários de Pico",
                "Distribuição das consultas por sala física e turnos do dia",
                () -> RelatorioConsultorios.executar(conexao)
        ));

        return sidebar;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(BG_SIDEBAR);
        rodape.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA_CARD),
                new EmptyBorder(10, 24, 10, 24)
        ));

        labelStatus = new JLabel("● Conectado ao PostgreSQL (Schema: clinica_pi_uc6)");
        labelStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelStatus.setForeground(VERDE_ONLINE);
        rodape.add(labelStatus, BorderLayout.WEST);

        JLabel info = new JLabel("Módulo de Relatórios • Felipe Walker • TDS");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(TEXTO_MUTED);
        rodape.add(info, BorderLayout.EAST);

        return rodape;
    }

    private JButton criarBotaoMenu(String titulo, String subtitulo) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();
                boolean pressed = getModel().isPressed();

                Color bg = BG_CARD;
                Color border = BORDA_CARD;

                if (pressed) {
                    bg = ACCENT_ACTIVE;
                    border = ACCENT_PRIMARY;
                } else if (hover) {
                    bg = new Color(30, 41, 59);
                    border = ACCENT_PRIMARY;
                }

                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(border);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setLayout(new BorderLayout());
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTit.setForeground(TEXTO_TITULO);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(TEXTO_MUTED);

        JPanel pnlTexto = new JPanel(new GridLayout(2, 1, 0, 2));
        pnlTexto.setOpaque(false);
        pnlTexto.add(lblTit);
        pnlTexto.add(lblSub);

        btn.add(pnlTexto, BorderLayout.CENTER);
        return btn;
    }

    // Botão de Atualizar com ícone circular vetorial desenhado via código
    private JButton criarBotaoAtualizarComIcone() {
        JButton btn = new JButton("Atualizar Dados") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();
                Color bg = hover ? new Color(14, 165, 233, 60) : new Color(14, 165, 233, 30);

                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(ACCENT_PRIMARY);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));

                // Desenha o ícone de sincronização circular
                int iconX = 18;
                int iconY = (getHeight() - 14) / 2;
                g2.setColor(ACCENT_HOVER);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new Arc2D.Float(iconX, iconY, 14, 14, 45, 270, Arc2D.OPEN));

                // Setinha do arco
                int[] arrowX = {iconX + 11, iconX + 15, iconX + 15};
                int[] arrowY = {iconY + 1, iconY + 1, iconY + 5};
                g2.fillPolygon(arrowX, arrowY, 3);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(ACCENT_HOVER);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 44, 10, 16)); // Margem esquerda abre espaço para o ícone vetorial
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private JButton criarBotaoAcao(String texto, Color bgBase, Color corTexto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover();
                Color bg = hover ? bgBase.brighter() : bgBase;

                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(BORDA_CARD);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(corTexto);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private void dispararRelatorio(String nome, String subtitulo, TarefaRelatorio tarefa) {
        this.nomeRelatorioAtual = nome;
        this.tarefaAtual = tarefa;

        labelCardTitulo.setText(nome);
        labelCardSubtitulo.setText(subtitulo);

        executarTarefaEmSegundoPlano(nome, tarefa);
    }

    private void acaoAtualizar() {
        if (tarefaAtual == null || nomeRelatorioAtual == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum relatório está aberto no momento.\nPor favor, escolha um relatório no menu lateral para visualizar e atualizar.",
                    "Aviso de Atualização",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        executarTarefaEmSegundoPlano(nomeRelatorioAtual, tarefaAtual);
    }

    private void executarTarefaEmSegundoPlano(String nome, TarefaRelatorio tarefa) {
        areaTexto.setText(">>> Conectando ao PostgreSQL e extraindo dados mais recentes de [" + nome + "]...\n>>> Processando agregações...");
        labelStatus.setText("● Atualizando dados...");
        labelStatus.setForeground(ACCENT_HOVER);

        new Thread(() -> {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            try {
                PrintStream customOut = new PrintStream(buffer, true, StandardCharsets.UTF_8);
                System.setOut(customOut);

                tarefa.executar();

                String resultado = buffer.toString(StandardCharsets.UTF_8);
                String horaFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                SwingUtilities.invokeLater(() -> {
                    areaTexto.setText(formatarParaHtml(resultado));
                    areaTexto.setCaretPosition(0);
                    labelUltimaAtualizacao.setText("Sincronização: " + horaFormatada + " (Dados em tempo real)");
                    labelStatus.setText("● " + nome + " sincronizado às " + horaFormatada);
                    labelStatus.setForeground(VERDE_ONLINE);
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    areaTexto.setText(formatarParaHtml("ERRO:\n" + ex.getMessage()));
                    labelStatus.setText("● Falha na consulta SQL.");
                    labelStatus.setForeground(VERMELHO_ERRO);
                });
            } finally {
                System.setOut(originalOut);
            }
        }).start();
    }

    public static void abrir(Connection conexao) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}

            TelaRelatorios tela = new TelaRelatorios(conexao);
            tela.setVisible(true);
        });
    }

    @FunctionalInterface
    private interface TarefaRelatorio {
        void executar() throws Exception;
    }

    private String formatarParaHtml(String textoBruto) {
        if (!textoBruto.contains("|") && !textoBruto.contains("===")) {
            return "<html><body style='font-family:Segoe UI, sans-serif; font-size:14px; color:#94A3B8; padding:20px;'>"
                 + textoBruto.replace("\n", "<br>") + "</body></html>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>")
            .append("body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #0D121E; color: #E2E8F0; margin: 15px; }")
            .append(".titulo { font-size: 16px; font-weight: bold; color: #38BDF8; margin-bottom: 15px; text-transform: uppercase; letter-spacing: 0.5px; }")
            .append("table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 14px; }")
            .append("th { background-color: #1E293B; color: #38BDF8; font-weight: 600; text-align: left; padding: 12px 14px; border-bottom: 2px solid #0284C7; font-size: 13px; text-transform: uppercase; }")
            .append("td { padding: 10px 14px; border-bottom: 1px solid #1E293B; }")
            .append(".zebrada { background-color: #131B2E; }")
            .append(".rodape { margin-top: 18px; padding: 12px 16px; background-color: #1E293B; border-left: 4px solid #22C55E; font-size: 14px; font-weight: bold; color: #F8FAFC; }")
            .append(".valor-dinheiro { color: #34D399; font-weight: 600; font-family: 'Consolas', monospace; }")
            .append("</style></head><body>");

        String[] linhas = textoBruto.split("\\r?\\n");
        boolean emTabela = false;
        boolean cabecalhoLido = false;
        int linhaIdx = 0;

        for (String linha : linhas) {
            String l = linha.trim();
            if (l.isEmpty() || l.startsWith("===") || l.startsWith("---")) continue;

            if (l.contains("RELATÓRIO")) {
                html.append("<div class='titulo'>").append(l).append("</div>");
            } else if (l.contains("|") && !l.toLowerCase().contains("total acumulado") && !l.toLowerCase().contains("total geral")) {
                if (!emTabela) {
                    html.append("<table>");
                    emTabela = true;
                }
                String[] colunas = l.split("\\|");
                if (!cabecalhoLido) {
                    html.append("<tr>");
                    for (String col : colunas) {
                        html.append("<th>").append(col.trim()).append("</th>");
                    }
                    html.append("</tr>");
                    cabecalhoLido = true;
                } else {
                    String classe = (linhaIdx++ % 2 == 1) ? " class='zebrada'" : "";
                    html.append("<tr").append(classe).append(">");
                    for (String col : colunas) {
                        String v = col.trim();
                        if (v.contains("R$")) {
                            html.append("<td class='valor-dinheiro'>").append(v).append("</td>");
                        } else {
                            html.append("<td>").append(v).append("</td>");
                        }
                    }
                    html.append("</tr>");
                }
            } else if (l.toLowerCase().contains("total") || l.toLowerCase().contains("acumulado")) {
                if (emTabela) {
                    html.append("</table>");
                    emTabela = false;
                }
                html.append("<div class='rodape'>").append(l).append("</div>");
            }
        }

        if (emTabela) html.append("</table>");
        html.append("</body></html>");
        return html.toString();
    }
}