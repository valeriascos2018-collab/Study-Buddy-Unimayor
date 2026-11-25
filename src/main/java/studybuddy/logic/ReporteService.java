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
        if (idTutor <= 0 || fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            throw new SQLException("El tutor y el rango de fechas deben ser válidos.");
        }
        // HU-11: consultar únicamente sesiones COMPLETADAS en el rango solicitado.
        double totalHoras = sesionDAO.calcularHorasCompletadas(idTutor, fechaInicio, fechaFin);
        int totalSesiones = sesionDAO.contarSesionesCompletadas(idTutor, fechaInicio, fechaFin);
        int asistencias = sesionDAO.contarAsistencias(idTutor, fechaInicio, fechaFin, "ASISTIO");
        int inasistencias = sesionDAO.contarAsistencias(idTutor, fechaInicio, fechaFin, "NO_ASISTIO");

        Map<String, Object> reporte = new HashMap<>();
        reporte.put("totalHoras", totalHoras);
        reporte.put("totalSesiones", totalSesiones);
        reporte.put("sinDatos", totalSesiones == 0);
        reporte.put("totalAsistencias", asistencias);
        reporte.put("totalInasistencias", inasistencias);
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        
        return reporte;
    }
}
