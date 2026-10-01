/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.dao;

import studybuddy.model.Sesion;
import java.util.List;

public interface SesionDAO {
    boolean crearSesion(Sesion sesion);
    boolean actualizarSesion(Sesion sesion);
    boolean cancelarSesion(int id);
    Sesion obtenerSesionPorId(int id);
    List<Sesion> obtenerSesionesPorTutor(int tutorId);
    List<Sesion> obtenerTodasLasSesionesDisponibles();
    List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad);
    boolean verificarCruceHorario(int tutorId, String fecha, String horaInicio, String horaFin);
}