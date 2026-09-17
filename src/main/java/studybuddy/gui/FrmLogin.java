package studybuddy.gui;

import studybuddy.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ventana de Inicio de Sesión (Login).
 * Diseñada con Swing puro, con estética moderna y sin dependencias de NetBeans GUI Builder.
 */
public class FrmLogin extends JFrame {
    
    // Componentes públicos para acceso del controller (INTACTOS)
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JButton btnRegistro;
    
    // Paleta de colores moderna
    private final Color COLOR_FONDO = new Color(245, 247, 250);
    private final Color COLOR_PRIMARIO = new Color(0, 102, 204); // Azul institucional/moderno
    private final Color COLOR_PRIMARIO_HOVER = new Color(0, 82, 164);
    private final Color COLOR_SECUNDARIO = new Color(236, 240, 241);
    private final Color COLOR_TEXTO = new Color(51, 51, 51);
    private final Color COLOR_BORDE = new Color(200, 210, 220);

    public FrmLogin() {
        initComponents();
        initController();
    }
    
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 420); // Un poco más grande para que "respire"
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con fondo suave
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Título
        JLabel lblTitulo = new JLabel("Study Buddy Unimayor", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de formulario (Tarjeta blanca)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Borde suave para la tarjeta del formulario
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Etiqueta Correo
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblCorreo = new JLabel("Correo Institucional:");
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCorreo.setForeground(COLOR_TEXTO);
        panelFormulario.add(lblCorreo, gbc);
        
        // Campo Correo
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        txtCorreo = new JTextField(20);
        estilizarCampoDeTexto(txtCorreo);
        panelFormulario.add(txtCorreo, gbc);
        
        // Etiqueta Contraseña
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(COLOR_TEXTO);
        panelFormulario.add(lblPassword, gbc);
        
        // Campo Contraseña
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        txtPassword = new JPasswordField(20);
        estilizarCampoDeTexto(txtPassword);
        panelFormulario.add(txtPassword, gbc);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        btnLogin = new JButton("Iniciar Sesión");
        estilizarBotonPrimario(btnLogin);
        panelBotones.add(btnLogin);
        
        btnRegistro = new JButton("Registrarse");
        estilizarBotonSecundario(btnRegistro);
        panelBotones.add(btnRegistro);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Aplica estilos modernos a los campos de texto.
     */
    private void estilizarCampoDeTexto(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setForeground(COLOR_TEXTO);
        campo.setPreferredSize(new Dimension(250, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        // Efecto al enfocar
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_PRIMARIO, 2, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
    }

    /**
     * Aplica estilos al botón principal (Iniciar Sesión).
     */
    private void estilizarBotonPrimario(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 42));
        
        // Efecto Hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO_HOVER);
            }
            public void mouseExited(MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO);
            }
        });
    }

    /**
     * Aplica estilos al botón secundario (Registrarse).
     */
    private void estilizarBotonSecundario(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(COLOR_SECUNDARIO);
        boton.setForeground(COLOR_PRIMARIO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 42));
        
        // Efecto Hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(new Color(220, 230, 231));
            }
            public void mouseExited(MouseEvent evt) {
                boton.setBackground(COLOR_SECUNDARIO);
            }
        });
    }
    
    private void initController() {
        new LoginController(this);
    }
}