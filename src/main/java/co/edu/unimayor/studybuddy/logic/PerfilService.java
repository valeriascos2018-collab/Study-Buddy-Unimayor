package co.edu.unimayor.studybuddy.logic;

import co.edu.unimayor.studybuddy.dao.UsuarioDAO;
import co.edu.unimayor.studybuddy.dao.impl.UsuarioDAOImpl;
import co.edu.unimayor.studybuddy.model.Usuario;

import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para gestión de perfiles.
 * Permite consultar y actualizar datos del usuario logueado.
 */
public class PerfilService {
    
    private final UsuarioDAO usuarioDAO;
    private final AutenticacionService autenticacionService;
    
    public PerfilService() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.autenticacionService = new AutenticacionService();
    }
    
    /**
     * Obtiene el perfil completo de un usuario por correo.
     * @param correo Correo del usuario
     * @return Usuario encontrado o null
     */
    public Usuario obtenerPerfil(String correo) {
        try {
            return usuarioDAO.obtenerPorCorreo(correo);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Actualiza el perfil de un usuario validando reglas de negocio.
     * @param usuario Objeto Usuario con datos actualizados
     * @return Mensaje de error o null si éxito
     */
    public String actualizarPerfil(Usuario usuario) {
        // Validar campos no vacíos
        if (!autenticacionService.validarCamposNoVacios(
                usuario.getNombreCompleto(),
                usuario.getFacultad(),
                usuario.getProgramaAcademico()
        )) {
            return "Todos los campos son obligatorios";
        }
        
        try {
            usuarioDAO.actualizarPerfil(usuario);
            return null; // null indica éxito
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar perfil: " + e.getMessage();
        }
    }
}