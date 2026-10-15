/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.model.Usuario;

public interface IPerfilService {
    Usuario obtenerPerfil(String correo);

    /** @return mensaje de error o null si todo salió bien */
    String actualizarPerfil(Usuario usuario);
}

