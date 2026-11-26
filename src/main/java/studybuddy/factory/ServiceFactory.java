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
import studybuddy.logic.*;
import studybuddy.logic.strategy.ValidadorAdministrador;
import studybuddy.logic.strategy.ValidadorEstudiante;
import studybuddy.logic.strategy.ValidadorTutor;

public final class ServiceFactory {

    private ServiceFactory() {}

    // --- Estrategias de Validación (Strategy) ---
    public static IValidadorUsuario crearValidadorEstudiante() {
        return new ValidadorEstudiante();
    }

    public static IValidadorUsuario crearValidadorTutor() {
        return new ValidadorTutor();
    }

    public static IValidadorUsuario crearValidadorAdministrador() {
        return new ValidadorAdministrador();
    }

    // --- Servicios de Negocio ---
    public static IAutenticacionService crearAutenticacionService() {
        return new AutenticacionService(new UsuarioDAOImpl(), crearValidadorEstudiante());
    }

    public static IPerfilService crearPerfilService() {
        return new PerfilService(new UsuarioDAOImpl(), crearValidadorEstudiante());
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

    public static IReporteService crearReporteService() {
        return new ReporteService(new SesionDAOImpl());
    }

    public static TutoriasFacade crearTutoriasFacade() {
        return new TutoriasFacade(crearSesionService());
    }
}