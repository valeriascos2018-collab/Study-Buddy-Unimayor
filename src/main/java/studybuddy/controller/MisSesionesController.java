package studybuddy.controller;

import studybuddy.gui.FrmMisSesiones;
import studybuddy.gui.FrmEditarSesion;
import studybuddy.logic.ISesionService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;
import studybuddy.gui.FrmMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para la gestión de sesiones del tutor.
 * Maneja HU-6 (Edición, cancelación y finalización de sesiones).
 */
public class MisSesionesController implements ActionListener {
    
    private final FrmMisSesiones vista;
    private final ISesionService service;
    private final Usuario tutor;
    
    public MisSesionesController(FrmMisSesiones vista, Usuario tutor, ISesionService service) {
        this.vista = vista;
        this.service = service;
        this.tutor = tutor;
        
        // Registrar listeners
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
        this.vista.btnCompletar.addActionListener(this); // <-- NUEVO: Registrar el botón completar
        
        // Cargar sesiones al iniciar
        cargarSesiones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnEditar) {
            editarSesion();
        } else if (e.getSource() == vista.btnCancelar) {
            cancelarSesion();
        } else if (e.getSource() == vista.btnCompletar) { // <-- NUEVO: Ruta para el botón completar
            marcarComoCompletada();
        } else if (e.getSource() == vista.btnVolver) {
            volverAlMenu();
        }
    }
    
    /**
     * Carga las sesiones del tutor en la tabla.
     */
    private void cargarSesiones() {
        List<Sesion> sesiones = service.obtenerSesionesPorTutor(tutor.getId());
        
        // Limpiar tabla
        DefaultTableModel modelo = vista.modeloTabla;
        modelo.setRowCount(0);
        
        // Agregar filas
        for (Sesion sesion : sesiones) {
            Object[] fila = {
                sesion.getId(),
                sesion.getAsignatura(),
                sesion.getTema(),
                sesion.getFecha().toString(),
                sesion.getHoraInicio().toString() + " - " + sesion.getHoraFin().toString(),
                sesion.getModalidad(),
                sesion.getEstado()
            };
            modelo.addRow(fila);
        }
    }
    
    /**
     * HU-6 CA1: Edición exitosa de sesión.
     */
    private void editarSesion() {
        int filaSeleccionada = vista.tblSesiones.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Debes seleccionar una sesión para editar", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la sesión", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (sesion.estaCompletada()) {
            JOptionPane.showMessageDialog(vista, 
                "No es posible modificar una sesión ya finalizada", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FrmEditarSesion frmEditar = new FrmEditarSesion(sesion);
        frmEditar.setController(new EditarSesionController(frmEditar, service, tutor));
        frmEditar.setVisible(true);
        
        // Nota: La recarga de la tabla debería hacerse cuando se cierre el FrmEditarSesion, 
        // no inmediatamente aquí, para que refleje los cambios reales.
    }
    
    /**
     * HU-6 CA2: Cancelación exitosa de sesión.
     */
    private void cancelarSesion() {
        int filaSeleccionada = vista.tblSesiones.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Debes seleccionar una sesión para cancelar", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la sesión", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (sesion.estaCompletada()) {
            JOptionPane.showMessageDialog(vista, 
                "No es posible cancelar una sesión ya finalizada", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Estás seguro de cancelar la sesión de " + sesion.getAsignatura() + " del " + sesion.getFecha() + "?", 
            "Confirmar cancelación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String error = service.cancelarSesion(sesionId);
            
            if (error == null) {
                JOptionPane.showMessageDialog(vista, "Sesión cancelada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarSesiones(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * NUEVO: HU-6 - Marcar sesión como completada manualmente.
     */
    private void marcarComoCompletada() {
        int filaSeleccionada = vista.tblSesiones.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Debes seleccionar una sesión para marcar como completada", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la sesión", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validación de defensa: por si acaso ya estaba completada
        if (sesion.estaCompletada()) {
            JOptionPane.showMessageDialog(vista, 
                "Esta sesión ya está marcada como completada", 
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Estás seguro de marcar la sesión de '" + sesion.getAsignatura() + "' como COMPLETADA?\n" +
            "Esta acción indicará que la tutoría ya se llevó a cabo.", 
            "Confirmar completado", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamamos al servicio para ejecutar la regla de negocio
            String error = service.marcarComoCompletada(sesionId, tutor.getId());
            
            if (error == null) {
                JOptionPane.showMessageDialog(vista, 
                    "Sesión marcada como completada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarSesiones(); // Recargar la tabla para reflejar el nuevo estado
            } else {
                JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Navega de vuelta al menú principal.
     */
    private void volverAlMenu() {
        vista.dispose();
        new FrmMenuPrincipal(tutor).setVisible(true);
    }
}