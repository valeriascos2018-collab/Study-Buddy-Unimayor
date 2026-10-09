package studybuddy.controller;

import studybuddy.gui.FrmLogin;
import studybuddy.gui.FrmRegistro;
import studybuddy.gui.FrmMenuPrincipal;
import studybuddy.logic.IAutenticacionService; // ✅ 1. Importar la INTERFAZ, no la clase concreta
import studybuddy.model.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para la ventana de Login.
 * Maneja eventos de autenticación y navegación a otras vistas.
 */
public class LoginController implements ActionListener {

    private final FrmLogin vista;
    private final IAutenticacionService servicio; // ✅ 2. Depende de la abstracción

    // ✅ 3. El constructor AHORA RECIBE el servicio como parámetro
    public LoginController(FrmLogin vista, IAutenticacionService servicio) {
        this.vista = vista;
        this.servicio = servicio; // Se asigna la dependencia inyectada (ya no se usa "new")
        
        // Registro de listeners
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
        // Se usa txtCorreo (coherente con tu FrmLogin)
        String correo = vista.txtCorreo.getText().trim();
        String contraseña = new String(vista.txtPassword.getPassword());

        // Validaciones básicas delegadas al servicio
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
            case ADMINISTRADOR:
                new FrmMenuPrincipal(usuario).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido", "Error", JOptionPane.ERROR_MESSAGE);
                break;
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