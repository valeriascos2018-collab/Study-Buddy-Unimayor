/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

import studybuddy.factory.ServiceFactory;
import studybuddy.gui.FrmCatalogoTutorias;
import studybuddy.gui.FrmLogin;
import studybuddy.gui.FrmMisSesiones;
import studybuddy.gui.FrmNotificaciones;
import studybuddy.gui.FrmPerfil;
import studybuddy.gui.FrmPublicarSesion;
import studybuddy.gui.FrmRegistro;
import studybuddy.gui.FrmReporteHoras;
import studybuddy.logic.TutoriasFacade;
import studybuddy.model.Usuario;

public class ControllerFactory {

    // ==========================================
    // LOGIN
    // ==========================================

   public static LoginController crearLoginController(FrmLogin vista) {
    return new LoginController(
        vista,
        ServiceFactory.crearAutenticacionService(),
        ServiceFactory.crearValidadorEstudiante()
    );
}

    // ==========================================
    // REGISTRO
    // ==========================================

    public static RegistroController crearRegistroController(FrmRegistro vista) {

        return new RegistroController(
                vista,
                ServiceFactory.crearAutenticacionService()
        );
    }

    // ==========================================
    // PERFIL
    // ==========================================

    public static PerfilController crearPerfilController(
            FrmPerfil vista,
            Usuario usuario) {

        return new PerfilController(
                vista,
                usuario,
                ServiceFactory.crearPerfilService()
        );
    }

    // ==========================================
    // CATALOGO DE TUTORIAS
    // ==========================================

    public static CatalogoTutoriasController crearCatalogoController(
            FrmCatalogoTutorias vista,
            Usuario estudiante) {

        return new CatalogoTutoriasController(
                vista,
                estudiante,
                ServiceFactory.crearSesionService(),
                ServiceFactory.crearReservaService()
        );
    }

    // ==========================================
    // PUBLICAR SESION
    // ==========================================

    public static PublicarSesionController crearPublicarSesionController(
            FrmPublicarSesion vista,
            int tutorId) {

        TutoriasFacade facade =
                ServiceFactory.crearTutoriasFacade();

        return new PublicarSesionController(
                vista,
                tutorId,
                facade
        );
    }

    // ==========================================
    // MIS SESIONES
    // ==========================================

    public static MisSesionesController crearMisSesionesController(
            FrmMisSesiones vista,
            Usuario tutor) {

        return new MisSesionesController(
                vista,
                tutor,
                ServiceFactory.crearSesionService()
        );
    }

    // ==========================================
    // NOTIFICACIONES
    // ==========================================

    public static NotificacionesController crearNotificacionesController(
            FrmNotificaciones vista,
            Usuario usuario) {

        return new NotificacionesController(
                vista,
                usuario,
                ServiceFactory.crearBandejaNotificaciones()
        );
    }

    // ==========================================
    // REPORTE DE HORAS
    // ==========================================

    public static ReporteController crearReporteController(
            FrmReporteHoras vista,
            Usuario usuario) {

        return new ReporteController(
                vista,
                ServiceFactory.crearReporteService(),
                usuario
        );
    }
}
