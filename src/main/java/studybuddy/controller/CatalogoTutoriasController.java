package studybuddy.controller;

import studybuddy.gui.FrmCatalogoTutorias;
import studybuddy.logic.ISesionService;
import studybuddy.logic.IReservaService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;
import studybuddy.gui.FrmMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para el catálogo de tutorías.
 * Maneja HU-7 (Catálogo con filtros) y HU-8 (Reserva de cupos).
 */
public class CatalogoTutoriasController implements ActionListener {
    
    private final FrmCatalogoTutorias vista;
    private final ISesionService sesionService;
    private final IReservaService reservaService;
    private final Usuario estudiante;
    
    public CatalogoTutoriasController(FrmCatalogoTutorias vista, Usuario estudiante,
                                      ISesionService sesionService, IReservaService reservaService) {
        this.vista = vista;
        this.sesionService = sesionService;
        this.reservaService = reservaService;
        this.estudiante = estudiante;
        
        // Registrar listeners
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnReservar.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
        
        // Cargar todas las sesiones disponibles al iniciar
        cargarSesionesDisponibles();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscar) {
            buscarSesiones();
        } else if (e.getSource() == vista.btnLimpiar) {
            limpiarFiltros();
        } else if (e.getSource() == vista.btnReservar) {
            reservarCupo();
        } else if (e.getSource() == vista.btnVolver) {
            volverAlMenu();
        }
    }
    
    /**
     * HU-7 CA1: Consulta con resultados usando filtros.
     */
    private void buscarSesiones() {
        String asignatura = (String) vista.cboFiltroAsignatura.getSelectedItem();
        String sede = (String) vista.cboFiltroSede.getSelectedItem();
        String modalidad = (String) vista.cboFiltroModalidad.getSelectedItem();
        
        // Si seleccionó "Todas", pasar null para no filtrar
        String asignaturaFiltro = (asignatura == null || "Todas".equalsIgnoreCase(asignatura.trim())) ? null : asignatura.trim();
        String sedeFiltro = (sede == null || "Todas".equalsIgnoreCase(sede.trim())) ? null : sede.trim();
        String modalidadFiltro = (modalidad == null || "Todas".equalsIgnoreCase(modalidad.trim())) ? null : modalidad.trim();
        List<Sesion> sesiones = sesionService.buscarSesiones(asignaturaFiltro, sedeFiltro, modalidadFiltro);
        
        DefaultTableModel modelo = vista.modeloTabla;
        modelo.setRowCount(0);
        
        if (sesiones.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron tutorías disponibles con los criterios seleccionados", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (Sesion sesion : sesiones) {
            Object[] fila = {
                sesion.getId(), // AGREGADO: ID en la posición 0
                sesion.getAsignatura(),
                sesion.getTema(),
                sesion.getTutorNombre(),
                sesion.getFecha().toString(),
                sesion.getHoraInicio().toString() + " - " + sesion.getHoraFin().toString(),
                sesion.getModalidad(),
                sesion.getSede(),
                sesion.getCuposDisponibles()
            };
            modelo.addRow(fila);
        }
    }
    
    /**
     * HU-8: Reserva de cupo en una sesión.
     */
    private void reservarCupo() {
        int filaSeleccionada = vista.tblResultados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Debes seleccionar una sesión para reservar", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // CORREGIDO: Obtener el ID de la sesión desde la columna 0
        int sesionId = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        String asignatura = (String) vista.modeloTabla.getValueAt(filaSeleccionada, 1);
        int cuposDisponibles = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 8);
        
        if (cuposDisponibles <= 0) {
            JOptionPane.showMessageDialog(vista, "No hay cupos disponibles para esta sesión", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Deseas reservar un cupo en la sesión de " + asignatura + "?", 
            "Confirmar reserva", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // CORREGIDO: Se pasa el estudianteId y el sesionId (int, int)
            String error = reservaService.reservarCupo(estudiante.getId(), sesionId);
            
            if (error == null) {
                JOptionPane.showMessageDialog(vista, "Reserva exitosa. ¡Nos vemos en la sesión!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                buscarSesiones(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(vista, error, "Error de reserva", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    /**
     * Limpia los filtros y recarga todas las sesiones.
     */
    private void limpiarFiltros() {
        vista.cboFiltroAsignatura.setSelectedIndex(0);
        vista.cboFiltroSede.setSelectedIndex(0);
        vista.cboFiltroModalidad.setSelectedIndex(0);
        cargarSesionesDisponibles();
    }
    
    /**
     * Carga todas las sesiones disponibles al iniciar.
     */
    private void cargarSesionesDisponibles() {
        try {
            List<Sesion> sesiones = sesionService.obtenerTodasLasSesionesDisponibles();
            DefaultTableModel modelo = vista.modeloTabla;
            modelo.setRowCount(0);
            
            for (Sesion sesion : sesiones) {
                Object[] fila = {
                    sesion.getId(), // AGREGADO: ID en la posición 0
                    sesion.getAsignatura(),
                    sesion.getTema(),
                    sesion.getTutorNombre(),
                    (sesion.getFecha() != null) ? sesion.getFecha().toString() : "",
                    (sesion.getHoraInicio() != null && sesion.getHoraFin() != null) 
                        ? sesion.getHoraInicio().toString() + " - " + sesion.getHoraFin().toString() : "",
                    sesion.getModalidad(),
                    sesion.getSede(),
                    sesion.getCuposDisponibles()
                };
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar sesiones en la tabla:");
            ex.printStackTrace();
        }
    }
    
    /**
     * Navega de vuelta al menú principal del estudiante.
     */
    private void volverAlMenu() {
        vista.dispose();
        new FrmMenuPrincipal(estudiante).setVisible(true);
        // new FrmMenuEstudiante(estudiante).setVisible(true);
    }
}