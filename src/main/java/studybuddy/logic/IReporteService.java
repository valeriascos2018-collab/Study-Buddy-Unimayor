/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public interface IReporteService {
    // Retorna un mapa con el resumen: { "TotalHoras": 15, "SesionesCompletadas": 5 }
    Map<String, Object> generarReporteHoras(int idTutor, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException;
}