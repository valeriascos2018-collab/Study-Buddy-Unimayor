/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package studybuddy.logic;

import studybuddy.dao.ReservaDAO;
import studybuddy.dao.SesionDAO;
import studybuddy.model.Reserva;
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

    public SesionService(
            SesionDAO sesionDAO,
            ReservaDAO reservaDAO,
            INotificadorSesiones notificador) {

        this.sesionDAO = sesionDAO;
        this.reservaDAO = reservaDAO;
        this.notificador = notificador;
    }

    // ==========================================
    // HU-4: PUBLICAR SESION
    // ==========================================

    @Override
    public String publicarSesion(
            String asignatura,
            String tema,
            int cupoMaximo,
            String tipoSesion,
            String modalidad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            String sede,
            int tutorId) {

        if (asignatura == null || asignatura.trim().isEmpty()
                || tema == null || tema.trim().isEmpty()
                || tipoSesion == null || tipoSesion.trim().isEmpty()
                || modalidad == null || modalidad.trim().isEmpty()
                || cupoMaximo <= 0) {

            return "Debe completar todos los campos obligatorios";
        }

        if (fecha == null || horaInicio == null || horaFin == null) {
            return "Debe seleccionar una fecha y un horario válido";
        }

        if (fecha.isBefore(LocalDate.now())) {
            return "No se puede publicar una sesión en una fecha pasada";
        }

        if (!horaFin.isAfter(horaInicio)) {
            return "La hora de fin debe ser posterior a la hora de inicio";
        }

        if (!validarSede(sede, modalidad)) {
            return "La sede seleccionada no es válida";
        }

        if (sesionDAO.verificarCruceHorario(
                tutorId,
                fecha.toString(),
                horaInicio.toString(),
                horaFin.toString())) {

            return "Conflicto de horario: ya tienes una actividad programada en este horario";
        }

        // Crear sesión utilizando el patrón Builder.
        Sesion sesion = new Sesion.Builder()
                .conAsignatura(asignatura)
                .conTema(tema)
                .conCupoMaximo(cupoMaximo)
                .conTipoSesion(tipoSesion)
                .conModalidad(modalidad)
                .conFecha(fecha)
                .conHoraInicio(horaInicio)
                .conHoraFin(horaFin)
                .conSede(sede)
                .conTutorId(tutorId)
                .construir();

        if (sesionDAO.crearSesion(sesion)) {
            return null;
        }

        return "Error al publicar la sesión";
    }

    // ==========================================
    // HU-6: EDITAR SESION
    // ==========================================

    @Override
    public String editarSesion(
            int sesionId,
            String asignatura,
            String tema,
            int cupoMaximo,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            String sede) {

        ultimosNotificados = 0;

        Sesion sesionExistente =
                sesionDAO.obtenerSesionPorId(sesionId);

        if (sesionExistente == null) {
            return "La sesión no existe";
        }

        if (sesionExistente.estaCompletada()) {
            return "No es posible modificar una sesión ya finalizada";
        }

        if (asignatura == null || asignatura.trim().isEmpty()
                || tema == null || tema.trim().isEmpty()
                || cupoMaximo <= 0
                || fecha == null
                || horaInicio == null
                || horaFin == null) {

            return "Los datos ingresados no son válidos";
        }

        if (fecha.isBefore(LocalDate.now())) {
            return "No se puede programar una sesión en una fecha pasada";
        }

        if (!horaFin.isAfter(horaInicio)) {
            return "La hora de fin debe ser posterior a la hora de inicio";
        }

        if (!validarSede(sede, sesionExistente.getModalidad())) {
            return "La sede seleccionada no es válida";
        }

        // Obtener los datos anteriores para las notificaciones.
        Sesion sesionAntes =
                sesionDAO.obtenerSesionPorId(sesionId);

        sesionExistente.setAsignatura(asignatura);
        sesionExistente.setTema(tema);
        sesionExistente.setCupoMaximo(cupoMaximo);
        sesionExistente.setFecha(fecha);
        sesionExistente.setHoraInicio(horaInicio);
        sesionExistente.setHoraFin(horaFin);
        sesionExistente.setSede(sede);

        if (sesionDAO.actualizarSesion(sesionExistente)) {

            ultimosNotificados =
                    notificador.notificarEdicion(
                            sesionAntes,
                            sesionExistente
                    );

            return null;
        }

        return "Error al actualizar la sesión";
    }

    // ==========================================
    // HU-6: CANCELAR SESION
    // ==========================================

    @Override
    public String cancelarSesion(int sesionId) {

        ultimosNotificados = 0;

        Sesion sesion =
                sesionDAO.obtenerSesionPorId(sesionId);

        if (sesion == null) {
            return "La sesión no existe";
        }

        if (sesion.estaCompletada()) {
            return "No es posible cancelar una sesión ya finalizada";
        }

        if (sesionDAO.cancelarSesion(sesionId)) {

            ultimosNotificados =
                    notificador.notificarCancelacion(sesion);

            reservaDAO.cancelarReservasDeSesion(sesionId);

            return null;
        }

        return "Error al cancelar la sesión";
    }

    // ==========================================
    // HU-7: BUSCAR SESIONES
    // ==========================================

    @Override
    public List<Sesion> buscarSesiones(
            String asignatura,
            String sede,
            String modalidad) {

        return sesionDAO.buscarSesiones(
                asignatura,
                sede,
                modalidad
        );
    }

    @Override
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {

        return sesionDAO.obtenerSesionesPorTutor(tutorId);
    }

    @Override
    public Sesion obtenerSesionPorId(int id) {

        return sesionDAO.obtenerSesionPorId(id);
    }

    @Override
    public List<Sesion> obtenerTodasLasSesionesDisponibles() {

        return sesionDAO.obtenerTodasLasSesionesDisponibles();
    }

    // ==========================================
    // VALIDAR SEDE
    // ==========================================

    private boolean validarSede(String sede, String modalidad) {

        if (modalidad == null || modalidad.trim().isEmpty()) {
            return false;
        }

        if (MODALIDAD_VIRTUAL.equalsIgnoreCase(modalidad)) {
            return sede != null && !sede.trim().isEmpty();
        }

        return Sede.esPresencialValida(sede);
    }

    // ==========================================
    // HU-6: ACTUALIZAR SESIONES PASADAS
    // ==========================================

    @Override
    public void verificarYActualizarSesionesPasadas() {

        sesionDAO.actualizarSesionesPasadas();

        System.out.println(
                "Notificación HU-6: Se han procesado las sesiones pasadas."
        );
    }

    // ==========================================
    // NOTIFICACIONES
    // ==========================================

    @Override
    public int getUltimosNotificados() {

        return ultimosNotificados;
    }

    @Override
    public int contarEstudiantesConReserva(int sesionId) {

        return notificador.contarEstudiantesConReserva(sesionId);
    }

    // ==========================================
    // HU-10: MARCAR SESION COMO COMPLETADA
    // ==========================================

    @Override
    public String marcarComoCompletada(
            int sesionId,
            int tutorId) {

        Sesion sesion =
                sesionDAO.obtenerSesionPorId(sesionId);

        if (sesion == null) {
            return "La sesión no existe";
        }

        if (sesion.getTutorId() != tutorId) {
            return "No tienes permiso para modificar esta sesión";
        }

        if (sesion.estaCompletada()) {
            return "La sesión ya está marcada como completada";
        }

        sesion.setEstado("COMPLETADA");

        if (sesionDAO.actualizarSesion(sesion)) {
            return null;
        }

        return "Error al actualizar la sesión en la base de datos";
    }

    // ==========================================
    // HU-10: REGISTRAR ASISTENCIA
    // ==========================================

    @Override
    public void registrarAsistenciaEstudiante(int idReserva, boolean asistio) throws Exception {
        if (idReserva <= 0) {
            throw new Exception("El identificador de reserva no es válido.");
        }
        Reserva reserva = reservaDAO.obtenerPorId(idReserva);
        if (reserva == null) {
            throw new Exception("La reserva no existe.");
        }
        Sesion sesion = sesionDAO.obtenerSesionPorId(reserva.getSesionId());
        if (sesion == null) {
            throw new Exception("La sesión asociada no existe.");
        }
        if (!sesion.estaCompletada()) {
            throw new Exception("Solo se puede registrar asistencia cuando la sesión está COMPLETADA.");
        }
        if (!"CONFIRMADA".equalsIgnoreCase(reserva.getEstado())
                && !"PROGRAMADA".equalsIgnoreCase(reserva.getEstado())) {
            throw new Exception("La reserva ya fue procesada o está cancelada.");
        }
        String nuevoEstado = asistio ? "ASISTIO" : "NO_ASISTIO";
        if (!reservaDAO.actualizarEstado(idReserva, nuevoEstado)) {
            throw new Exception("No se pudo guardar la asistencia en MariaDB.");
        }
    }

    // ==========================================
    // HU-10: FINALIZAR SESION
    // ==========================================

    @Override
    public void finalizarSesion(int idSesion) throws Exception {

        Sesion sesion =
                sesionDAO.obtenerSesionPorId(idSesion);

        if (sesion == null) {
            throw new Exception("La sesión no existe");
        }

        if (sesion.estaCompletada()) {
            throw new Exception("La sesión ya está completada");
        }

        sesion.setEstado("COMPLETADA");

        if (!sesionDAO.actualizarSesion(sesion)) {

            throw new Exception(
                    "No se pudo actualizar el estado de la sesión"
            );
        }
    }

    @Override
    public List<Reserva> obtenerAsistentes(int sesionId) {
        return reservaDAO.obtenerReservasPorSesion(sesionId);
    }
}
