package studybuddy.controller;

import studybuddy.gui.FrmPublicarSesion;
import studybuddy.logic.TutoriasFacade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class PublicarSesionController implements ActionListener {

    private final FrmPublicarSesion vista;
    private final TutoriasFacade tutoriaFacade;
    private final int tutorId;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public PublicarSesionController(
            FrmPublicarSesion vista,
            int tutorId,
            TutoriasFacade tutoriaFacade) {

        this.vista = vista;
        this.tutorId = tutorId;
        this.tutoriaFacade = tutoriaFacade;

        this.vista.btnPublicar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
    }

    // ==========================================
    // EVENTOS
    // ==========================================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnPublicar) {
            publicarSesion();

        } else if (e.getSource() == vista.btnCancelar) {
            volver();
        }
    }

    // ==========================================
    // HU-4: PUBLICAR SESION
    // ==========================================

    private void publicarSesion() {

        try {

            // 1. Obtener datos de la vista

            String asignatura =
                    (String) vista.cboAsignatura.getSelectedItem();

            String tema =
                    vista.txtTema.getText().trim();

            String cupoMaximoStr =
                    vista.txtCupoMaximo.getText().trim();

            String tipoSesion =
                    (String) vista.cboTipoSesion.getSelectedItem();

            String modalidad =
                    (String) vista.cboModalidad.getSelectedItem();

            // 2. Validar cupo máximo

            if (cupoMaximoStr.isEmpty()) {

                mostrarError("El cupo máximo es obligatorio");
                return;
            }

            int cupoMaximo = Integer.parseInt(cupoMaximoStr);

            if (cupoMaximo <= 0) {

                mostrarError("El cupo máximo debe ser mayor a cero");
                return;
            }

            // 3. Validar campos obligatorios

            if (asignatura == null || tema.isEmpty()
                    || tipoSesion == null || modalidad == null) {

                mostrarError(
                        "Debes completar todos los campos obligatorios"
                );

                return;
            }

            // 4. Obtener fecha

            Object valFecha = vista.spnFecha.getValue();

            if (!(valFecha instanceof Date)) {

                mostrarError("Selecciona una fecha válida");
                return;
            }

            LocalDate fecha = ((Date) valFecha)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // 5. Obtener hora de inicio

            Object valInicio = vista.spnHoraInicio.getValue();

            if (!(valInicio instanceof Date)) {

                mostrarError("Selecciona una hora de inicio válida");
                return;
            }

            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime((Date) valInicio);

            LocalTime horaInicio = LocalTime.of(
                    calInicio.get(Calendar.HOUR_OF_DAY),
                    calInicio.get(Calendar.MINUTE),
                    calInicio.get(Calendar.SECOND)
            );

            // 6. Obtener hora de finalización

            Object valFin = vista.spnHoraFin.getValue();

            if (!(valFin instanceof Date)) {

                mostrarError("Selecciona una hora de fin válida");
                return;
            }

            Calendar calFin = Calendar.getInstance();
            calFin.setTime((Date) valFin);

            LocalTime horaFin = LocalTime.of(
                    calFin.get(Calendar.HOUR_OF_DAY),
                    calFin.get(Calendar.MINUTE),
                    calFin.get(Calendar.SECOND)
            );

            // 7. Obtener sede

            Object sedeObj = vista.cboSede.getSelectedItem();

            String sede = (sedeObj != null)
                    ? sedeObj.toString()
                    : "";

            if (sede.trim().isEmpty()) {

                mostrarError("Debes seleccionar una sede");
                return;
            }

            // 8. Validar horario

            if (!horaFin.isAfter(horaInicio)) {

                mostrarError(
                        "La hora de fin debe ser posterior a la hora de inicio"
                );

                return;
            }

            // ==========================================
            // 9. UTILIZAR FACADE
            // ==========================================

            String error = tutoriaFacade.publicarTutoria(
                    asignatura,
                    tema,
                    cupoMaximo,
                    tipoSesion,
                    modalidad,
                    fecha,
                    horaInicio,
                    horaFin,
                    sede,
                    tutorId
            );

            // 10. Mostrar resultado

            if (error == null) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Sesión publicada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                volver();

            } else {

                mostrarError(error);
            }

        } catch (NumberFormatException ex) {

            mostrarError(
                    "El cupo máximo debe ser un número válido"
            );

        } catch (Exception ex) {

            String mensaje = (ex.getMessage() != null)
                    ? ex.getMessage()
                    : ex.toString();

            mostrarError("Error del sistema: " + mensaje);

            ex.printStackTrace();
        }
    }

    // ==========================================
    // MOSTRAR ERROR
    // ==========================================

    private void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                vista,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ==========================================
    // VOLVER
    // ==========================================

    private void volver() {

        vista.dispose();

        // Aquí puedes abrir el menú principal del tutor.
    }
}
