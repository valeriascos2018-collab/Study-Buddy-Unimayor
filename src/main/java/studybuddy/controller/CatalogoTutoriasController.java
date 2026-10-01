package studybuddy.controller;

import studybuddy.gui.FrmCatalogoTutorias;
import studybuddy.logic.SesionService;
import studybuddy.logic.ReservaService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;

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
    private final SesionService sesionService;
    private final ReservaService reservaService;
    private final Usuario estudiante;
    
    public CatalogoTutoriasController(FrmCatalogoTutorias vista, Usuario estudiante) {
        this.vista = vista;
        this.sesionService = new SesionService();
        this.reservaService = new ReservaService();
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
        String asignaturaFiltro = "Todas".equals(asignatura) ? null : asignatura;
        String sedeFiltro = "Todas".equals(sede) ? null : sede;
        String modalidadFiltro = "Todas".equals(modalidad) ? null : modalidad;
        
        List<Sesion> sesiones = sesionService.buscarSesiones(asignaturaFiltro, sedeFiltro, modalidadFiltro);
        
        // Limpiar tabla
        DefaultTableModel modelo = vista.modeloTabla;
        modelo.setRowCount(0);
        
        // HU-7 CA2: Consulta sin resultados
        if (sesiones.isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "No se encontraron tutorías disponibles con los criterios seleccionados", 
                "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Agregar filas a la tabla
        for (Sesion sesion : sesiones) {
            Object[] fila = {
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
            JOptionPane.showMessageDialog(vista, 
                "Debes seleccionar una sesión para reservar", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos de la fila seleccionada
        String asignatura = (String) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        String fechaStr = (String) vista.modeloTabla.getValueAt(filaSeleccionada, 3);
        String horaStr = (String) vista.modeloTabla.getValueAt(filaSeleccionada, 4);
        int cuposDisponibles = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 7);
        
        // HU-8 CA2: Validar que haya cupos disponibles
        if (cuposDisponibles <= 0) {
            JOptionPane.showMessageDialog(vista, 
                "No hay cupos disponibles para esta sesión", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmar reserva
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Deseas reservar un cupo en la sesión de " + asignatura + " del " + fechaStr + " a las " + horaStr + "?", 
            "Confirmar reserva", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Aquí debes obtener el ID de la sesión (puedes agregarlo como columna oculta en la tabla)
            // Por ahora, simulamos la reserva
            String error = reservaService.reservarCupo(estudiante.getId(), asignatura, fechaStr);
            
            if (error == null) {
                JOptionPane.showMessageDialog(vista, 
                    "Reserva exitosa. ¡Nos vemos en la sesión!", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                buscarSesiones(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(vista, 
                    error, 
                    "Error de reserva", JOptionPane.ERROR_MESSAGE);
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
        List<Sesion> sesiones = sesionService.obtenerTodasLasSesionesDisponibles();
        
        DefaultTableModel modelo = vista.modeloTabla;
        modelo.setRowCount(0);
        
        for (Sesion sesion : sesiones) {
            Object[] fila = {
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
     * Navega de vuelta al menú principal del estudiante.
     */
    private void volverAlMenu() {
        vista.dispose();
        // new FrmMenuEstudiante(estudiante).setVisible(true);
    }
}