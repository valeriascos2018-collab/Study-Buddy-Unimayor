package studybuddy.controller;

import studybuddy.gui.FrmRegistro;
import studybuddy.gui.FrmLogin;
import studybuddy.logic.AutenticacionService;
import studybuddy.model.Rol;
import studybuddy.model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para la ventana de Registro.
 * Maneja el registro de nuevos usuarios con validaciones de negocio.
 */
public class RegistroController implements ActionListener {
    
    private final FrmRegistro vista;
    private final AutenticacionService servicio;
    
    public RegistroController(FrmRegistro vista) {
        this.vista = vista;
        this.servicio = new AutenticacionService();
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrar) {
            registrarUsuario();
        } else if (e.getSource() == vista.btnVolver) {
            volverALogin();
        }
    }
    
    /**
     * Procesa el registro de un nuevo usuario.
     */
    private void registrarUsuario() {
        // Recopilar datos de la vista
        String nombre = vista.txtNombre.getText().trim();
        String correo = vista.txtCorreo.getText().trim();
        String contraseña = new String(vista.txtPassword.getPassword());
        String confirmarPassword = new String(vista.txtConfirmarPassword.getPassword());
        String facultad = (String) vista.cboFacultad.getSelectedItem();
        String programa = vista.txtPrograma.getText().trim();
        Rol rol = (Rol) vista.cboRol.getSelectedItem();
        
        // Validar contraseñas coincidentes
        if (!contraseña.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear objeto usuario
        Usuario usuario = new Usuario(nombre, correo, contraseña, facultad, programa, rol);
        
        try {
            String error = servicio.registrarUsuario(usuario);
            if (error == null) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                volverALogin();
            } else {
                JOptionPane.showMessageDialog(vista, error, "Error de registro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error del sistema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Navega de vuelta a la ventana de login.
     */
    private void volverALogin() {
        vista.dispose();
        new FrmLogin().setVisible(true);
    }
}