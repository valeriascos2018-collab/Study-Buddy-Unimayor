/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

import studybuddy.dao.SesionDAO;
import studybuddy.dao.impl.SesionDAOImpl;
import studybuddy.model.Sesion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SesionService {
    
    private final SesionDAO sesionDAO;
    private final NotificacionService notificacionService;
    private int ultimosNotificados;
    
    public SesionService() {
        this.sesionDAO = new SesionDAOImpl();
        this.notificacionService = new NotificacionService();
    }
    
    // HU-4: Publicación de sesión
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
    public String editarSesion(int sesionId, String asignatura, String tema, 
                           int cupoMaximo, LocalDate fecha,
                           LocalTime horaInicio, LocalTime horaFin, String sede) {

    ultimosNotificados = 0;

    Sesion sesionExistente = sesionDAO.obtenerSesionPorId(sesionId);
    if (sesionExistente == null) {
        return "La sesión no existe";
    }
    if ("COMPLETADA".equals(sesionExistente.getEstado())) {
        return "No es posible modificar una sesión ya finalizada";
    }

    // Copia del estado anterior (otra lectura a la BD, así no se ve afectada por los setters)
    Sesion sesionAntes = sesionDAO.obtenerSesionPorId(sesionId);

    sesionExistente.setAsignatura(asignatura);
    sesionExistente.setTema(tema);
    sesionExistente.setCupoMaximo(cupoMaximo);
    sesionExistente.setFecha(fecha);
    sesionExistente.setHoraInicio(horaInicio);
    sesionExistente.setHoraFin(horaFin);
    sesionExistente.setSede(sede);

    if (sesionDAO.actualizarSesion(sesionExistente)) {
        ultimosNotificados = notificacionService.notificarEdicion(sesionAntes, sesionExistente);
        return null; // Éxito
    } else {
        return "Error al actualizar la sesión";
        }
    }
    // HU-6: Cancelación de sesión
    public String cancelarSesion(int sesionId) {
    Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);
    if (sesion == null) return "La sesión no existe";
    if ("COMPLETADA".equals(sesion.getEstado()))
        return "No es posible modificar una sesión ya finalizada";

    if (sesionDAO.cancelarSesion(sesionId)) {
        // ajusta el nombre del método al que tengas en NotificacionService
        ultimosNotificados = notificacionService.notificarCancelacion(sesion);
        return null;
    }
    return "Error al cancelar la sesión";
    }
    
    // HU-7: Búsqueda de sesiones
    public List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad) {
        return sesionDAO.buscarSesiones(asignatura, sede, modalidad);
    }
    
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {
        return sesionDAO.obtenerSesionesPorTutor(tutorId);
    }
    
    // Validación auxiliar de sede
    private boolean validarSede(String sede, String modalidad) {
        // CAMBIO: Corregido a "Virtual" (coincide con el combo) y uso de equalsIgnoreCase
        if ("Virtual".equalsIgnoreCase(modalidad)) {
            return sede != null && !sede.trim().isEmpty(); // Puede ser un enlace o "Virtual"
        }
        
        // Sedes presenciales válidas
        return "Bicentenario".equals(sede) || 
               "Casa Obando".equals(sede) || 
               "Encarnación".equals(sede);
    }
    public void verificarYActualizarSesionesPasadas() {
        sesionDAO.actualizarSesionesPasadas();
        // Aquí se podría agregar lógica de notificación (ej. enviar correo o mostrar alerta)
        System.out.println("Notificación HU-6: Se han procesado las sesiones pasadas.");
    }
    public Sesion obtenerSesionPorId(int id) {
    return sesionDAO.obtenerSesionPorId(id);
    }

    public List<Sesion> obtenerTodasLasSesionesDisponibles() {
    return sesionDAO.obtenerTodasLasSesionesDisponibles();
    }
    
    public int getUltimosNotificados() {
        return ultimosNotificados;
    }

    /** Cuántos estudiantes tienen reserva vigente en la sesión. */
    public int contarEstudiantesConReserva(int sesionId) {
        return notificacionService.contarEstudiantesConReserva(sesionId);
    }
}