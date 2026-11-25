/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.gui;

/**
 *
 * @author yoban
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmReporteHoras extends JFrame {

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================
    private static final Color AZUL_OSCURO = new Color(7, 52, 94);
    private static final Color AZUL_BOTON = new Color(0, 105, 190);
    private static final Color GRIS = new Color(104, 119, 137);
    private static final Color FONDO = new Color(241, 244, 248);
    private static final Color BORDE_TARJETA = new Color(215, 221, 228);
    private static final Color BORDE_CAMPO = new Color(200, 208, 218);
    private static final Color FONDO_RESULTADO = new Color(248, 250, 252);

    // ==========================
    // COMPONENTES PÚBLICOS (MVC)
    // ==========================
    public JTextField txtFechaInicio;
    public JTextField txtFechaFin;
    public JComboBox<String> cmbTutores;
    public JButton btnGenerar;
    public JButton btnVolver;
    public JTextArea txtResultado;

    public FrmReporteHoras(boolean esAdministrador) {
        initComponents(esAdministrador);
    }

    private void initComponents(boolean esAdministrador) {
        setTitle("StudyBuddy Unimayor - Reporte de horas impartidas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(740, esAdministrador ? 640 : 600);
        setMinimumSize(new Dimension(640, 520));
        setLocationRelativeTo(null);

        // ==========================
        // PANEL PRINCIPAL
        // ==========================
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(FONDO);
        panelPrincipal.setBorder(new EmptyBorder(24, 30, 20, 30));

        // ==========================
        // ENCABEZADO
        // ==========================
        JPanel panelHeader = new JPanel(new BorderLayout(18, 0));
        panelHeader.setBackground(AZUL_OSCURO);
        panelHeader.setBorder(new EmptyBorder(20, 26, 20, 26));

        JLabel lblIcono = new JLabel(crearIconoVectorial("reporte", 42, Color.WHITE));
        lblIcono.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelTextos = new JPanel();
        panelTextos.setOpaque(false);
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Reporte de Horas Impartidas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String subtitulo = esAdministrador
                ? "Consulta el consolidado de horas por tutor o monitor en un rango de fechas"
                : "Consulta el resumen de tus horas de tutoría impartidas por período";

        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(185, 208, 234));
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelTextos.add(lblTitulo);
        panelTextos.add(Box.createVerticalStrut(4));
        panelTextos.add(lblSubtitulo);

        panelHeader.add(lblIcono, BorderLayout.WEST);
        panelHeader.add(panelTextos, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ==========================
        // TARJETA DE CONTENIDO
        // ==========================
        JPanel panelTarjeta = new JPanel(new BorderLayout(0, 16));
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(22, 26, 22, 26)
        ));

        // Sección superior: Filtros de consulta
        JPanel panelFiltros = new JPanel(new BorderLayout(0, 12));
        panelFiltros.setBackground(Color.WHITE);

        JLabel lblSeccionFiltros = new JLabel("Parámetros de consulta");
        lblSeccionFiltros.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSeccionFiltros.setForeground(AZUL_OSCURO);
        panelFiltros.add(lblSeccionFiltros, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Fecha inicio
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        campos.add(crearEtiqueta("Fecha inicio (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 0, 6, 0);
        txtFechaInicio = crearCampoTexto();
        campos.add(txtFechaInicio, gbc);

        // Fila 1: Fecha fin
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(6, 0, 6, 12);
        campos.add(crearEtiqueta("Fecha fin (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 0, 6, 0);
        txtFechaFin = crearCampoTexto();
        campos.add(txtFechaFin, gbc);

        // Fila 2 (opcional): Tutor/Monitor
        if (esAdministrador) {
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0.0;
            gbc.insets = new Insets(6, 0, 6, 12);
            campos.add(crearEtiqueta("Tutor / Monitor:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.insets = new Insets(6, 0, 6, 0);
            cmbTutores = new JComboBox<>();
            estilizarCombo(cmbTutores);
            campos.add(cmbTutores, gbc);
        }

        panelFiltros.add(campos, BorderLayout.CENTER);
        panelTarjeta.add(panelFiltros, BorderLayout.NORTH);

        // Sección central: Resultado del reporte
        JPanel panelCentro = new JPanel(new BorderLayout(0, 8));
        panelCentro.setBackground(Color.WHITE);

        JLabel lblSeccionResultado = new JLabel("Detalle del reporte");
        lblSeccionResultado.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSeccionResultado.setForeground(AZUL_OSCURO);
        panelCentro.add(lblSeccionResultado, BorderLayout.NORTH);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtResultado.setBackground(FONDO_RESULTADO);
        txtResultado.setForeground(new Color(45, 55, 72));
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        txtResultado.setBorder(new EmptyBorder(12, 14, 12, 14));

        JScrollPane scrollResultado = crearScrollEstilizado(txtResultado);
        scrollResultado.setBorder(new LineBorder(BORDE_TARJETA, 1, true));
        panelCentro.add(scrollResultado, BorderLayout.CENTER);

        panelTarjeta.add(panelCentro, BorderLayout.CENTER);

        // Sección inferior: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        panelBotones.setBackground(Color.WHITE);

        btnGenerar = crearBoton(
                "Generar reporte",
                AZUL_BOTON,
                crearIconoVectorial("generar", 16, Color.WHITE),
                185
        );
        btnVolver = crearBoton(
                "Volver",
                GRIS,
                crearIconoVectorial("volver", 16, Color.WHITE),
                145
        );

        panelBotones.add(btnGenerar);
        panelBotones.add(btnVolver);

        panelTarjeta.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelTarjeta, BorderLayout.CENTER);

        // ==========================
        // PIE DE PÁGINA
        // ==========================
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setBackground(FONDO);

        JLabel lblFooter = new JLabel("StudyBuddy Unimayor | Acompañamiento académico");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(110, 125, 140));

        panelFooter.add(lblFooter);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(55, 68, 84));
        return lbl;
    }

    private JTextField crearCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setForeground(new Color(35, 45, 60));
        txt.setBackground(Color.WHITE);
        txt.setPreferredSize(new Dimension(220, 36));

        Border bordeNormal = BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_CAMPO, 1, true),
                new EmptyBorder(4, 10, 4, 10)
        );
        Border bordeFoco = BorderFactory.createCompoundBorder(
                new LineBorder(AZUL_BOTON, 2, true),
                new EmptyBorder(3, 9, 3, 9)
        );

        txt.setBorder(bordeNormal);
        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setBorder(bordeFoco);
            }

            @Override
            public void focusLost(FocusEvent e) {
                txt.setBorder(bordeNormal);
            }
        });

        return txt;
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setForeground(new Color(35, 45, 60));
        combo.setPreferredSize(new Dimension(220, 36));
        combo.setBorder(new LineBorder(BORDE_CAMPO, 1, true));
    }

    private JScrollPane crearScrollEstilizado(Component contenido) {
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBackground(FONDO_RESULTADO);
        scroll.getViewport().setBackground(FONDO_RESULTADO);

        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setBackground(FONDO_RESULTADO);
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(195, 205, 218);
                this.thumbHighlightColor = new Color(160, 175, 192);
                this.trackColor = FONDO_RESULTADO;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return crearBotonCero();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return crearBotonCero();
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isDragging ? AZUL_BOTON : thumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 2, thumbBounds.width - 2, thumbBounds.height - 4, 8, 8);
                g2.dispose();
            }

            private JButton crearBotonCero() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                btn.setMinimumSize(new Dimension(0, 0));
                btn.setMaximumSize(new Dimension(0, 0));
                return btn;
            }
        });

        return scroll;
    }

    private JButton crearBoton(String texto, Color colorFondo, Icon icono, int ancho) {
        JButton btn = new JButton(texto, icono);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setIconTextGap(9);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(ancho, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(colorFondo.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(colorFondo);
            }
        });

        return btn;
    }

    private Icon crearIconoVectorial(String tipo, int size, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.translate(x, y);

                switch (tipo) {
                    case "reporte": {
                        g2.drawRoundRect(3, 2, size - 6, size - 4, 3, 3);
                        g2.drawLine(8, size - 7, 8, size - 13);
                        g2.drawLine(size / 2, size - 7, size / 2, 10);
                        g2.drawLine(size - 8, size - 7, size - 8, 15);
                        break;
                    }
                    case "generar": {
                        g2.drawRoundRect(2, 2, size - 4, size - 4, 3, 3);
                        g2.drawLine(5, 6, size - 5, 6);
                        g2.drawLine(5, 9, size - 5, 9);
                        g2.drawLine(5, 12, size - 8, 12);
                        break;
                    }
                    case "volver": {
                        int mid = size / 2;
                        g2.drawLine(4, mid, size - 3, mid);
                        g2.drawLine(4, mid, mid + 1, 4);
                        g2.drawLine(4, mid, mid + 1, size - 4);
                        break;
                    }
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
}