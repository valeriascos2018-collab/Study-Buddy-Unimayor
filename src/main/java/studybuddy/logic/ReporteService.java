/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.dao.SesionDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReporteService implements IReporteService {
    private final SesionDAO sesionDAO;

    public ReporteService(SesionDAO sesionDAO) {
        this.sesionDAO = sesionDAO;
    }

    @Override
    public Map<String, Object> generarReporteHoras(int idTutor, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException {
        // HU-11 Escenario 1 y 2: Consultar sesiones COMPLETADAS en el rango
        double totalHoras = sesionDAO.calcularHorasCompletadas(idTutor, fechaInicio, fechaFin);
        int totalSesiones = sesionDAO.contarSesionesCompletadas(idTutor, fechaInicio, fechaFin);

        if (totalSesiones == 0) {
            // El controlador/GUI deberá manejar este caso para mostrar el mensaje de "Sin datos"
            throw new SQLException("No hay datos de horas impartidas en el periodo seleccionado.");
        }

        Map<String, Object> reporte = new HashMap<>();
        reporte.put("totalHoras", totalHoras);
        reporte.put("totalSesiones", totalSesiones);
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        
        return reporte;
    }
}
