/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.dao;

import studybuddy.model.Sesion;
import java.util.List;
import java.time.LocalDateTime;

public interface SesionDAO {
    boolean crearSesion(Sesion sesion);
    boolean actualizarSesion(Sesion sesion);
    boolean cancelarSesion(int id);
    Sesion obtenerSesionPorId(int id);
    List<Sesion> obtenerSesionesPorTutor(int tutorId);
    List<Sesion> obtenerTodasLasSesionesDisponibles();
    List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad);
    boolean verificarCruceHorario(int tutorId, String fecha, String horaInicio, String horaFin);
    
    // AGREGADO: Método para el job de HU-6 (Validación de sesiones pasadas)
    void actualizarSesionesPasadas();
    // Para HU-9 (Solapamiento)
    List<Sesion> buscarSesionesPorUsuarioYRango(int idUsuario, LocalDateTime inicio, LocalDateTime fin);

// Para HU-11 (Reportes)
    double calcularHorasCompletadas(int idTutor, LocalDateTime inicio, LocalDateTime fin);
    int contarSesionesCompletadas(int idTutor, LocalDateTime inicio, LocalDateTime fin);
}