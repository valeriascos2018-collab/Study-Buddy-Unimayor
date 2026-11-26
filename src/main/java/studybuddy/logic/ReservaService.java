/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

import studybuddy.dao.ReservaDAO;
import studybuddy.dao.SesionDAO;
import studybuddy.model.Reserva;
import studybuddy.model.Sesion;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;

public class ReservaService implements IReservaService {

    private final ReservaDAO reservaDAO;
    private final SesionDAO sesionDAO;

    public ReservaService(ReservaDAO reservaDAO, SesionDAO sesionDAO) {
        this.reservaDAO = reservaDAO;
        this.sesionDAO = sesionDAO;
    }

    /**
     * HU-8: Reserva un cupo en una sesión.
     * HU-9: Valida que no haya cruce de horario en las clases .
     */
    @Override
    public String reservarCupo(int estudianteId, int sesionId) {
        if (estudianteId <= 0 || sesionId <= 0) return "El estudiante y la sesión deben ser válidos.";
        Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);
        if (sesion == null) return "La sesión no existe.";
        if (!sesion.estaProgramada()) return "La sesión no está disponible para reservas.";
        if (sesion.getFecha() == null || sesion.getFecha().isBefore(java.time.LocalDate.now()))
            return "No se puede reservar una sesión cuya fecha ya pasó.";
        if (sesion.getHoraInicio() == null || sesion.getHoraFin() == null
                || !sesion.getHoraFin().isAfter(sesion.getHoraInicio()))
            return "El horario de la sesión no es válido.";
        Reserva reserva = new Reserva();
        reserva.setEstudianteId(estudianteId);
        reserva.setSesionId(sesionId);
        reserva.setEstado("CONFIRMADA");
        if (reservaDAO.crearReserva(reserva)) return null;
        return "No se pudo completar la reserva. Verifica los cupos, el horario y que no exista otra reserva.";
    }
    @Override
    public String reservarCupoGrupal(List<Integer> estudianteIds, int sesionId) {
    if (estudianteIds == null || estudianteIds.isEmpty())
        return "La lista de estudiantes no puede estar vacía.";
    if (sesionId <= 0) return "La sesión debe ser válida.";
    
    // Validar que no haya IDs duplicados
    if (estudianteIds.size() != new HashSet<>(estudianteIds).size())
        return "Hay estudiantes duplicados en la lista.";
    
    Sesion sesion = sesionDAO.obtenerSesionPorId(sesionId);
    if (sesion == null) return "La sesión no existe.";
    if (!sesion.estaProgramada()) return "La sesión no está disponible para reservas.";
    if (sesion.getFecha() == null || sesion.getFecha().isBefore(java.time.LocalDate.now()))
        return "No se puede reservar una sesión cuya fecha ya pasó.";
    if (sesion.getCuposDisponibles() < estudianteIds.size())
        return "No hay suficientes cupos disponibles para " + estudianteIds.size() + " estudiantes.";
    
    // Crear objetos Reserva
    List<Reserva> reservas = estudianteIds.stream()
        .map(id -> {
            Reserva r = new Reserva();
            r.setEstudianteId(id);
            r.setSesionId(sesionId);
            r.setEstado("CONFIRMADA");
            return r;
        })
        .collect(Collectors.toList());
    
    if (reservaDAO.crearReservas(reservas)) return null;
    return "No se pudo completar la reserva grupal. Verifica los cupos y horarios.";
    }
}
