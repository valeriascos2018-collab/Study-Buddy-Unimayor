/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

import studybuddy.dao.ReservaDAO;
import studybuddy.dao.impl.ReservaDAOImpl;

public class ReservaService {
    
    private final ReservaDAO reservaDAO;
    
    public ReservaService() {
        this.reservaDAO = new ReservaDAOImpl();
    }
    
    /**
     * HU-8: Reserva un cupo en una sesión.
     * HU-9: Valida que no haya cruce de horario.
     */
    public String reservarCupo(int estudianteId, String asignatura, String fecha) {
        // Implementar lógica de reserva y validación de cruce de horario
        return null; // o mensaje de error
    }
}
