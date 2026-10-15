package studybuddy.controller;

import studybuddy.gui.FrmLogin;
import studybuddy.logic.IAutenticacionService;
import studybuddy.logic.IValidadorUsuario;
import studybuddy.model.Usuario;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    
    private final FrmLogin vista;
    private final IAutenticacionService servicio;
    private final IValidadorUsuario validador;
    
    // ✅ Constructor que coincide con tu ControllerFactory
    public LoginController(FrmLogin vista, IAutenticacionService servicio, IValidadorUsuario validador) {
        this.vista = vista;
        this.servicio = servicio;
        this.validador = validador;
        
        // ✅ 1. Escuchar el clic del botón de Login
        this.vista.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarLogin();
            }
        });
        
        // ✅ 2. Escuchar el clic del botón de Registro (para abrir esa ventana)
        this.vista.btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    }
    
    private void manejarLogin() {
    // 1. Obtener datos de la vista
    String correo = vista.txtCorreo.getText().trim();
    String password = new String(vista.txtPassword.getPassword()).trim();
    
    // 2. Validaciones básicas
    if (correo.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (!validador.validarDominioInstitucional(correo)) {
        JOptionPane.showMessageDialog(vista, "El correo debe ser institucional (@unimayor.edu.co).", "Error de dominio", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 3. Intentar autenticar
    Usuario usuarioAutenticado = servicio.autenticar(correo, password);
    
    if (usuarioAutenticado != null) {
        // ✅ ÉXITO: Mostrar mensaje
        JOptionPane.showMessageDialog(vista, 
            "¡Bienvenido, " + usuarioAutenticado.getNombreCompleto() , 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // 🚀 Pasar el usuario autenticado a FrmMenuPrincipal y mostrarlo
        studybuddy.gui.FrmMenuPrincipal menu = new studybuddy.gui.FrmMenuPrincipal(usuarioAutenticado);
        menu.setVisible(true);
        
        // Cerramos la ventana de login
        vista.dispose(); 
        
    } else {
        // ❌ FALLO: Credenciales incorrectas
        JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        vista.txtPassword.setText(""); 
        vista.txtCorreo.requestFocus(); 
        }
    }
    private void abrirRegistro() {
        // Ocultar login
        vista.setVisible(false);
        
        // Crear y mostrar ventana de registro usando el Factory
        studybuddy.gui.FrmRegistro vistaRegistro = new studybuddy.gui.FrmRegistro();
        ControllerFactory.crearRegistroController(vistaRegistro);
        vistaRegistro.setVisible(true);
        
        // Opcional: Cuando se cierre el registro, volver a mostrar el login
        vistaRegistro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                vista.setVisible(true);
            }
        });
    }
}