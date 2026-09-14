package co.edu.unimayor.studybuddy.gui;

import co.edu.unimayor.studybuddy.controller.LoginController;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de Inicio de Sesión (Login).
 * Diseñada con Swing puro, sin dependencias de NetBeans GUI Builder.
 */
public class FrmLogin extends JFrame {
    
    // Componentes públicos para acceso del controller
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JButton btnRegistro;
    
    public FrmLogin() {
        initComponents();
        initController();
    }
    
    /**
     * Inicializa todos los componentes Swing.
     */
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Título
        JLabel lblTitulo = new JLabel("Study Buddy Unimayor", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Correo
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Correo Institucional:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtCorreo = new JTextField(20);
        panelFormulario.add(txtCorreo, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        panelFormulario.add(txtPassword, gbc);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(120, 35));
        panelBotones.add(btnLogin);
        
        btnRegistro = new JButton("Registrarse");
        btnRegistro.setPreferredSize(new Dimension(120, 35));
        panelBotones.add(btnRegistro);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Inicializa el controller para manejar eventos.
     */
    private void initController() {
        new LoginController(this);
    }
}