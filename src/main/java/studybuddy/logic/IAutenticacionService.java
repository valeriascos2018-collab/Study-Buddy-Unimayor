/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.model.Usuario;
import java.sql.SQLException;

public interface IAutenticacionService {
    Usuario autenticar(String correo, String contraseña);
    String registrarUsuario(Usuario usuario) throws SQLException;
    boolean validarDominioInstitucional(String correo);
    boolean validarCamposNoVacios(String... campos);
}