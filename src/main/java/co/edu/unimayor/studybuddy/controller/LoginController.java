package co.edu.unimayor.studybuddy.controller;

import co.edu.unimayor.studybuddy.gui.FrmLogin;
import co.edu.unimayor.studybuddy.gui.FrmRegistro;
import co.edu.unimayor.studybuddy.gui.FrmPerfil;
import co.edu.unimayor.studybuddy.logic.AutenticacionService;
import co.edu.unimayor.studybuddy.model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para la ventana de Login.
 * Maneja eventos de autenticación y navegación a otras vistas.
 */
public class LoginController implements ActionListener {
    
    private final FrmLogin vista;
    private final AutenticacionService servicio;
    
    public LoginController(FrmLogin vista) {
        this.vista = vista;
        this.servicio = new AutenticacionService();
        this.vista.btnLogin.addActionListener(this);
        this.vista.btnRegistro.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLogin) {
            autenticar();
        } else if (e.getSource() == vista.btnRegistro) {
            navegarARegistro();
        }
    }
    
    /**
     * Procesa la autenticación del usuario.
     */
    private void autenticar() {
        String correo = vista.txtCorreo.getText().trim();
        String contraseña = new String(vista.txtPassword.getPassword());
        
        // Validaciones básicas
        if (!servicio.validarCamposNoVacios(correo, contraseña)) {
            JOptionPane.showMessageDialog(vista, "Ingrese correo y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuario = servicio.autenticar(correo, contraseña);
        
        if (usuario != null) {
            JOptionPane.showMessageDialog(vista, "¡Bienvenido " + usuario.getNombreCompleto() + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            navegarSegunRol(usuario);
        } else {
            JOptionPane.showMessageDialog(vista, "Credenciales inválidas", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Redirige a la vista correspondiente según el rol del usuario.
     */
    private void navegarSegunRol(Usuario usuario) {
        vista.dispose();
        
        switch (usuario.getRol()) {
            case ESTUDIANTE:
            case TUTOR_MONITOR:
                new FrmPerfil(usuario).setVisible(true);
                break;
            case ADMINISTRADOR:
                // Aquí iría la vista de administrador (fuera del Sprint 1)
                JOptionPane.showMessageDialog(null, "Vista de Administrador en desarrollo");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido");
        }
    }
    
    /**
     * Navega a la ventana de registro.
     */
    private void navegarARegistro() {
        vista.dispose();
        new FrmRegistro().setVisible(true);
    }
}