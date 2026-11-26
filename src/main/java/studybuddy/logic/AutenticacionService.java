package studybuddy.logic;

import studybuddy.dao.UsuarioDAO;
import studybuddy.factory.ServiceFactory;
import studybuddy.model.Usuario;
import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para autenticación y registro.
 * Las validaciones de datos están en {@link IValidadorUsuario}.
 */
public class AutenticacionService implements IAutenticacionService {

    private final UsuarioDAO usuarioDAO;
    private final IValidadorUsuario validadorBase;

    public AutenticacionService(UsuarioDAO usuarioDAO, IValidadorUsuario validador) {
        this.usuarioDAO = usuarioDAO;
        this.validadorBase = validador;
    }

    public String procesarContraseña(String contraseña) {
        if (contraseña == null) return "";
        return contraseña.trim();
    }

    @Override
    public Usuario autenticar(String correo, String contraseña) {
        if (correo == null || contraseña == null) {
            return null;
        }
        try {
            Usuario usuario = usuarioDAO.obtenerPorCorreo(correo.trim().toLowerCase());

            if (usuario != null && usuario.getContraseña().equals(procesarContraseña(contraseña))) {
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String registrarUsuario(Usuario usuario) throws SQLException {
        if (usuario == null) {
            return "Datos de usuario inválidos";
        }

        // 1. Selecciona la estrategia concreta según el rol del usuario
        IValidadorUsuario estrategiaRol = (usuario.getRol() != null)
                ? ServiceFactory.crearValidador(usuario.getRol())
                : validadorBase;

        // 2. Aplica la estrategia de validación de dominio
        if (!estrategiaRol.validarDominioInstitucional(usuario.getCorreo())) {
            return "El correo no pertenece al dominio institucional permitido para este rol";
        }

        // 3. Aplica la estrategia de validación de campos obligatorios
        if (!estrategiaRol.validarCamposNoVacios(
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getContraseña(),
                usuario.getFacultad(),
                usuario.getProgramaAcademico())) {
            return "Todos los campos obligatorios deben estar completos";
        }

        String correoNormalizado = usuario.getCorreo().trim().toLowerCase();

        if (usuarioDAO.existeCorreo(correoNormalizado)) {
            return "El correo ya está registrado";
        }

        usuario.setContraseña(procesarContraseña(usuario.getContraseña()));
        usuario.setCorreo(correoNormalizado);
        usuarioDAO.registrar(usuario);
        return null;
    }
}