/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

import studybuddy.dao.ReservaDAO;
import studybuddy.dao.SesionDAO;
import studybuddy.model.Sede;
import studybuddy.model.Sesion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SesionService implements ISesionService {

    private static final String MODALIDAD_VIRTUAL = "Virtual";

    private final SesionDAO sesionDAO;
    private final ReservaDAO reservaDAO;
    private final INotificadorSesiones notificador;
    private int ultimosNotificados;

    public SesionService(SesionDAO sesionDAO, ReservaDAO reservaDAO, INotificadorSesiones notificador) {
        this.sesionDAO = sesionDAO;
        this.reservaDAO = reservaDAO;
        this.notificador = notificador;
    }

    // HU-4: Publicación de sesión
    @Override
    public String publicarSesion(String asignatura, String tema, int cupoMaximo,
                                 String tipoSesion, String modalidad, LocalDate fecha,
                                 LocalTime horaInicio, LocalTime horaFin, String sede,
                                 int tutorId) {

        // Validaciones HU-4 CA2
        if (asignatura == null || asignatura.trim().isEmpty() ||
            tema == null || tema.trim().isEmpty() ||
            cupoMaximo <= 0) {
            return "Debe completar todos los campos obligatorios (asignatura, tema, cupo máximo)";
        }

        // Validación HU-4 CA3
        if (cupoMaximo <= 0) {
            return "El cupo máximo debe ser mayor a cero";
        }

        // Validación HU-5 CA2 - Horario coherente
        if (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio)) {
            return "El horario ingresado no es válido (la hora de fin debe ser posterior a la de inicio)";
        }

        // Validación HU-5 CA3 - Sede válida
        if (!validarSede(sede, modalidad)) {
            return "La sede seleccionada no es válida";
        }

        // Validación HU-9 - Cruce de horario
        if (sesionDAO.verificarCruceHorario(tutorId, fecha.toString(),
                                             horaInicio.toString(), horaFin.toString())) {
            return "Conflicto de horario: ya tienes una actividad programada en este horario";
        }

        // Crear sesión
        Sesion sesion = new Sesion(asignatura, tema, cupoMaximo, tipoSesion,
                                   modalidad, fecha, horaInicio, horaFin, sede, tutorId);

        if (sesionDAO.crearSesion(sesion)) {
            return null; // Éxito
        } else {
            return "Error al publicar la sesión";
        }
    }

    // HU-6: Edición de sesión
    @Override
    public String editarSesion(int sesionId, String asignatura, String tema,
                               int cupoMaximo, LocalDate fecha,
                               LocalTime horaInicio, LocalTime horaFin, String sede) {

        ultimosNotificados = 0;

        Sesion sesionExistente = sesionDAO.obtenerSesionPorId(sesionId);

        if (sesionExistente == null) {
            return "La sesión no existe";
        }

        // Validación HU-6 CA3 - No modificar sesión finalizada
        if (sesionExistente.estaCompletada()) {
            return "No es posible modificar una sesión ya finalizada";
        }

        // Copia del estado anterior (otra lectura a la BD, así no se ve afectada por los setters)
        Sesion sesionAntes = sesionDAO.obtenerSesionPorId(sesionId);

        // Actualizar datos
        sesionExistente.setAsignatura(asignatura);
        sesionExistente.setTema(tema);
        sesionExistente.setCupoMaximo(cupoMaximo);
        sesionExistente.setFecha(fecha);
        sesionExistente.setHoraInicio(horaInicio);
        sesionExistente.setHoraFin(horaFin);
        sesionExistente.setSede(sede);

        if (sesionDAO.actualizarSesion(sesionExistente)) {
            ultimosNotificados = notificador.notificarEdicion(sesionAntes, sesionExistente);
            return null; // Éxito
        } else {
            return "Error al actualizar la sesión";
        }
    }

    // HU-6: Cancelación de sesión
    @Override
    public String cancelarSesion(int sesionId) {
        Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);

        if (sesion == null) {
            return "La sesión no existe";
        }

        // Validación HU-6 CA3
        if (sesion.estaCompletada()) {
            return "No es posible modificar una sesión ya finalizada";
        }

        if (sesionDAO.cancelarSesion(sesionId)) {
            // Primero se notifica (mientras las reservas siguen vigentes) y después se cancelan
            ultimosNotificados = notificador.notificarCancelacion(sesion);
            reservaDAO.cancelarReservasDeSesion(sesionId);
            return null; // Éxito
        }
        return "Error al cancelar la sesión";
    }

    // HU-7: Búsqueda de sesiones
    @Override
    public List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad) {
        return sesionDAO.buscarSesiones(asignatura, sede, modalidad);
    }

    @Override
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {
        return sesionDAO.obtenerSesionesPorTutor(tutorId);
    }

    // Validación auxiliar de sede
    private boolean validarSede(String sede, String modalidad) {
        if (MODALIDAD_VIRTUAL.equalsIgnoreCase(modalidad)) {
            return sede != null && !sede.trim().isEmpty(); // Puede ser un enlace o "Virtual"
        }

        // Sedes presenciales válidas (ver enum Sede)
        return Sede.esPresencialValida(sede);
    }

    @Override
    public void verificarYActualizarSesionesPasadas() {
        sesionDAO.actualizarSesionesPasadas();
        // Aquí se podría agregar lógica de notificación (ej. enviar correo o mostrar alerta)
        System.out.println("Notificación HU-6: Se han procesado las sesiones pasadas.");
    }

    @Override
    public Sesion obtenerSesionPorId(int id) {
        return sesionDAO.obtenerSesionPorId(id);
    }

    @Override
    public List<Sesion> obtenerTodasLasSesionesDisponibles() {
        return sesionDAO.obtenerTodasLasSesionesDisponibles();
    }

    @Override
    public int getUltimosNotificados() {
        return ultimosNotificados;
    }

    /** Cuántos estudiantes tienen reserva vigente en la sesión. */
    @Override
    public int contarEstudiantesConReserva(int sesionId) {
        return notificador.contarEstudiantesConReserva(sesionId);
    }
}
