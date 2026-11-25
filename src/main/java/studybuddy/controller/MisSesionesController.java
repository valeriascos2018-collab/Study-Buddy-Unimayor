package studybuddy.controller;


import studybuddy.factory.ServiceFactory;
import studybuddy.gui.FrmEditarSesion;
import studybuddy.gui.FrmMenuPrincipal;
import studybuddy.gui.FrmMisSesiones;
import studybuddy.gui.FrmRegistrarAsistencia;
import studybuddy.logic.ISesionService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Controlador para la gestión de sesiones del tutor.
 * Maneja HU-6 (Edición y cancelación de sesiones) y acceso a asistencia.
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
        this.vista.btnAsistencia.addActionListener(this);

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
        } else if (e.getSource() == vista.btnAsistencia) {
            abrirAsistencia();
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
        if (sesion.estaCompletada()) {
            JOptionPane.showMessageDialog(vista,
                    "No es posible modificar una sesión ya finalizada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Abrir formulario de edición
        FrmEditarSesion frmEditar = new FrmEditarSesion(sesion);
        frmEditar.setController(new EditarSesionController(frmEditar, service, tutor));
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
        if (sesion.estaCompletada()) {
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
                cargarSesiones();
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
        new FrmMenuPrincipal(tutor).setVisible(true);
    }

    private void abrirAsistencia() {
        int fila = vista.tblSesiones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debes seleccionar una sesión");
            return;
        }

        int sesionId = (int) vista.modeloTabla.getValueAt(fila, 0);
        Sesion sesion = service.obtenerSesionPorId(sesionId);
        if (sesion == null) {
            return;
        }

        if ("CANCELADA".equalsIgnoreCase(sesion.getEstado())) {
            JOptionPane.showMessageDialog(vista,
                    "No se puede registrar asistencia de una sesión cancelada");
            return;
        }

        FrmRegistrarAsistencia v = new FrmRegistrarAsistencia(
                sesion.getAsignatura() + " - " + sesion.getFecha());
        new AsistenciaController(v,
                ServiceFactory.crearTutoriasFacade(), tutor, sesion);
        v.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarSesiones();
            }
        });
        v.setVisible(true);
    }
}