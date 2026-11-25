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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmRegistrarAsistencia extends JFrame {

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================
    private static final Color AZUL_OSCURO = new Color(7, 52, 94);
    private static final Color AZUL_BOTON = new Color(0, 105, 190);
    private static final Color VERDE = new Color(34, 153, 87);
    private static final Color NARANJA = new Color(214, 138, 22);
    private static final Color ROJO = new Color(200, 61, 77);
    private static final Color GRIS = new Color(104, 119, 137);
    private static final Color FONDO = new Color(241, 244, 248);
    private static final Color BORDE_TARJETA = new Color(215, 221, 228);
    private static final Color FILA_ALTERNA = new Color(248, 250, 252);
    private static final Color SELECCION_TABLA = new Color(225, 238, 252);

    // ==========================
    // COMPONENTES PÚBLICOS (MVC)
    // ==========================
    public JTable tblAsistencia;
    public DefaultTableModel modeloTabla;
    public JButton btnFinalizar;
    public JButton btnGuardar;
    public JButton btnVolver;

    public FrmRegistrarAsistencia(String tituloSesion) {
        initComponents(tituloSesion);
    }

    private void initComponents(String tituloSesion) {
        setTitle("StudyBuddy Unimayor - Registrar asistencia");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(860, 580);
        setMinimumSize(new Dimension(760, 500));
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

        JLabel lblIcono = new JLabel(crearIconoVectorial("asistencia", 42, Color.WHITE));
        lblIcono.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelTextos = new JPanel();
        panelTextos.setOpaque(false);
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Control de Asistencia");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sesión: " + tituloSesion);
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

        // Cabecera de la tarjeta
        JPanel panelCabeceraTarjeta = new JPanel(new BorderLayout());
        panelCabeceraTarjeta.setBackground(Color.WHITE);

        JLabel lblSeccion = new JLabel("Asistentes de la sesión");
        lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblSeccion.setForeground(AZUL_OSCURO);

        JLabel lblAyuda = new JLabel("Finaliza la sesión antes de guardar la asistencia");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(110, 125, 140));

        panelCabeceraTarjeta.add(lblSeccion, BorderLayout.WEST);
        panelCabeceraTarjeta.add(lblAyuda, BorderLayout.EAST);

        panelTarjeta.add(panelCabeceraTarjeta, BorderLayout.NORTH);

        // ==========================
        // TABLA ESTILIZADA
        // ==========================
        String[] cols = {"ID Reserva", "Estudiante", "Estado actual", "Asistió (Sí/No)"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 3;
            }
        };

        tblAsistencia = new JTable(modeloTabla);
        estilizarTabla(tblAsistencia);

        JScrollPane scrollPane = crearScrollEstilizado(tblAsistencia);
        scrollPane.setBorder(new LineBorder(BORDE_TARJETA, 1, true));
        panelTarjeta.add(scrollPane, BorderLayout.CENTER);

        // ==========================
        // BARRA DE BOTONES
        // ==========================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        panelBotones.setBackground(Color.WHITE);

        btnFinalizar = crearBoton(
                "Finalizar sesión",
                NARANJA,
                crearIconoVectorial("finalizar", 16, Color.WHITE),
                175
        );
        btnGuardar = crearBoton(
                "Guardar asistencia",
                VERDE,
                crearIconoVectorial("guardar", 16, Color.WHITE),
                190
        );
        btnVolver = crearBoton(
                "Volver",
                GRIS,
                crearIconoVectorial("volver", 16, Color.WHITE),
                140
        );

        panelBotones.add(btnFinalizar);
        panelBotones.add(btnGuardar);
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

        setContentPane(panelPrincipal);
        revalidate();
        repaint();
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(36);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(234, 238, 243));
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setSelectionBackground(SELECCION_TABLA);
        tabla.setSelectionForeground(AZUL_OSCURO);
        tabla.setFillsViewportHeight(true);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(244, 247, 251));
        header.setForeground(AZUL_OSCURO);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        // Ocultar columna ID Reserva
        tabla.getColumn("ID Reserva").setMinWidth(0);
        tabla.getColumn("ID Reserva").setMaxWidth(0);
        tabla.getColumn("ID Reserva").setPreferredWidth(0);

        // Anchos de columnas visibles
        tabla.getColumnModel().getColumn(1).setPreferredWidth(340);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(140);

        // Desplegable "Sí" / "No" compatible con AsistenciaController
        JComboBox<String> comboAsistencia = new JComboBox<>(new String[]{"Sí", "No"});
        comboAsistencia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboAsistencia));

        // Renderizador uniforme para todas las columnas visibles
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                cell.setBorder(new EmptyBorder(0, 12, 0, 12));

                if (!isSelected) {
                    cell.setBackground(row % 2 == 0 ? Color.WHITE : FILA_ALTERNA);
                    cell.setForeground(new Color(45, 55, 72));
                }

                if (column == 2 && value != null) {
                    String estado = value.toString().toUpperCase();
                    cell.setHorizontalAlignment(SwingConstants.LEFT);
                    cell.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    if (!isSelected) {
                        if (estado.contains("NO_ASISTIO")) {
                            cell.setForeground(ROJO);
                        } else if (estado.contains("ASISTIO")) {
                            cell.setForeground(VERDE);
                        } else {
                            cell.setForeground(AZUL_BOTON);
                        }
                    }
                } else if (column == 3 && value != null) {
                    String resp = value.toString();
                    cell.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    if (!isSelected) {
                        cell.setForeground("Sí".equalsIgnoreCase(resp) ? VERDE : ROJO);
                    }
                } else {
                    cell.setHorizontalAlignment(SwingConstants.LEFT);
                    cell.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                }

                return cell;
            }
        };

        tabla.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
    }

    private JScrollPane crearScrollEstilizado(Component contenido) {
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);

        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setBackground(Color.WHITE);
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(195, 205, 218);
                this.thumbHighlightColor = new Color(160, 175, 192);
                this.trackColor = Color.WHITE;
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
                    case "asistencia": {
                        g2.drawOval(2, 2, size - 4, size - 4);
                        g2.drawLine(5, size / 2, size / 2 - 1, size - 5);
                        g2.drawLine(size / 2 - 1, size - 5, size - 5, 5);
                        break;
                    }
                    case "finalizar": {
                        g2.drawOval(2, 2, size - 4, size - 4);
                        int pad = size / 3;
                        g2.fillRect(pad, pad, size - pad * 2, size - pad * 2);
                        break;
                    }
                    case "guardar": {
                        g2.drawRoundRect(2, 2, size - 4, size - 4, 3, 3);
                        g2.drawRect(5, 2, size - 10, 5);
                        g2.drawRect(5, size - 7, size - 10, 5);
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