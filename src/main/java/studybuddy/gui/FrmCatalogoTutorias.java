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
import java.util.ArrayList;
import java.util.List;

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
    public JButton btnReservarGrupal;

    // Paleta de colores institucionales Unimayor
    private static final Color AZUL_OSCURO = new Color(0, 51, 102);      // #003366
    private static final Color AZUL_PRIMARIO = new Color(0, 102, 204);   // #0066CC
    private static final Color AZUL_SUAVE = new Color(232, 242, 254);    // Selección de fila
    private static final Color FONDO_GENERAL = new Color(242, 245, 249); // Gris azulado muy claro
    private static final Color BORDE_TARJETA = new Color(218, 224, 232);
    private static final Color TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private static final Color VERDE_ACCION = new Color(34, 153, 84);
    private static final Color NARANJA_GRUPAL = new Color(214, 138, 22);
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

        JLabel lblSubtitulo = new JLabel("Filtra por asignatura, sede o modalidad y reserva tu cupo individual o grupal");
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

                if (column == 0 || column == 4 || column == 5 || column == 6) {
                    celda.setHorizontalAlignment(SwingConstants.CENTER);
                    celda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                } else if (column == 1) {
                    celda.setHorizontalAlignment(SwingConstants.LEFT);
                    celda.setFont(new Font("Segoe UI", Font.BOLD, 13));
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

        btnReservarGrupal = crearBoton("Reserva Grupal", NARANJA_GRUPAL, Color.WHITE,
                crearIconoVectorial("grupo", 14, Color.WHITE));
        btnReservarGrupal.setPreferredSize(new Dimension(165, 38));

        btnReservar = crearBoton("Reservar Cupo", VERDE_ACCION, Color.WHITE, crearIconoVectorial("check", 14, Color.WHITE));
        btnReservar.setPreferredSize(new Dimension(165, 38));

        panelBotones.add(btnVolver);
        panelBotones.add(btnReservarGrupal);
        panelBotones.add(btnReservar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Despliega un diálogo modal estilizado para armar el grupo de estudiantes.
     *
     * @return Lista de IDs de estudiantes confirmados, o null si el usuario canceló.
     */
    public List<Integer> mostrarDialogoReservaGrupal(String asignatura, String fechaHora,
                                                     int cuposDisponibles, int idEstudianteActual,
                                                     String nombreEstudianteActual) {
        final List<Integer> resultado = new ArrayList<>();
        final boolean[] confirmado = {false};

        JDialog dialogo = new JDialog(this, "Reserva Grupal de Tutoría", true);
        dialogo.setSize(490, 460);
        dialogo.setResizable(false);
        dialogo.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(FONDO_GENERAL);
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        // 1. Header Institucional
        JPanel header = new JPanel(new BorderLayout(10, 4));
        header.setBackground(AZUL_OSCURO);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel lblTitulo = new JLabel("Reserva Grupal: " + asignatura);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setIcon(crearIconoVectorial("grupo", 20, Color.WHITE));
        lblTitulo.setIconTextGap(10);

        JLabel lblDetalle = new JLabel("Horario: " + fechaHora + "  •  Cupos libres: " + cuposDisponibles);
        lblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDetalle.setForeground(new Color(205, 222, 242));

        header.add(lblTitulo, BorderLayout.NORTH);
        header.add(lblDetalle, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // 2. Tarjeta Central
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JPanel panelAgregar = new JPanel(new BorderLayout(8, 0));
        panelAgregar.setOpaque(false);

        JTextField txtNuevoId = new JTextField();
        txtNuevoId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNuevoId.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        txtNuevoId.setToolTipText("Ingresa el ID numérico del compañero");

        JButton btnAgregar = crearBoton("Añadir ID", AZUL_PRIMARIO, Color.WHITE, null);
        btnAgregar.setPreferredSize(new Dimension(105, 34));

        JLabel lblInstruccion = new JLabel("Ingresa el ID de cada compañero y presiona Añadir:");
        lblInstruccion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInstruccion.setForeground(AZUL_OSCURO);
        lblInstruccion.setBorder(new EmptyBorder(0, 0, 6, 0));

        panelAgregar.add(lblInstruccion, BorderLayout.NORTH);
        panelAgregar.add(txtNuevoId, BorderLayout.CENTER);
        panelAgregar.add(btnAgregar, BorderLayout.EAST);
        card.add(panelAgregar, BorderLayout.NORTH);

        // Tabla de integrantes del grupo
        DefaultTableModel modeloGrupo = new DefaultTableModel(new String[]{"ID Estudiante", "Rol en el grupo"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        modeloGrupo.addRow(new Object[]{idEstudianteActual, nombreEstudianteActual + " (Tú - Organizador)"});
        resultado.add(idEstudianteActual);

        JTable tblGrupo = new JTable(modeloGrupo);
        tblGrupo.setRowHeight(30);
        tblGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGrupo.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblGrupo.getTableHeader().setBackground(new Color(240, 244, 248));
        tblGrupo.getTableHeader().setForeground(AZUL_OSCURO);

        JScrollPane scrollGrupo = new JScrollPane(tblGrupo);
        scrollGrupo.setBorder(new LineBorder(BORDE_TARJETA, 1));
        card.add(scrollGrupo, BorderLayout.CENTER);

        // Barra de estado del grupo
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setOpaque(false);

        JLabel lblContador = new JLabel("Integrantes seleccionados: 1 de " + cuposDisponibles + " cupos");
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblContador.setForeground(VERDE_ACCION);

        JButton btnQuitar = crearBoton("Quitar seleccionado", GRIS_NEUTRO, TEXTO_PRINCIPAL, null);
        btnQuitar.setPreferredSize(new Dimension(150, 28));
        btnQuitar.setFont(new Font("Segoe UI", Font.BOLD, 11));

        panelEstado.add(lblContador, BorderLayout.WEST);
        panelEstado.add(btnQuitar, BorderLayout.EAST);
        card.add(panelEstado, BorderLayout.SOUTH);

        root.add(card, BorderLayout.CENTER);

        // 3. Botones inferiores
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new LineBorder(BORDE_TARJETA, 1, true));

        JButton btnCancelar = crearBoton("Cancelar", GRIS_NEUTRO, TEXTO_PRINCIPAL, null);
        btnCancelar.setPreferredSize(new Dimension(115, 36));

        JButton btnConfirmar = crearBoton("Confirmar Reserva", VERDE_ACCION, Color.WHITE, crearIconoVectorial("check", 14, Color.WHITE));
        btnConfirmar.setPreferredSize(new Dimension(175, 36));

        footer.add(btnCancelar);
        footer.add(btnConfirmar);
        root.add(footer, BorderLayout.SOUTH);

        // Lógica de interacción del diálogo
        Runnable accionAgregar = () -> {
            String raw = txtNuevoId.getText().trim();
            if (raw.isEmpty()) return;
            try {
                int nuevoId = Integer.parseInt(raw);
                if (nuevoId <= 0) {
                    JOptionPane.showMessageDialog(dialogo, "El ID debe ser un número positivo.", "ID inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (resultado.contains(nuevoId)) {
                    JOptionPane.showMessageDialog(dialogo, "Ese estudiante ya está en la lista.", "Duplicado", JOptionPane.INFORMATION_MESSAGE);
                    txtNuevoId.setText("");
                    return;
                }
                if (resultado.size() >= cuposDisponibles) {
                    JOptionPane.showMessageDialog(dialogo, "Ya alcanzaste el límite de " + cuposDisponibles + " cupos disponibles.", "Sin más cupos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                resultado.add(nuevoId);
                modeloGrupo.addRow(new Object[]{nuevoId, "Integrante invitado"});
                lblContador.setText("Integrantes seleccionados: " + resultado.size() + " de " + cuposDisponibles + " cupos");
                txtNuevoId.setText("");
                txtNuevoId.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Ingresa solo el número de ID del estudiante.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
            }
        };

        btnAgregar.addActionListener(e -> accionAgregar.run());
        txtNuevoId.addActionListener(e -> accionAgregar.run());

        btnQuitar.addActionListener(e -> {
            int fila = tblGrupo.getSelectedRow();
            if (fila == -1) return;
            if (fila == 0) {
                JOptionPane.showMessageDialog(dialogo, "No puedes quitarte a ti mismo como organizador.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            resultado.remove(fila);
            modeloGrupo.removeRow(fila);
            lblContador.setText("Integrantes seleccionados: " + resultado.size() + " de " + cuposDisponibles + " cupos");
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        btnConfirmar.addActionListener(e -> {
            confirmado[0] = true;
            dialogo.dispose();
        });

        dialogo.setContentPane(root);
        dialogo.setVisible(true);

        return confirmado[0] ? resultado : null;
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
                    case "grupo": {
                        g2.drawOval(2, 2, size / 2, size / 2);
                        g2.drawOval(size / 2, 2, size / 2, size / 2);
                        g2.drawArc(2, size / 2, size - 4, size / 2, 0, 180);
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
        // Evita duplicar listeners si ya fueron registrados en el constructor del controlador
        for (var l : this.btnBuscar.getActionListeners()) {
            if (l == controller) return;
        }
        this.btnBuscar.addActionListener(controller);
        this.btnLimpiar.addActionListener(controller);
        this.btnReservar.addActionListener(controller);
        this.btnVolver.addActionListener(controller);
        if (this.btnReservarGrupal != null) {
            this.btnReservarGrupal.addActionListener(controller);
        }
    }
}