package studybuddy.logic;

import studybuddy.dao.UsuarioDAO;
import studybuddy.model.Usuario;
import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para gestión de perfiles.
 * Permite consultar y actualizar datos del usuario logueado.
 */
public class PerfilService implements IPerfilService {

    private final UsuarioDAO usuarioDAO;
    private final IValidadorUsuario validador;

    public PerfilService(UsuarioDAO usuarioDAO, IValidadorUsuario validador) {
        this.usuarioDAO = usuarioDAO;
        this.validador = validador;
    }

    /**
     * Obtiene el perfil completo de un usuario por correo.
     * @param correo Correo del usuario
     * @return Usuario encontrado o null
     */
    @Override
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
    @Override
    public String actualizarPerfil(Usuario usuario) {
        // Validar campos no vacíos
        if (!validador.validarCamposNoVacios(
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
