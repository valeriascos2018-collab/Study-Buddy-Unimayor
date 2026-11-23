package studybuddy.gui;

import studybuddy.controller.PublicarSesionController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

public class FrmPublicarSesion extends JFrame {

    // Componentes públicos (mismos nombres para compatibilidad total con PublicarSesionController)
    public JComboBox<String> cboAsignatura;
    public JTextField txtTema;
    public JTextField txtCupoMaximo;
    public JComboBox<String> cboTipoSesion;
    public JComboBox<String> cboModalidad;
    public JSpinner spnFecha;
    public JSpinner spnHoraInicio;
    public JSpinner spnHoraFin;
    public JComboBox<String> cboSede;
    public JButton btnPublicar;
    public JButton btnCancelar;

    // Paleta de colores institucionales Unimayor (consistente con FrmCatalogoTutorias)
    private static final Color AZUL_OSCURO = new Color(0, 51, 102);      // #003366
    private static final Color AZUL_PRIMARIO = new Color(0, 102, 204);   // #0066CC
    private static final Color FONDO_GENERAL = new Color(242, 245, 249); // Gris azulado suave
    private static final Color BORDE_TARJETA = new Color(218, 224, 232);
    private static final Color BORDE_CAMPO = new Color(198, 206, 218);
    private static final Color FONDO_CAMPO = new Color(250, 251, 253);
    private static final Color TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private static final Color TEXTO_SECUNDARIO = new Color(108, 117, 125);
    private static final Color VERDE_ACCION = new Color(34, 153, 84);
    private static final Color GRIS_NEUTRO = new Color(233, 236, 239);

