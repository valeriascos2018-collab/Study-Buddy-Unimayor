/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.dao;

/**
 *
 * @author yoban
 */
import studybuddy.model.Notificacion;
import java.util.List;

public interface NotificacionDAO {
    /** Inserta todas las notificaciones en una sola transacción (o todas o ninguna). */
    boolean crearVarias(List<Notificacion> notificaciones);

    List<Notificacion> obtenerPorUsuario(int usuarioId);

    int contarNoLeidas(int usuarioId);

    boolean marcarComoLeida(int notificacionId, int usuarioId);

    boolean marcarTodasComoLeidas(int usuarioId);
}
