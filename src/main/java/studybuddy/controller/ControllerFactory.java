/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

import studybuddy.gui.FrmLogin;
import studybuddy.gui.FrmRegistro;
import studybuddy.gui.FrmPerfil;
import studybuddy.logic.IAutenticacionService;
import studybuddy.logic.AutenticacionService;
import studybuddy.logic.PerfilService;
import studybuddy.model.Usuario;

public class ControllerFactory {
    
    public static LoginController crearLoginController(FrmLogin vista) {
        IAutenticacionService servicio = new AutenticacionService();
        return new LoginController(vista, servicio);
    }
    
    public static RegistroController crearRegistroController(FrmRegistro vista) {
        IAutenticacionService servicio = new AutenticacionService();
        return new RegistroController(vista, servicio);
    }
    
    public static PerfilController crearPerfilController(FrmPerfil vista, Usuario usuario) {
        PerfilService servicio = new PerfilService();
        return new PerfilController(vista, usuario, servicio);
    }
}