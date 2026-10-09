package studybuddy.controller;

import studybuddy.gui.FrmPerfil;
import studybuddy.logic.PerfilService;
import studybuddy.model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para la ventana de Perfil.
 * Maneja consulta y actualización de datos del usuario logueado.
 */
public class PerfilController implements ActionListener {
    
    private final FrmPerfil vista;
    private final PerfilService servicio;
    private Usuario usuarioActual;
    
    public PerfilController(FrmPerfil vista, Usuario usuario, PerfilService servicio) {
        this.vista = vista;
        this.servicio = servicio;
        this.usuarioActual = usuario;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        
        cargarDatosUsuario();
    }
    
    /**
     * Carga los datos del usuario en los campos de la vista.
     */
    private void cargarDatosUsuario() {
        vista.txtNombre.setText(usuarioActual.getNombreCompleto());
        vista.txtCorreo.setText(usuarioActual.getCorreo());
        vista.txtFacultad.setText(usuarioActual.getFacultad());
        vista.txtPrograma.setText(usuarioActual.getProgramaAcademico());
        vista.txtRol.setText(usuarioActual.getRol().name());
        
        // Deshabilitar campos no editables
        vista.txtCorreo.setEnabled(false);
        vista.txtRol.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            actualizarPerfil();
        } else if (e.getSource() == vista.btnSalir) {
            salir();
        }
    }
    
    /**
     * Actualiza el perfil del usuario con los datos de la vista.
     */
    private void actualizarPerfil() {
        usuarioActual.setNombreCompleto(vista.txtNombre.getText().trim());
        usuarioActual.setFacultad(vista.txtFacultad.getText().trim());
        usuarioActual.setProgramaAcademico(vista.txtPrograma.getText().trim());
        
        String error = servicio.actualizarPerfil(usuarioActual);
        
        if (error == null) {
            JOptionPane.showMessageDialog(vista, "Perfil actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Cierra la ventana de perfil.
     */
    private void salir() {
        vista.dispose();
        new studybuddy.gui.FrmLogin().setVisible(true);
    }
}