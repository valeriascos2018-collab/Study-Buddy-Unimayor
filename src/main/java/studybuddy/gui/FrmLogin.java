package studybuddy.gui;

import studybuddy.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import studybuddy.controller.ControllerFactory;

/**
 * Ventana de Inicio de Sesión (Login) adaptada a la identidad de Unimayor.
 */
public class FrmLogin extends JFrame {

    // Componentes públicos para compatibilidad total con LoginController y referencias directas
    public JTextField txtCorreo;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JButton btnRegistro;

    // Colores Institucionales Unimayor
    private static final Color AZUL_UNIMAYOR = new Color(0, 51, 102);  // #003366
    private static final Color AZUL_BOTON = new Color(0, 86, 179);    // #0056b3
    private static final Color GRIS_FONDO = new Color(245, 247, 250);

    public FrmLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Study Buddy Unimayor - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 540);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBackground(GRIS_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // --- CABECERA: Ícono Unimayor + Título ---
        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));
        panelCabecera.setOpaque(false);

        JLabel lblIcono = new JLabel();
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/imagenes/unimayor_logo.png"));
            Image imgEscalada = iconOriginal.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception e) {
            lblIcono.setText("🎓");
            lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        }

        JLabel lblTitulo = new JLabel("Study Buddy Unimayor");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(AZUL_UNIMAYOR);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCabecera.add(lblIcono);
        panelCabecera.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCabecera.add(lblTitulo);

        // --- FORMULARIO CENTRAL ---
        JPanel panelTarjeta = new JPanel(new GridBagLayout());
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuenteEtiquetas = new Font("Segoe UI", Font.BOLD, 14);

        // Etiqueta: Correo Institucional
        JLabel lblCorreo = new JLabel("Correo Institucional:");
        lblCorreo.setFont(fuenteEtiquetas);
        lblCorreo.setForeground(AZUL_UNIMAYOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        panelTarjeta.add(lblCorreo, gbc);

        // Campo: Correo Institucional
        txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCorreo.setPreferredSize(new Dimension(200, 34));
        txtCorreo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_BOTON, 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        panelTarjeta.add(txtCorreo, gbc);

        // Etiqueta: Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(fuenteEtiquetas);
        lblContrasena.setForeground(AZUL_UNIMAYOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        panelTarjeta.add(lblContrasena, gbc);

        // Campo: Contraseña
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(200, 34));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 210), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        panelTarjeta.add(txtPassword, gbc);

        // --- BOTONES ---
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBotones.setOpaque(false);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(AZUL_BOTON);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(0, 42));

        btnRegistro = new JButton("Registrarse");
        btnRegistro.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistro.setBackground(new Color(238, 242, 246));
        btnRegistro.setForeground(AZUL_BOTON);
        btnRegistro.setFocusPainted(false);
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistro.setPreferredSize(new Dimension(0, 42));

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelTarjeta, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void initController() {
        ControllerFactory.crearLoginController(this);
    }

    // Métodos Getters y Listeners para compatibilidad total
    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    public String getContrasena() {
        return new String(txtPassword.getPassword());
    }

    public void addIniciarSesionListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void addRegistrarseListener(ActionListener listener) {
        btnRegistro.addActionListener(listener);
    }
}
