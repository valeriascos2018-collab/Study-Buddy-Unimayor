package studybuddy.controller;

import studybuddy.gui.FrmMisSesiones;
import studybuddy.gui.FrmEditarSesion;
import studybuddy.logic.SesionService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para la gestión de sesiones del tutor.
 * Maneja HU-6 (Edición y cancelación de sesiones).
 */
public class MisSesionesController implements ActionListener {
    
    private final FrmMisSesiones vista;
    private final SesionService service;
    private final Usuario tutor;
    
    public MisSesionesController(FrmMisSesiones vista, Usuario tutor) {
        this.vista = vista;
        this.service = new SesionService();
        this.tutor = tutor;
        
        // Registrar listeners
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
        
        // Cargar sesiones al iniciar
        cargarSesiones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnEditar) {
            editarSesion();
        } else if (e.getSource() == vista.btnCancelar) {
            cancelarSesion();
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
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID de la sesión seleccionada
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            JOptionPane.showMessageDialog(vista, 
                "No se pudo cargar la sesión", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // HU-6 CA3: Validar que no sea sesión finalizada
        if ("COMPLETADA".equals(sesion.getEstado())) {
            JOptionPane.showMessageDialog(vista, 
                "No es posible modificar una sesión ya finalizada", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Abrir formulario de edición (debes crear FrmEditarSesion)
        FrmEditarSesion frmEditar = new FrmEditarSesion(sesion);
        frmEditar.setController(new EditarSesionController(frmEditar, service));
        frmEditar.setVisible(true);
        
        // Recargar tabla después de editar
        cargarSesiones();
    }
    
    /**
     * HU-6 CA2: Cancelación exitosa de sesión.
     */
    private void cancelarSesion() {
        int filaSeleccionada = vista.tblSesiones.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, 
                "Debes seleccionar una sesión para cancelar", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID de la sesión seleccionada
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            JOptionPane.showMessageDialog(vista, 
                "No se pudo cargar la sesión", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // HU-6 CA3: Validar que no sea sesión finalizada
        if ("COMPLETADA".equals(sesion.getEstado())) {
            JOptionPane.showMessageDialog(vista, 
                "No es posible modificar una sesión ya finalizada", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmar cancelación
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Estás seguro de cancelar la sesión de " + sesion.getAsignatura() + " del " + sesion.getFecha() + "?", 
            "Confirmar cancelación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String error = service.cancelarSesion(sesionId);
            
            if (error == null) {
                JOptionPane.showMessageDialog(vista, 
                    "Sesión cancelada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarSesiones(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(vista, 
                    error, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Navega de vuelta al menú principal.
     */
    private void volverAlMenu() {
        vista.dispose();
        // new FrmMenuTutor(tutor).setVisible(true);
    }
}