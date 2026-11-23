
package studybuddy.gui;

import studybuddy.controller.PerfilController;
import studybuddy.model.Usuario;
import studybuddy.controller.ControllerFactory;
import javax.swing.text.AbstractDocument;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de Gestión de Perfil Académico.
 * Permite consultar y actualizar datos del usuario logueado.
 */
public class FrmPerfil extends JFrame {

    // Componentes públicos para acceso del controller
    public JTextField txtNombre;
    public JTextField txtCorreo;
    public JTextField txtFacultad;
    public JTextField txtPrograma;
    public JTextField txtRol;

    public JButton btnGuardar;
    public JButton btnSalir;

    // Colores institucionales
    private final Color AZUL_OSCURO = new Color(7, 52, 94);
    private final Color AZUL_BOTON = new Color(0, 105, 190);
    private final Color VERDE = new Color(34, 153, 87);
    private final Color FONDO = new Color(241, 244, 248);
    private final Color GRIS = new Color(232, 236, 240);
    private final Color TEXTO = new Color(30, 45, 60);

    public FrmPerfil(Usuario usuario) {
        initComponents();
        initController(usuario);
    }

    /**
     * Inicializa todos los componentes Swing.
     */
    private void initComponents() {

        setTitle("Study Buddy Unimayor - Mi Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(FONDO);
        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // ==========================
        // ENCABEZADO
        // ==========================

        JPanel panelEncabezado = new JPanel(new BorderLayout(15, 5));
        panelEncabezado.setBackground(AZUL_OSCURO);
        panelEncabezado.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(
            new BoxLayout(panelTitulo, BoxLayout.Y_AXIS)
        );

        JLabel lblTitulo = new JLabel("Mi Perfil Académico");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 27));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel(
            "Consulta y actualiza tu información personal y académica"
        );
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(215, 229, 244));

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createVerticalStrut(8));
        panelTitulo.add(lblSubtitulo);

        JLabel lblIcono = new JLabel("\uD83C\uDF93");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        lblIcono.setForeground(Color.WHITE);

        panelEncabezado.add(lblIcono, BorderLayout.WEST);
        panelEncabezado.add(panelTitulo, BorderLayout.CENTER);

        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        // ==========================
        // PANEL DEL FORMULARIO
        // ==========================

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(215, 221, 228)
                ),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
            )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Título de información personal
        JLabel lblPersonal = crearSubtitulo("Información personal");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelFormulario.add(lblPersonal, gbc);

        gbc.gridwidth = 1;

        // Nombre
        agregarCampo(
            panelFormulario, gbc,
            "Nombre Completo:",
            1,
            txtNombre = crearTextFieldSoloLetras(25)
        );

        // Correo
        txtCorreo = crearTextFieldSoloLectura(25);
        agregarCampo(
            panelFormulario, gbc,
            "Correo:",
            2,
            txtCorreo
        );

        // Separador
        JSeparator separador = new JSeparator();

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        panelFormulario.add(separador, gbc);

        // Información académica
        JLabel lblAcademica = crearSubtitulo(
            "Información académica"
        );

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelFormulario.add(lblAcademica, gbc);

        gbc.gridwidth = 1;

        // Facultad
        agregarCampo(
            panelFormulario, gbc,
            "Facultad:",
            5,
            txtFacultad = crearTextFieldSoloLetras(25)
        );

        // Programa académico
        agregarCampo(
            panelFormulario, gbc,
            "Programa Académico:",
            6,
            txtPrograma = crearTextFieldSoloLetras(25)
        );

        // Rol
        txtRol = crearTextFieldSoloLectura(25);
        agregarCampo(
            panelFormulario, gbc,
            "Rol:",
            7,
            txtRol
        );

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // ==========================
        // PANEL DE BOTONES
        // ==========================

        JPanel panelBotones = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 15, 5)
        );
        panelBotones.setBackground(FONDO);

        // Botón salir
        btnSalir = new JButton("←  Salir");
        estilizarBoton(
            btnSalir,
            GRIS,
            TEXTO
        );

        // Botón guardar
        btnGuardar = new JButton("✓  Guardar Cambios");
        estilizarBoton(
            btnGuardar,
            VERDE,
            Color.WHITE
        );

        panelBotones.add(btnSalir);
        panelBotones.add(btnGuardar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Agrega una etiqueta y su campo de texto al formulario.
     */
    private void agregarCampo(
        JPanel panel,
        GridBagConstraints gbc,
        String texto,
        int fila,
        JTextField campo
    ) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(AZUL_OSCURO);

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.30;
        gbc.insets = new Insets(9, 10, 9, 10);

        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        campo.setPreferredSize(new Dimension(350, 36));

        panel.add(campo, gbc);
    }

    /**
     * Crea los subtítulos del formulario.
     */
    private JLabel crearSubtitulo(String texto) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 17));
        etiqueta.setForeground(AZUL_OSCURO);

        return etiqueta;
    }

    /**
     * Crea campos editables que solo aceptan letras y espacios.
     */
    private JTextField crearTextFieldSoloLetras(int columns) {

        JTextField textField = new JTextField(columns);

        ((AbstractDocument) textField.getDocument())
            .setDocumentFilter(new SoloLetrasFilter());

        estilizarCampo(textField, true);

        return textField;
    }

    /**
     * Crea campos de solo lectura.
     */
    private JTextField crearTextFieldSoloLectura(int columns) {

        JTextField textField = new JTextField(columns);

        textField.setEditable(false);

        estilizarCampo(textField, false);

        return textField;
    }

    /**
     * Estilo visual de los campos de texto.
     */
    private void estilizarCampo(
        JTextField campo,
        boolean editable
    ) {

        campo.setFont(new Font("Arial", Font.PLAIN, 15));
        campo.setForeground(TEXTO);

        campo.setBackground(
            editable ? Color.WHITE : new Color(237, 241, 245)
        );

        campo.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(180, 195, 208)
                ),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
            )
        );
    }

    /**
     * Estilo de los botones.
     */
    private void estilizarBoton(
        JButton boton,
        Color fondo,
        Color texto
    ) {

        boton.setPreferredSize(new Dimension(190, 43));
        boton.setBackground(fondo);
        boton.setForeground(texto);

        boton.setFont(new Font("Arial", Font.BOLD, 14));

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
    }

    /**
     * Inicializa el controller para manejar eventos.
     */
    private void initController(Usuario usuario) {

        ControllerFactory.crearPerfilController(this, usuario);
    }
}
