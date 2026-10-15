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
import studybuddy.model.Usuario;

public class ControllerFactory {

    public static LoginController crearLoginController(FrmLogin vista) {
        return new LoginController(vista,
                ServiceFactory.crearAutenticacionService(),
                ServiceFactory.crearValidadorUsuario());
    }

    public static RegistroController crearRegistroController(FrmRegistro vista) {
        return new RegistroController(vista, ServiceFactory.crearAutenticacionService());
    }

    public static PerfilController crearPerfilController(FrmPerfil vista, Usuario usuario) {
        return new PerfilController(vista, usuario, ServiceFactory.crearPerfilService());
    }

    public static CatalogoTutoriasController crearCatalogoController(FrmCatalogoTutorias vista, Usuario estudiante) {
        return new CatalogoTutoriasController(vista, estudiante,
                ServiceFactory.crearSesionService(),
                ServiceFactory.crearReservaService());
    }

    public static PublicarSesionController crearPublicarSesionController(FrmPublicarSesion vista, int tutorId) {
        return new PublicarSesionController(vista, tutorId, ServiceFactory.crearSesionService());
    }

    public static MisSesionesController crearMisSesionesController(FrmMisSesiones vista, Usuario tutor) {
        return new MisSesionesController(vista, tutor, ServiceFactory.crearSesionService());
    }

    public static NotificacionesController crearNotificacionesController(FrmNotificaciones vista, Usuario usuario) {
        return new NotificacionesController(vista, usuario, ServiceFactory.crearBandejaNotificaciones());
    }
}
