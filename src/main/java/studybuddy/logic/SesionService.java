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
    
    public SesionService() {
        this.sesionDAO = new SesionDAOImpl();
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
        
        Sesion sesionExistente = sesionDAO.obtenerSesionPorId(sesionId);
        
        if (sesionExistente == null) {
            return "La sesión no existe";
        }
        
        // Validación HU-6 CA3 - No modificar sesión finalizada
        if ("COMPLETADA".equals(sesionExistente.getEstado())) {
            return "No es posible modificar una sesión ya finalizada";
        }
        
        // Actualizar datos
        sesionExistente.setAsignatura(asignatura);
        sesionExistente.setTema(tema);
        sesionExistente.setCupoMaximo(cupoMaximo);
        sesionExistente.setFecha(fecha);
        sesionExistente.setHoraInicio(horaInicio);
        sesionExistente.setHoraFin(horaFin);
        sesionExistente.setSede(sede);
        
        if (sesionDAO.actualizarSesion(sesionExistente)) {
            return null; // Éxito
        } else {
            return "Error al actualizar la sesión";
        }
    }
    
    // HU-6: Cancelación de sesión
    public String cancelarSesion(int sesionId) {
        Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);
        
        if (sesion == null) {
            return "La sesión no existe";
        }
        
        // Validación HU-6 CA3
        if ("COMPLETADA".equals(sesion.getEstado())) {
            return "No es posible modificar una sesión ya finalizada";
        }
        
        if (sesionDAO.cancelarSesion(sesionId)) {
            return null; // Éxito
        } else {
            return "Error al cancelar la sesión";
        }
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
        if ("VIRTUAL".equals(modalidad)) {
            return sede != null && !sede.trim().isEmpty(); // Puede ser un enlace
        }
        
        // Sedes presenciales válidas
        return "Bicentenario".equals(sede) || 
               "Casa Obando".equals(sede) || 
               "Encarnación".equals(sede);
    }
    public Sesion obtenerSesionPorId(int id) {
    return sesionDAO.obtenerSesionPorId(id);
    }

public List<Sesion> obtenerTodasLasSesionesDisponibles() {
    return sesionDAO.obtenerTodasLasSesionesDisponibles();
    }
}