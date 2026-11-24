/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */

import studybuddy.model.Sesion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Fachada para centralizar la gestión de tutorías
 * en StudyBuddy.
 */
public class TutoriasFacade {

    private final ISesionService sesionService;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public TutoriasFacade(ISesionService sesionService) {
        this.sesionService = sesionService;
    }

    // ==========================================
    // HU-4: PUBLICAR TUTORIA
    // ==========================================

    public String publicarTutoria(
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

        return sesionService.publicarSesion(
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
    }

    // ==========================================
    // HU-6: EDITAR TUTORIA
    // ==========================================

    public String editarTutoria(
            int sesionId,
            String asignatura,
            String tema,
            int cupoMaximo,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            String sede) {

        return sesionService.editarSesion(
                sesionId,
                asignatura,
                tema,
                cupoMaximo,
                fecha,
                horaInicio,
                horaFin,
                sede
        );
    }

    // ==========================================
    // HU-6: CANCELAR TUTORIA
    // ==========================================

    public String cancelarTutoria(int sesionId) {

        return sesionService.cancelarSesion(sesionId);
    }

    // ==========================================
    // HU-7: CONSULTAR TUTORIAS
    // ==========================================

    public List<Sesion> buscarTutorias(
            String asignatura,
            String sede,
            String modalidad) {

        return sesionService.buscarSesiones(
                asignatura,
                sede,
                modalidad
        );
    }

    public List<Sesion> obtenerTutoriasPorTutor(int tutorId) {

        return sesionService.obtenerSesionesPorTutor(tutorId);
    }

    public Sesion obtenerTutoriaPorId(int id) {

        return sesionService.obtenerSesionPorId(id);
    }

    public List<Sesion> obtenerTutoriasDisponibles() {

        return sesionService.obtenerTodasLasSesionesDisponibles();
    }

    // ==========================================
    // HU-10: COMPLETAR TUTORIA
    // ==========================================

    public String completarTutoria(
            int sesionId,
            int tutorId) {

        return sesionService.marcarComoCompletada(
                sesionId,
                tutorId
        );
    }

    public void finalizarTutoria(int sesionId) throws Exception {

        sesionService.finalizarSesion(sesionId);
    }

    // ==========================================
    // HU-10: REGISTRAR ASISTENCIA
    // ==========================================

    public void registrarAsistencia(
            int idReserva,
            boolean asistio) throws Exception {

        sesionService.registrarAsistenciaEstudiante(
                idReserva,
                asistio
        );
    }

    // ==========================================
    // NOTIFICACIONES
    // ==========================================

    public int obtenerUltimosNotificados() {

        return sesionService.getUltimosNotificados();
    }

    public int contarEstudiantesReservados(int sesionId) {

        return sesionService.contarEstudiantesConReserva(sesionId);
    }

    // ==========================================
    // ACTUALIZAR SESIONES PASADAS
    // ==========================================

    public void actualizarTutoriasPasadas() {

        sesionService.verificarYActualizarSesionesPasadas();
    }
}

