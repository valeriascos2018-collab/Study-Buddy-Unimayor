package studybuddy.gui;

import studybuddy.controller.PerfilController;
import studybuddy.model.Usuario;
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
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Título
        JLabel lblTitulo = new JLabel("Mi Perfil Académico", SwingConstants.CENTER);
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
        
        // Correo (no editable)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtCorreo = new JTextField(25);
        panelFormulario.add(txtCorreo, gbc);
        
        // Facultad
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Facultad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtFacultad = new JTextField(25);
        panelFormulario.add(txtFacultad, gbc);
        
        // Programa Académico
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Programa Académico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        txtPrograma = new JTextField(25);
        panelFormulario.add(txtPrograma, gbc);
        
        // Rol (no editable)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panelFormulario.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        txtRol = new JTextField(25);
        panelFormulario.add(txtRol, gbc);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setPreferredSize(new Dimension(140, 35));
        panelBotones.add(btnGuardar);
        
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(120, 35));
        panelBotones.add(btnSalir);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Inicializa el controller para manejar eventos.
     */
    private void initController(Usuario usuario) {
        new PerfilController(this, usuario);
    }
}
