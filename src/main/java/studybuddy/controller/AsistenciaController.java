/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

/**
 *
 * @author yoban
 */
import studybuddy.gui.FrmRegistrarAsistencia;
import studybuddy.logic.TutoriasFacade;
import studybuddy.model.Reserva;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AsistenciaController implements ActionListener {

    private final FrmRegistrarAsistencia vista;
    private final TutoriasFacade facade;
    private final Usuario tutor;
    private final Sesion sesion;

    public AsistenciaController(FrmRegistrarAsistencia vista, TutoriasFacade facade,
                                Usuario tutor, Sesion sesion) {
        this.vista = vista; this.facade = facade; this.tutor = tutor; this.sesion = sesion;
        vista.btnFinalizar.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.btnVolver.addActionListener(this);
        cargarAsistentes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnFinalizar) finalizarSesion();
        else if (e.getSource() == vista.btnGuardar) guardarAsistencia();
        else if (e.getSource() == vista.btnVolver) vista.dispose();
    }

    private void cargarAsistentes() {
        vista.modeloTabla.setRowCount(0);
        for (Reserva r : facade.obtenerAsistentes(sesion.getId())) {
            boolean procesada = "ASISTIO".equalsIgnoreCase(r.getEstado())
                             || "NO_ASISTIO".equalsIgnoreCase(r.getEstado());
            vista.modeloTabla.addRow(new Object[]{
                r.getId(), r.getEstudianteNombre(), r.getEstado(),
                procesada ? ("ASISTIO".equalsIgnoreCase(r.getEstado()) ? "Sí" : "No") : "Sí"
            });
        }
    }

    /** HU-10 CA1, paso 1: el tutor marca la sesión como COMPLETADA. */
    private void finalizarSesion() {
        String error = facade.completarTutoria(sesion.getId(), tutor.getId());
        if (error == null) {
            sesion.setEstado("COMPLETADA");
            JOptionPane.showMessageDialog(vista,
                "Sesión finalizada. Ahora puede registrar la asistencia.");
        } else {
            JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** HU-10 CA1, paso 2 + CA2: guardar asistencia. */
    private void guardarAsistencia() {
        // CA2: intento de registrar antes de finalizar
        if (!"COMPLETADA".equalsIgnoreCase(sesion.getEstado())) {
            JOptionPane.showMessageDialog(vista,
                "No es posible registrar asistencia antes de finalizar la sesión",
                "Asistencia no registrada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            for (int i = 0; i < vista.modeloTabla.getRowCount(); i++) {
                int idReserva = (int) vista.modeloTabla.getValueAt(i, 0);
                String estado = (String) vista.modeloTabla.getValueAt(i, 2);
                if ("ASISTIO".equalsIgnoreCase(estado) || "NO_ASISTIO".equalsIgnoreCase(estado))
                    continue; // ya procesada (idempotencia)
                boolean asistio = "Sí".equalsIgnoreCase((String) vista.modeloTabla.getValueAt(i, 3));
                facade.registrarAsistencia(idReserva, asistio); // -> reservas.estado = ASISTIO/NO_ASISTIO
            }
            JOptionPane.showMessageDialog(vista, "Asistencia registrada correctamente");
            cargarAsistentes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}