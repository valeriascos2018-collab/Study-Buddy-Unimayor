package studybuddy.gui;

import studybuddy.model.Rol;
import studybuddy.controller.ControllerFactory;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Ventana de Registro de Usuarios.
 * Diseño estilizado con identidad Unimayor, icono de usuario y espaciado optimizado.
 */
public class FrmRegistro extends JFrame {

    // Componentes públicos para acceso del controller (no modificar nombres)
    public JTextField txtNombre;
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JPasswordField txtConfirmarPassword;
    public JComboBox<String> cboFacultad;
    public JTextField txtPrograma;
    public JComboBox<Rol> cboRol;
    public JButton btnRegistrar;
    public JButton btnVolver;

    // Paleta de colores institucionales Unimayor
    private static final Color AZUL_UNIMAYOR = new Color(0, 51, 102);   // #003366
    private static final Color AZUL_BOTON    = new Color(0, 86, 179);   // #0056b3
    private static final Color GRIS_FONDO    = new Color(245, 247, 250);
    private static final Color BORDE_TARJETA = new Color(220, 224, 230);
    private static final Color BORDE_CAMPO   = new Color(195, 202, 212);

    public FrmRegistro() {
        initComponents();
        initController();
    }

    /**
     * Inicializa y organiza todos los componentes Swing.
     */
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con márgenes amplios
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 18));
        panelPrincipal.setBackground(GRIS_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(22, 32, 24, 32));

        // --- 1. CABECERA: Ícono de Usuario + Título más grande ---
        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));
        panelCabecera.setOpaque(false);

        JLabel lblIconoUsuario = new JLabel(crearIconoUsuario(74));
        lblIconoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Más grande y en negrilla
        lblTitulo.setForeground(AZUL_UNIMAYOR);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCabecera.add(lblIconoUsuario);
        panelCabecera.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCabecera.add(lblTitulo);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        // --- 2. TARJETA DEL FORMULARIO ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_TARJETA, 1),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 8, 9, 8); // Espaciado vertical y horizontal equilibrado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font fuenteEtiquetas = new Font("Segoe UI", Font.BOLD, 14);
        Font fuenteCampos    = new Font("Segoe UI", Font.BOLD, 13);

        // Fila 0: Nombre Completo
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Nombre Completo:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.62;
        txtNombre = crearTextFieldSoloLetras(22, fuenteCampos);
        panelFormulario.add(txtNombre, gbc);

        // Fila 1: Correo Institucional
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Correo Institucional:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.62;
        txtCorreo = crearTextFieldEstilizado(22, fuenteCampos);
        panelFormulario.add(txtCorreo, gbc);

        // Fila 2: Contraseña
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Contraseña:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.62;
        txtPassword = crearPasswordFieldEstilizado(22, fuenteCampos);
        panelFormulario.add(txtPassword, gbc);

        // Fila 3: Confirmar Contraseña
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Confirmar Contraseña:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.62;
        txtConfirmarPassword = crearPasswordFieldEstilizado(22, fuenteCampos);
        panelFormulario.add(txtConfirmarPassword, gbc);

        // Fila 4: Facultad
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Facultad:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.62;
        String[] facultades = {
            "Ingeniería",
            "Ciencias de la Salud",
            "Ciencias Administrativas",
            "Educación",
            "Derecho"
        };
        cboFacultad = new JComboBox<>(facultades);
        estilizarComboBox(cboFacultad, fuenteCampos);
        panelFormulario.add(cboFacultad, gbc);

        // Fila 5: Programa Académico
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Programa Académico:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 0.62;
        txtPrograma = crearTextFieldSoloLetras(22, fuenteCampos);
        panelFormulario.add(txtPrograma, gbc);

        // Fila 6: Rol
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.38;
        panelFormulario.add(crearEtiquetaNegrilla("Rol:", fuenteEtiquetas), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 0.62;
        Rol[] roles = {Rol.ESTUDIANTE, Rol.TUTOR_MONITOR};
        cboRol = new JComboBox<>(roles);
        estilizarComboBox(cboRol, fuenteCampos);
        panelFormulario.add(cboRol, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // --- 3. PANEL DE BOTONES ---
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 18, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(4, 20, 0, 20));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRegistrar.setBackground(AZUL_BOTON);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setPreferredSize(new Dimension(140, 42));

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnVolver.setBackground(new Color(235, 240, 247));
        btnVolver.setForeground(AZUL_UNIMAYOR);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setPreferredSize(new Dimension(140, 42));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea una etiqueta en negrilla con el color institucional.
     */
    private JLabel crearEtiquetaNegrilla(String texto, Font fuente) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        lbl.setForeground(AZUL_UNIMAYOR);
        return lbl;
    }

    /**
     * Crea un JTextField estilizado con altura cómoda y margen interno.
     */
    private JTextField crearTextFieldEstilizado(int columns, Font fuente) {
        JTextField campo = new JTextField(columns);
        campo.setFont(fuente);
        campo.setForeground(new Color(30, 35, 45));
        campo.setPreferredSize(new Dimension(220, 36));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CAMPO, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }

    /**
     * Crea un JTextField que solo acepta letras y espacios.
     */
    private JTextField crearTextFieldSoloLetras(int columns, Font fuente) {
        JTextField textField = crearTextFieldEstilizado(columns, fuente);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new SoloLetrasFilter());
        return textField;
    }

    /**
     * Crea un JPasswordField estilizado con altura cómoda y margen interno.
     */
    private JPasswordField crearPasswordFieldEstilizado(int columns, Font fuente) {
        JPasswordField campo = new JPasswordField(columns);
        campo.setFont(fuente);
        campo.setForeground(new Color(30, 35, 45));
        campo.setPreferredSize(new Dimension(220, 36));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CAMPO, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }

    /**
     * Aplica fuente en negrilla y altura uniforme a los JComboBox.
     */
    private void estilizarComboBox(JComboBox<?> combo, Font fuente) {
        combo.setFont(fuente);
        combo.setForeground(new Color(30, 35, 45));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(220, 36));
    }

    /**
     * Dibuja un ícono vectorial de usuario (avatar circular azul Unimayor)
     * para evitar dependencias de archivos externos de imagen.
     */
    private Icon crearIconoUsuario(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Círculo exterior sutil
                g2.setColor(new Color(220, 232, 248));
                g2.fillOval(x, y, size, size);

                // Círculo principal azul Unimayor
                int padding = 4;
                g2.setColor(AZUL_BOTON);
                g2.fillOval(x + padding, y + padding, size - padding * 2, size - padding * 2);

                // Recorte circular para que los hombros queden perfectamente dentro del círculo
                g2.setClip(new Ellipse2D.Float(x + padding, y + padding, size - padding * 2, size - padding * 2));

                // Silueta blanca del usuario (cabeza + hombros)
                g2.setColor(Color.WHITE);
                int headSize = (int) (size * 0.32);
                int headX = x + (size - headSize) / 2;
                int headY = y + (int) (size * 0.22);
                g2.fillOval(headX, headY, headSize, headSize);

                int bodyWidth = (int) (size * 0.62);
                int bodyHeight = (int) (size * 0.48);
                int bodyX = x + (size - bodyWidth) / 2;
                int bodyY = y + (int) (size * 0.58);
                g2.fillOval(bodyX, bodyY, bodyWidth, bodyHeight);

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

    /**
     * Inicializa el controller para manejar eventos.
     */
    private void initController() {
        ControllerFactory.crearRegistroController(this);
    }
}