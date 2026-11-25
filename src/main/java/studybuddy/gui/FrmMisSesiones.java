package studybuddy.gui;

import studybuddy.controller.MisSesionesController;

import studybuddy.controller.MisSesionesController;

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

public class FrmMisSesiones extends JFrame {

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================
    private static final Color AZUL_OSCURO = new Color(7, 52, 94);
    private static final Color AZUL_BOTON = new Color(0, 105, 190);
    private static final Color VERDE = new Color(34, 153, 87);
    private static final Color ROJO = new Color(200, 61, 77);
    private static final Color GRIS = new Color(104, 119, 137);
    private static final Color FONDO = new Color(241, 244, 248);
    private static final Color BORDE_TARJETA = new Color(215, 221, 228);
    private static final Color FILA_ALTERNA = new Color(248, 250, 252);
    private static final Color SELECCION_TABLA = new Color(225, 238, 252);

    // ==========================
    // COMPONENTES PÚBLICOS (MVC)
    // ==========================
    public JTable tblSesiones;
    public DefaultTableModel modeloTabla;
    public JButton btnEditar;
    public JButton btnCancelar;
    public JButton btnVolver;
    public JButton btnAsistencia;

    public FrmMisSesiones() {
        initComponents();
    }

    private void initComponents() {
        setTitle("StudyBuddy Unimayor - Mis sesiones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(920, 620);
        setMinimumSize(new Dimension(820, 540));
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

        JLabel lblIcono = new JLabel(crearIconoVectorial("sesiones", 42, Color.WHITE));
        lblIcono.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelTextos = new JPanel();
        panelTextos.setOpaque(false);
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Gestión de Mis Sesiones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Administra tus tutorías programadas, edita detalles o registra la asistencia");
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

        JLabel lblSeccion = new JLabel("Sesiones registradas");
        lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblSeccion.setForeground(AZUL_OSCURO);

        JLabel lblAyuda = new JLabel("Selecciona una fila para habilitar las acciones");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAyuda.setForeground(new Color(110, 125, 140));

        panelCabeceraTarjeta.add(lblSeccion, BorderLayout.WEST);
        panelCabeceraTarjeta.add(lblAyuda, BorderLayout.EAST);

        panelTarjeta.add(panelCabeceraTarjeta, BorderLayout.NORTH);

        // ==========================
        // TABLA ESTILIZADA
        // ==========================
        String[] columnas = {"ID", "Asignatura", "Tema", "Fecha", "Hora", "Modalidad", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSesiones = new JTable(modeloTabla);
        estilizarTabla(tblSesiones);

        JScrollPane scrollPane = crearScrollEstilizado(tblSesiones);
        scrollPane.setBorder(new LineBorder(BORDE_TARJETA, 1, true));
        panelTarjeta.add(scrollPane, BorderLayout.CENTER);

        // ==========================
        // BARRA DE BOTONES
        // ==========================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        panelBotones.setBackground(Color.WHITE);

        btnAsistencia = crearBoton(
                "Registrar asistencia",
                VERDE,
                crearIconoVectorial("asistencia", 16, Color.WHITE),
                195
        );
        btnEditar = crearBoton(
                "Editar sesión",
                AZUL_BOTON,
                crearIconoVectorial("editar", 16, Color.WHITE),
                165
        );
        btnCancelar = crearBoton(
                "Cancelar sesión",
                ROJO,
                crearIconoVectorial("cancelar", 16, Color.WHITE),
                175
        );
        btnVolver = crearBoton(
                "Volver al menú",
                GRIS,
                crearIconoVectorial("volver", 16, Color.WHITE),
                165
        );

        panelBotones.add(btnAsistencia);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCancelar);
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
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        tabla.getColumnModel().getColumn(0).setMaxWidth(55);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(95);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(115);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(95);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(115);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                cell.setBorder(new EmptyBorder(0, 10, 0, 10));

                if (!isSelected) {
                    cell.setBackground(row % 2 == 0 ? Color.WHITE : FILA_ALTERNA);
                    cell.setForeground(new Color(45, 55, 72));
                }

                if (column == 0) {
                    cell.setHorizontalAlignment(SwingConstants.CENTER);
                    cell.setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else {
                    cell.setHorizontalAlignment(SwingConstants.LEFT);
                    cell.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                }

                if (column == 6 && value != null) {
                    String estado = value.toString().toUpperCase();
                    cell.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    if (!isSelected) {
                        if (estado.contains("COMPLETADA")) {
                            cell.setForeground(VERDE);
                        } else if (estado.contains("CANCELADA")) {
                            cell.setForeground(ROJO);
                        } else {
                            cell.setForeground(AZUL_BOTON);
                        }
                    }
                }

                return cell;
            }
        };

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
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
                    case "sesiones": {
                        g2.drawRoundRect(2, 4, size - 4, size - 6, 3, 3);
                        g2.drawLine(2, 9, size - 2, 9);
                        g2.drawLine(7, 2, 7, 6);
                        g2.drawLine(size - 7, 2, size - 7, 6);
                        break;
                    }
                    case "asistencia": {
                        g2.drawOval(2, 2, size - 4, size - 4);
                        g2.drawLine(5, size / 2, size / 2 - 1, size - 5);
                        g2.drawLine(size / 2 - 1, size - 5, size - 5, 5);
                        break;
                    }
                    case "editar": {
                        g2.drawLine(3, size - 3, 7, size - 3);
                        g2.drawLine(3, size - 3, 3, size - 7);
                        g2.drawLine(3, size - 7, size - 6, 2);
                        g2.drawLine(size - 6, 2, size - 2, 6);
                        g2.drawLine(size - 2, 6, 7, size - 3);
                        break;
                    }
                    case "cancelar": {
                        g2.drawOval(2, 2, size - 4, size - 4);
                        g2.drawLine(5, 5, size - 5, size - 5);
                        g2.drawLine(size - 5, 5, 5, size - 5);
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

    public void setController(MisSesionesController controller) {
        this.btnEditar.addActionListener(controller);
        this.btnCancelar.addActionListener(controller);
        this.btnVolver.addActionListener(controller);
        this.btnAsistencia.addActionListener(controller);
    }
}