    public FrmPublicarSesion() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Study Buddy Unimayor - Publicar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 760);
        setMinimumSize(new Dimension(700, 720));
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 14));
        panelPrincipal.setBackground(FONDO_GENERAL);
        panelPrincipal.setBorder(new EmptyBorder(16, 24, 16, 24));

        // =====================================================================
        // 1. HEADER INSTITUCIONAL
        // =====================================================================
        JPanel panelHeader = new JPanel(new BorderLayout(15, 0));
        panelHeader.setBackground(AZUL_OSCURO);
        panelHeader.setBorder(new EmptyBorder(16, 22, 16, 22));

        JPanel panelTituloTexto = new JPanel();
        panelTituloTexto.setLayout(new BoxLayout(panelTituloTexto, BoxLayout.Y_AXIS));
        panelTituloTexto.setOpaque(false);

        JLabel lblTitulo = new JLabel("Publicar Nueva Sesión de Tutoría");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 21));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setIcon(crearIconoVectorial("birrete", 24, Color.WHITE));
        lblTitulo.setIconTextGap(12);

        JLabel lblSubtitulo = new JLabel("Completa los detalles académicos, horario y ubicación de la tutoría");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(205, 222, 242));
        lblSubtitulo.setBorder(new EmptyBorder(4, 36, 0, 0));

        panelTituloTexto.add(lblTitulo);
        panelTituloTexto.add(lblSubtitulo);
        panelHeader.add(panelTituloTexto, BorderLayout.WEST);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // =====================================================================
        // 2. TARJETA DEL FORMULARIO ORGANIZADO EN 2 COLUMNAS
        // =====================================================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(18, 24, 20, 24)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font sectionFont = new Font("Segoe UI", Font.BOLD, 13);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // --- SECCIÓN A: INFORMACIÓN ACADÉMICA ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panelFormulario.add(crearEncabezadoSeccion("1. Información de la Tutoría", sectionFont), gbc);

        // Fila 1: Asignatura (Izquierda) y Cupo Máximo (Derecha)
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.65;
        gbc.insets = new Insets(2, 0, 4, 14);
        panelFormulario.add(crearLabelCampo("Asignatura *", labelFont), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.35;
        gbc.insets = new Insets(2, 0, 4, 0);
        panelFormulario.add(crearLabelCampo("Cupo Máximo *", labelFont), gbc);

        cboAsignatura = new JComboBox<>(new String[]{
            "Cálculo I", "Cálculo II", "Programación I", "Física Mecánica", "Álgebra Lineal", "Bases de Datos"
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.65;
        gbc.insets = new Insets(0, 0, 12, 14);
        panelFormulario.add(estilizarCombo(cboAsignatura, fieldFont), gbc);

        txtCupoMaximo = new JTextField();
        txtCupoMaximo.setToolTipText("Ej. 10");
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.35;
        gbc.insets = new Insets(0, 0, 12, 0);
        panelFormulario.add(estilizarTextField(txtCupoMaximo, fieldFont), gbc);

        // Fila 2: Tema específico (Ancho completo)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(2, 0, 4, 0);
        panelFormulario.add(crearLabelCampo("Tema específico *", labelFont), gbc);

        txtTema = new JTextField();
        txtTema.setToolTipText("Ej. Derivadas implícitas, Consultas SQL JOIN, Arreglos en Java...");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 16, 0);
        panelFormulario.add(estilizarTextField(txtTema, fieldFont), gbc);

        // --- SECCIÓN B: MODALIDAD Y UBICACIÓN ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 10, 0);
        panelFormulario.add(crearEncabezadoSeccion("2. Modalidad y Lugar", sectionFont), gbc);

        // Fila 3: Tipo de Sesión (Izquierda) y Modalidad (Derecha)
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.5;
        gbc.insets = new Insets(2, 0, 4, 14);
        panelFormulario.add(crearLabelCampo("Tipo de Sesión", labelFont), gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 0.5;
        gbc.insets = new Insets(2, 0, 4, 0);
        panelFormulario.add(crearLabelCampo("Modalidad", labelFont), gbc);

        cboTipoSesion = new JComboBox<>(new String[]{"Individual", "Grupal"});
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 12, 14);
        panelFormulario.add(estilizarCombo(cboTipoSesion, fieldFont), gbc);

        cboModalidad = new JComboBox<>(new String[]{"Presencial", "Virtual"});
        gbc.gridx = 1; gbc.gridy = 7; gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 12, 0);
        panelFormulario.add(estilizarCombo(cboModalidad, fieldFont), gbc);

        // Fila 4: Sede / Enlace (Ancho completo)
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(2, 0, 4, 0);
        panelFormulario.add(crearLabelCampo("Sede / Enlace *", labelFont), gbc);

        cboSede = new JComboBox<>(new String[]{"Bicentenario", "Casa Obando", "Encarnación", "Virtual"});
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 16, 0);
        panelFormulario.add(estilizarCombo(cboSede, fieldFont), gbc);

        // --- SECCIÓN C: FECHA Y HORARIO ---
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 10, 0);
        panelFormulario.add(crearEncabezadoSeccion("3. Fecha y Horario", sectionFont), gbc);

        // Subpanel de 3 columnas iguales para Fecha, Hora Inicio y Hora Fin
        JPanel panelHorario = new JPanel(new GridBagLayout());
        panelHorario.setOpaque(false);
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.fill = GridBagConstraints.HORIZONTAL;
        gbcH.anchor = GridBagConstraints.WEST;
        gbcH.weightx = 1.0;

        gbcH.gridx = 0; gbcH.gridy = 0; gbcH.insets = new Insets(0, 0, 4, 14);
        panelHorario.add(crearLabelCampo("Fecha *", labelFont), gbcH);

        gbcH.gridx = 1; gbcH.insets = new Insets(0, 0, 4, 14);
        panelHorario.add(crearLabelCampo("Hora Inicio *", labelFont), gbcH);

        gbcH.gridx = 2; gbcH.insets = new Insets(0, 0, 4, 0);
        panelHorario.add(crearLabelCampo("Hora Fin *", labelFont), gbcH);

        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(dateEditor);
        gbcH.gridx = 0; gbcH.gridy = 1; gbcH.insets = new Insets(0, 0, 0, 14);
        panelHorario.add(estilizarSpinner(spnFecha, fieldFont), gbcH);

        spnHoraInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(spnHoraInicio, "HH:mm");
        spnHoraInicio.setEditor(timeEditorInicio);
        gbcH.gridx = 1; gbcH.insets = new Insets(0, 0, 0, 14);
        panelHorario.add(estilizarSpinner(spnHoraInicio, fieldFont), gbcH);

        spnHoraFin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorFin = new JSpinner.DateEditor(spnHoraFin, "HH:mm");
        spnHoraFin.setEditor(timeEditorFin);
        gbcH.gridx = 2; gbcH.insets = new Insets(0, 0, 0, 0);
        panelHorario.add(estilizarSpinner(spnHoraFin, fieldFont), gbcH);

        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 4, 0);
        panelFormulario.add(panelHorario, gbc);

        JScrollPane scrollFormulario = new JScrollPane(panelFormulario);
        scrollFormulario.setBorder(null);
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(14);
        panelPrincipal.add(scrollFormulario, BorderLayout.CENTER);

        // =====================================================================
        // 3. BARRA INFERIOR DE ACCIONES
        // =====================================================================
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(12, 18, 12, 18)
        ));

        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNota.setForeground(TEXTO_SECUNDARIO);
        panelBotones.add(lblNota, BorderLayout.WEST);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panelAcciones.setOpaque(false);

        btnCancelar = crearBoton("Cancelar", GRIS_NEUTRO, TEXTO_PRINCIPAL, crearIconoVectorial("cerrar", 13, TEXTO_PRINCIPAL));
        btnCancelar.setPreferredSize(new Dimension(135, 38));

        btnPublicar = crearBoton("Publicar Sesión", VERDE_ACCION, Color.WHITE, crearIconoVectorial("check", 14, Color.WHITE));
        btnPublicar.setPreferredSize(new Dimension(175, 38));

        panelAcciones.add(btnCancelar);
        panelAcciones.add(btnPublicar);
        panelBotones.add(panelAcciones, BorderLayout.EAST);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DE ESTILO
    // =========================================================================
    private JPanel crearEncabezadoSeccion(String titulo, Font font) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(font);
        lbl.setForeground(AZUL_PRIMARIO);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 235, 242));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sep, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel crearLabelCampo(String texto, Font font) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(font);
        lbl.setForeground(AZUL_OSCURO);
        return lbl;
    }

    private JTextField estilizarTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setBackground(FONDO_CAMPO);
        field.setForeground(TEXTO_PRINCIPAL);
        field.setPreferredSize(new Dimension(0, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_CAMPO, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    private JComboBox<String> estilizarCombo(JComboBox<String> combo, Font font) {
        combo.setFont(font);
        combo.setBackground(FONDO_CAMPO);
        combo.setForeground(TEXTO_PRINCIPAL);
        combo.setPreferredSize(new Dimension(0, 36));
        return combo;
    }

    private JSpinner estilizarSpinner(JSpinner spinner, Font font) {
        spinner.setFont(font);
        spinner.setPreferredSize(new Dimension(0, 38));
        spinner.setBorder(new LineBorder(BORDE_CAMPO, 1, true));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setFont(font);
            tf.setBackground(FONDO_CAMPO);
            tf.setForeground(TEXTO_PRINCIPAL);
            tf.setHorizontalAlignment(SwingConstants.CENTER);
            tf.setBorder(new EmptyBorder(5, 6, 5, 6));
        }
        return spinner;
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
                    case "check": {
                        g2.drawLine((int) (size * 0.15), (int) (size * 0.52), (int) (size * 0.42), (int) (size * 0.78));
                        g2.drawLine((int) (size * 0.42), (int) (size * 0.78), (int) (size * 0.85), (int) (size * 0.25));
                        break;
                    }
                    case "cerrar": {
                        g2.drawLine(3, 3, size - 3, size - 3);
                        g2.drawLine(size - 3, 3, 3, size - 3);
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

    public void setController(PublicarSesionController controller) {
        this.btnPublicar.addActionListener(controller);
        this.btnCancelar.addActionListener(controller);
    }
}
