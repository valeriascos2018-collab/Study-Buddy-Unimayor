/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.model.Notificacion;
import java.util.List;

public interface IBandejaNotificaciones {
    List<Notificacion> obtenerNotificaciones(int usuarioId);
    int contarNoLeidas(int usuarioId);
    boolean marcarComoLeida(int notificacionId, int usuarioId);
    boolean marcarTodasComoLeidas(int usuarioId);
}
