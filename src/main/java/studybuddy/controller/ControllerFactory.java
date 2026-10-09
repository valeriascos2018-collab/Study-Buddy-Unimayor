/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

/**
 *
 * @author yoban
 */
public class ControllerFactory {
    
    public static LoginController crearLoginController(FrmLogin vista) {
        IAutenticacionService servicio = new AutenticacionService();
        return new LoginController(vista, servicio);
    }
    
    public static RegistroController crearRegistroController(FrmRegistro vista) {
        IAutenticacionService servicio = new AutenticacionService();
        return new RegistroController(vista, servicio);
    }
    
    public static PerfilController crearPerfilController(FrmPerfil vista) {
        PerfilService servicio = new PerfilService();
        return new PerfilController(vista, servicio);
    }
}
