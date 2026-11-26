package studybuddy.logic;


import studybuddy.dao.UsuarioDAO;
import studybuddy.factory.ServiceFactory;
import studybuddy.model.Usuario;
import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para gestión de perfiles.
 * Permite consultar y actualizar datos del usuario logueado.
 */
public class PerfilService implements IPerfilService {

    private final UsuarioDAO usuarioDAO;
    private final IValidadorUsuario validadorBase;

    public PerfilService(UsuarioDAO usuarioDAO, IValidadorUsuario validador) {
        this.usuarioDAO = usuarioDAO;
        this.validadorBase = validador;
    }

    /**
     * Obtiene el perfil completo de un usuario por correo.
     * @param correo Correo del usuario
     * @return Usuario encontrado o null
     */
    @Override
    public Usuario obtenerPerfil(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return null;
        }
        try {
            return usuarioDAO.obtenerPorCorreo(correo.trim().toLowerCase());
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
        if (usuario == null) {
            return "Datos de usuario inválidos";
        }

        IValidadorUsuario estrategiaRol = (usuario.getRol() != null)
                ? ServiceFactory.crearValidador(usuario.getRol())
                : this.validadorBase;

        if (!estrategiaRol.validarCamposNoVacios(
                usuario.getNombreCompleto(),
                usuario.getFacultad(),
                usuario.getProgramaAcademico())) {
            return "Todos los campos son obligatorios";
        }

        try {
            usuarioDAO.actualizarPerfil(usuario);
            return null;
        } catch (SQLException e) {
            return "Error al actualizar perfil: " + e.getMessage();
        }
    }
}