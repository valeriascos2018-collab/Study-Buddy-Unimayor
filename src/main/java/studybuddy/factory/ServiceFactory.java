/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.factory;

/**
 *
 * @author yoban
 */
import studybuddy.dao.impl.NotificacionDAOImpl;
import studybuddy.dao.impl.ReservaDAOImpl;
import studybuddy.dao.impl.SesionDAOImpl;
import studybuddy.dao.impl.UsuarioDAOImpl;
import studybuddy.logic.AutenticacionService;
import studybuddy.logic.IAutenticacionService;
import studybuddy.logic.IBandejaNotificaciones;
import studybuddy.logic.IPerfilService;
import studybuddy.logic.IReservaService;
import studybuddy.logic.ISesionService;
import studybuddy.logic.IValidadorUsuario;
import studybuddy.logic.NotificacionService;
import studybuddy.logic.PerfilService;
import studybuddy.logic.ReservaService;
import studybuddy.logic.SesionService;
import studybuddy.logic.ValidadorUsuario;

/**
 * Raíz de composición de la capa lógica: es el ÚNICO lugar que conoce las clases
 * concretas (DAOImpl y servicios) y las conecta entre sí. Todo lo demás depende de interfaces.
 * Para cambiar de MySQL a otra fuente de datos, o para probar con DAOs falsos,
 * solo se toca esta clase.
 */
public final class ServiceFactory {

    private ServiceFactory() {}

    public static IValidadorUsuario crearValidadorUsuario() {
        return new ValidadorUsuario();
    }

    public static IAutenticacionService crearAutenticacionService() {
        return new AutenticacionService(new UsuarioDAOImpl(), crearValidadorUsuario());
    }

    public static IPerfilService crearPerfilService() {
        return new PerfilService(new UsuarioDAOImpl(), crearValidadorUsuario());
    }

    public static ISesionService crearSesionService() {
        return new SesionService(new SesionDAOImpl(), new ReservaDAOImpl(), crearNotificacionService());
    }

    public static IReservaService crearReservaService() {
        return new ReservaService(new ReservaDAOImpl(), new SesionDAOImpl());
    }

    public static IBandejaNotificaciones crearBandejaNotificaciones() {
        return crearNotificacionService();
    }

    private static NotificacionService crearNotificacionService() {
        return new NotificacionService(new NotificacionDAOImpl(), new ReservaDAOImpl());
    }
}
