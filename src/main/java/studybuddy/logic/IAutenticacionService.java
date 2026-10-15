/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;
import java.sql.SQLException;
import studybuddy.model.Usuario;
/**
 *
 * @author yoban
 */
public interface IAutenticacionService {
    Usuario autenticar(String correo, String contraseña);
    String registrarUsuario(Usuario usuario) throws SQLException;
}

