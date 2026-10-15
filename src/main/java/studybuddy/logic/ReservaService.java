/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

import studybuddy.dao.ReservaDAO;
import studybuddy.dao.SesionDAO;
import studybuddy.model.Reserva;
import studybuddy.model.Sesion;

public class ReservaService implements IReservaService {

    private final ReservaDAO reservaDAO;
    private final SesionDAO sesionDAO;

    public ReservaService(ReservaDAO reservaDAO, SesionDAO sesionDAO) {
        this.reservaDAO = reservaDAO;
        this.sesionDAO = sesionDAO;
    }

    /**
     * HU-8: Reserva un cupo en una sesión.
     * HU-9: Valida que no haya cruce de horario.
     */
    @Override
    public String reservarCupo(int estudianteId, int sesionId) {
        Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);

        if (sesion == null) {
            return "La sesión no existe.";
        }

        if (!sesion.estaProgramada()) {
            return "La sesión no está disponible para reservas.";
        }

        if (sesion.getCuposDisponibles() <= 0) {
            return "No hay cupos disponibles para esta sesión.";
        }

        // HU-9: Validar cruce de horario del estudiante
        boolean hayCruce = reservaDAO.verificarCruceHorarioEstudiante(
            estudianteId,
            sesion.getFecha().toString(),
            sesion.getHoraInicio().toString(),
            sesion.getHoraFin().toString()
        );

        if (hayCruce) {
            return "Conflicto de horario: ya tienes una reserva en este horario.";
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setEstudianteId(estudianteId);
        reserva.setSesionId(sesionId);
        reserva.setEstado("CONFIRMADA");

        if (reservaDAO.crearReserva(reserva)) {
            // Actualizar cupos disponibles en la sesión
            sesion.setCuposDisponibles(sesion.getCuposDisponibles() - 1);
            sesionDAO.actualizarSesion(sesion);
            return null; // Éxito
        } else {
            return "Error al procesar la reserva.";
        }
    }
}
