package studybuddy.gui;

import studybuddy.controller.CatalogoTutoriasController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

public class FrmCatalogoTutorias extends JFrame {

    // Componentes públicos (mismos nombres para compatibilidad con CatalogoTutoriasController)
    public JComboBox<String> cboFiltroAsignatura;
    public JComboBox<String> cboFiltroSede;
    public JComboBox<String> cboFiltroModalidad;
    public JButton btnBuscar;
    public JButton btnLimpiar;

    public JTable tblResultados;
    public DefaultTableModel modeloTabla;
    public JButton btnReservar;
    public JButton btnVolver;

    // Paleta de colores institucionales Unimayor
    private static final Color AZUL_OSCURO = new Color(0, 51, 102);      // #003366
    private static final Color AZUL_PRIMARIO = new Color(0, 102, 204);   // #0066CC
    private static final Color AZUL_SUAVE = new Color(232, 242, 254);    // Selección de fila
    private static final Color FONDO_GENERAL = new Color(242, 245, 249); // Gris azulado muy claro
    private static final Color BORDE_TARJETA = new Color(218, 224, 232);
    private static final Color TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private static final Color VERDE_ACCION = new Color(34, 153, 84);
    private static final Color GRIS_NEUTRO = new Color(233, 236, 239);

    public FrmCatalogoTutorias() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Study Buddy Unimayor - Catálogo de Tutorías");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 620);
        setMinimumSize(new Dimension(880, 540));
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 14));
        panelPrincipal.setBackground(FONDO_GENERAL);
        panelPrincipal.setBorder(new EmptyBorder(18, 24, 18, 24));

        // =====================================================================
        // 1. CONTENEDOR SUPERIOR (Header Azul + Tarjeta de Filtros)
        // =====================================================================
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 12));
        panelSuperior.setOpaque(false);

        // --- Header Institucional ---
        JPanel panelHeader = new JPanel(new BorderLayout(15, 0));
        panelHeader.setBackground(AZUL_OSCURO);
        panelHeader.setBorder(new EmptyBorder(16, 22, 16, 22));

        JPanel panelTituloTexto = new JPanel();
        panelTituloTexto.setLayout(new BoxLayout(panelTituloTexto, BoxLayout.Y_AXIS));
        panelTituloTexto.setOpaque(false);

        JLabel lblTitulo = new JLabel("Catálogo de Tutorías Disponibles");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 21));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setIcon(crearIconoVectorial("birrete", 24, Color.WHITE));
        lblTitulo.setIconTextGap(12);

        JLabel lblSubtitulo = new JLabel("Filtra por asignatura, sede o modalidad y reserva tu cupo fácilmente");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(205, 222, 242));
        lblSubtitulo.setBorder(new EmptyBorder(4, 36, 0, 0));

        panelTituloTexto.add(lblTitulo);
        panelTituloTexto.add(lblSubtitulo);
        panelHeader.add(panelTituloTexto, BorderLayout.WEST);

        panelSuperior.add(panelHeader, BorderLayout.NORTH);

        // --- Tarjeta de Filtros ---
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(14, 18, 14, 18)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Filtro Asignatura
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelFiltros.add(crearLabelFiltro("Asignatura:", labelFont), gbc);

        cboFiltroAsignatura = new JComboBox<>(new String[]{
            "Todas", "Cálculo I", "Cálculo II", "Programación I", "Física Mecánica", "Bases de Datos"
        });
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFiltros.add(estilizarCombo(cboFiltroAsignatura, fieldFont), gbc);

        // Filtro Sede
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        panelFiltros.add(crearLabelFiltro("Sede:", labelFont), gbc);

        cboFiltroSede = new JComboBox<>(new String[]{
            "Todas", "Bicentenario", "Casa Obando", "Encarnación", "Virtual"
        });
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.9;
        panelFiltros.add(estilizarCombo(cboFiltroSede, fieldFont), gbc);

        // Filtro Modalidad
        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0;
        panelFiltros.add(crearLabelFiltro("Modalidad:", labelFont), gbc);

        cboFiltroModalidad = new JComboBox<>(new String[]{
            "Todas", "Presencial", "Virtual"
        });
        gbc.gridx = 5; gbc.gridy = 0; gbc.weightx = 0.8;
        panelFiltros.add(estilizarCombo(cboFiltroModalidad, fieldFont), gbc);

        // Botón Buscar con ícono
        gbc.gridx = 6; gbc.gridy = 0; gbc.weightx = 0;
        gbc.insets = new Insets(4, 6, 4, 6);
        btnBuscar = crearBoton("Buscar", AZUL_PRIMARIO, Color.WHITE, crearIconoVectorial("buscar", 14, Color.WHITE));
        btnBuscar.setPreferredSize(new Dimension(112, 36));
        panelFiltros.add(btnBuscar, gbc);

        // Botón Limpiar con ícono
        gbc.gridx = 7; gbc.gridy = 0; gbc.weightx = 0;
        gbc.insets = new Insets(4, 2, 4, 0);
        btnLimpiar = crearBoton("Limpiar", GRIS_NEUTRO, TEXTO_PRINCIPAL, crearIconoVectorial("limpiar", 14, TEXTO_PRINCIPAL));
        btnLimpiar.setPreferredSize(new Dimension(112, 36));
        panelFiltros.add(btnLimpiar, gbc);

        panelSuperior.add(panelFiltros, BorderLayout.CENTER);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // =====================================================================
        // 2. TABLA DE RESULTADOS (Tarjeta central)
        // =====================================================================
        String[] columnas = {"ID", "Asignatura", "Tema", "Tutor", "Fecha", "Hora", "Modalidad", "Sede", "Cupos Disp."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblResultados = new JTable(modeloTabla);
        tblResultados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblResultados.setRowHeight(36);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResultados.setShowVerticalLines(false);
        tblResultados.setShowHorizontalLines(true);
        tblResultados.setGridColor(new Color(235, 238, 242));
        tblResultados.setIntercellSpacing(new Dimension(0, 1));
        tblResultados.setFillsViewportHeight(true);

        // Estilo del encabezado de tabla (Títulos en negrilla y fondo azul oscuro)
        JTableHeader header = tblResultados.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(AZUL_OSCURO);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(AZUL_OSCURO);
        headerRenderer.setForeground(Color.WHITE);

        // Renderizador personalizado de celdas (filas alternadas + alineación + badge en cupos)
        tblResultados.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                celda.setBorder(new EmptyBorder(0, 10, 0, 10));

                if (isSelected) {
                    celda.setBackground(AZUL_SUAVE);
                    celda.setForeground(AZUL_OSCURO);
                } else {
                    celda.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    celda.setForeground(TEXTO_PRINCIPAL);
                }

                // Alineación y énfasis según la columna
                if (column == 0 || column == 4 || column == 5 || column == 6) {
                    celda.setHorizontalAlignment(SwingConstants.CENTER);
                    celda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                } else if (column == 1) {
                    celda.setHorizontalAlignment(SwingConstants.LEFT);
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Asignatura en negrilla
                } else if (column == 8) {
                    celda.setHorizontalAlignment(SwingConstants.CENTER);
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    if (!isSelected) {
                        celda.setForeground(VERDE_ACCION);
                    }
                } else {
                    celda.setHorizontalAlignment(SwingConstants.LEFT);
                    celda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                }

                return celda;
            }
        });

        configurarAnchosColumnas();

        JScrollPane scrollPane = new JScrollPane(tblResultados);
        scrollPane.setBorder(new LineBorder(BORDE_TARJETA, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(12, 16, 16, 16)
        ));

        JLabel lblSeccionTabla = new JLabel("Sesiones programadas (selecciona una fila para reservar)");
        lblSeccionTabla.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSeccionTabla.setForeground(AZUL_OSCURO);
        panelTabla.add(lblSeccionTabla, BorderLayout.NORTH);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // =====================================================================
        // 3. BARRA INFERIOR DE ACCIONES
        // =====================================================================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(4, 12, 4, 12)
        ));

        btnVolver = crearBoton("Volver al Menú", GRIS_NEUTRO, TEXTO_PRINCIPAL, crearIconoVectorial("volver", 14, TEXTO_PRINCIPAL));
        btnVolver.setPreferredSize(new Dimension(155, 38));

        btnReservar = crearBoton("Reservar Cupo", VERDE_ACCION, Color.WHITE, crearIconoVectorial("check", 14, Color.WHITE));
        btnReservar.setPreferredSize(new Dimension(165, 38));

        panelBotones.add(btnVolver);
        panelBotones.add(btnReservar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void configurarAnchosColumnas() {
        TableColumnModel colModel = tblResultados.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(42);   // ID
        colModel.getColumn(0).setMaxWidth(55);
        colModel.getColumn(1).setPreferredWidth(145);  // Asignatura
        colModel.getColumn(2).setPreferredWidth(185);  // Tema
        colModel.getColumn(3).setPreferredWidth(130);  // Tutor
        colModel.getColumn(4).setPreferredWidth(95);   // Fecha
        colModel.getColumn(5).setPreferredWidth(105);  // Hora
        colModel.getColumn(6).setPreferredWidth(90);   // Modalidad
        colModel.getColumn(7).setPreferredWidth(110);  // Sede
        colModel.getColumn(8).setPreferredWidth(85);   // Cupos Disp.
    }

    private JLabel crearLabelFiltro(String texto, Font font) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(font);
        lbl.setForeground(AZUL_OSCURO);
        return lbl;
    }

    private JComboBox<String> estilizarCombo(JComboBox<String> combo, Font font) {
        combo.setFont(font);
        combo.setBackground(new Color(250, 251, 253));
        combo.setForeground(TEXTO_PRINCIPAL);
        combo.setPreferredSize(new Dimension(130, 36));
        return combo;
    }

    private JButton crearBoton(String texto, Color bg, Color fg, Icon icono) {
        JButton btn = new JButton(texto, icono);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setIconTextGap(8);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover sutil
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    /**
     * Dibuja íconos vectoriales nítidos sin depender de archivos externos ni fuentes emoji.
     */
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
                    case "birrete": {
                        Path2D gorro = new Path2D.Double();
                        gorro.moveTo(size * 0.5, size * 0.15);
                        gorro.lineTo(size * 0.95, size * 0.38);
                        gorro.lineTo(size * 0.5, size * 0.60);
                        gorro.lineTo(size * 0.05, size * 0.38);
                        gorro.closePath();
                        g2.fill(gorro);
                        g2.drawArc((int) (size * 0.22), (int) (size * 0.45), (int) (size * 0.56), (int) (size * 0.35), 180, 180);
                        g2.drawLine((int) (size * 0.90), (int) (size * 0.40), (int) (size * 0.90), (int) (size * 0.72));
                        break;
                    }
                    case "buscar": {
                        int d = (int) (size * 0.62);
                        g2.drawOval(1, 1, d, d);
                        g2.drawLine(d, d, size - 2, size - 2);
                        break;
                    }
                    case "limpiar": {
                        // Ícono de reinicio / limpiar filtro (flecha circular)
                        g2.drawArc(2, 2, size - 4, size - 4, 45, 270);
                        g2.drawLine(size - 3, 2, size - 3, 6);
                        g2.drawLine(size - 3, 6, size - 7, 6);
                        break;
                    }
                    case "check": {
                        g2.drawLine((int) (size * 0.15), (int) (size * 0.52), (int) (size * 0.42), (int) (size * 0.78));
                        g2.drawLine((int) (size * 0.42), (int) (size * 0.78), (int) (size * 0.85), (int) (size * 0.25));
                        break;
                    }
                    case "volver": {
                        int mid = size / 2;
                        g2.drawLine(3, mid, size - 3, mid);
                        g2.drawLine(3, mid, mid, 3);
                        g2.drawLine(3, mid, mid, size - 3);
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

    public void setController(CatalogoTutoriasController controller) {
        this.btnBuscar.addActionListener(controller);
        this.btnLimpiar.addActionListener(controller);
        this.btnReservar.addActionListener(controller);
        this.btnVolver.addActionListener(controller);
    }
}
