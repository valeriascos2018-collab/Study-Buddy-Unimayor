/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.dao;
import studybuddy.model.Reserva;
import java.util.List;
/**
 *
 * @author yoban
 */

public interface ReservaDAO {
    boolean crearReserva(Reserva reserva);
    List<Reserva> obtenerReservasPorSesion(int sesionId);
    boolean verificarCruceHorarioEstudiante(int estudianteId, String fecha, String horaInicio, String horaFin);
    boolean actualizarEstado(int reservaId, String estado);
}
