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

public interface ISesionService {

    /** HU-4. @return mensaje de error o null si todo salió bien */
    String publicarSesion(String asignatura, String tema, int cupoMaximo,
                          String tipoSesion, String modalidad, LocalDate fecha,
                          LocalTime horaInicio, LocalTime horaFin, String sede,
                          int tutorId);

    /** HU-6. @return mensaje de error o null si todo salió bien */
    String editarSesion(int sesionId, String asignatura, String tema,
                        int cupoMaximo, LocalDate fecha,
                        LocalTime horaInicio, LocalTime horaFin, String sede);

    /** HU-6. @return mensaje de error o null si todo salió bien */
    String cancelarSesion(int sesionId);

    /** HU-7. */
    List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad);

    List<Sesion> obtenerSesionesPorTutor(int tutorId);

    Sesion obtenerSesionPorId(int id);

    List<Sesion> obtenerTodasLasSesionesDisponibles();

    void verificarYActualizarSesionesPasadas();

    int getUltimosNotificados();

    int contarEstudiantesConReserva(int sesionId);
}

