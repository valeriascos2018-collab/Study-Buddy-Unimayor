/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.model.Sesion;
public interface INotificadorSesiones {

    /** @return cuántos estudiantes fueron notificados */
    int notificarEdicion(Sesion antes, Sesion despues);

    /** @return cuántos estudiantes fueron notificados */
    int notificarCancelacion(Sesion sesion);

    /** Cuántos estudiantes tienen reserva vigente en la sesión. */
    int contarEstudiantesConReserva(int sesionId);
}
