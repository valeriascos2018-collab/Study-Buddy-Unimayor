package co.edu.unimayor.studybuddy.gui;

import co.edu.unimayor.studybuddy.controller.RegistroController;
import co.edu.unimayor.studybuddy.model.Rol;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de Registro de Usuarios.
 * Captura datos completos del usuario con validación de correo institucional.
 */
public class FrmRegistro extends JFrame {
    
    // Componentes públicos para acceso del controller
    public JTextField txtNombre;
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JPasswordField txtConfirmarPassword;
    public JComboBox<String> cboFacultad;
    public JTextField txtPrograma;
    public JComboBox<Rol> cboRol;
    public JButton btnRegistrar;
    public JButton btnVolver;
    
    public FrmRegistro() {
        initComponents();
        initController();
    }
    
    /**
     * Inicializa todos los componentes Swing.
     */
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con scroll
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre Completo
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNombre = new JTextField(25);
        panelFormulario.add(txtNombre, gbc);
        
        // Correo
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Correo Institucional:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtCorreo = new JTextField(25);
        panelFormulario.add(txtCorreo, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtPassword = new JPasswordField(25);
        panelFormulario.add(txtPassword, gbc);
        
        // Confirmar Contraseña
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        txtConfirmarPassword = new JPasswordField(25);
        panelFormulario.add(txtConfirmarPassword, gbc);
        
        // Facultad
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Facultad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        String[] facultades = {"Ingeniería", "Ciencias de la Salud", "Ciencias Administrativas", "Educación", "Derecho"};
        cboFacultad = new JComboBox<>(facultades);
        panelFormulario.add(cboFacultad, gbc);
        
        // Programa Académico
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Programa Académico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        txtPrograma = new JTextField(25);
        panelFormulario.add(txtPrograma, gbc);
        
        // Rol
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        Rol[] roles = {Rol.ESTUDIANTE, Rol.TUTOR_MONITOR};
        cboRol = new JComboBox<>(roles);
        panelFormulario.add(cboRol, gbc);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        panelBotones.add(btnRegistrar);
        
        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(120, 35));
        panelBotones.add(btnVolver);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Inicializa el controller para manejar eventos.
     */
    private void initController() {
        new RegistroController(this);
    }
}