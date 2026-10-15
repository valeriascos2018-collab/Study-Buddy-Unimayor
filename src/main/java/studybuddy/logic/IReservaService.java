/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
public interface IReservaService {

    /** @return mensaje de error o null si la reserva se hizo correctamente */
    String reservarCupo(int estudianteId, int sesionId);
}

