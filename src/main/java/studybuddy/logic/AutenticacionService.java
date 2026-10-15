package studybuddy.logic;

import studybuddy.dao.UsuarioDAO;
import studybuddy.model.Usuario;
import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para autenticación y registro.
 * Las validaciones de datos están en {@link IValidadorUsuario}.
 */
public class AutenticacionService implements IAutenticacionService {

    private final UsuarioDAO usuarioDAO;
    private final IValidadorUsuario validador;

    public AutenticacionService(UsuarioDAO usuarioDAO, IValidadorUsuario validador) {
        this.usuarioDAO = usuarioDAO;
        this.validador = validador;
    }

    /**
     * Limpia y normaliza la contraseña (en producción aplicar hash).
     * @param contraseña Contraseña raw
     * @return Contraseña procesada
     */
    public String procesarContraseña(String contraseña) {
        if (contraseña == null) return "";
        return contraseña.trim();
    }

    /**
     * Autentica un usuario verificando correo y contraseña.
     * @param correo Correo del usuario
     * @param contraseña Contraseña proporcionada
     * @return Usuario autenticado o null si falla
     */
    @Override
    public Usuario autenticar(String correo, String contraseña) {
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

    /**
     * Registra un nuevo usuario tras validar reglas de negocio.
     * @param usuario Objeto Usuario a registrar
     * @return Mensaje de resultado o null si éxito
     * @throws SQLException Si ocurre error en BD
     */
    @Override
    public String registrarUsuario(Usuario usuario) throws SQLException {
        // Validar dominio institucional
        if (!validador.validarDominioInstitucional(usuario.getCorreo())) {
            return "El correo debe ser institucional (@unimayor.edu.co)";
        }

        // Validar campos no vacíos
        if (!validador.validarCamposNoVacios(
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getContraseña(),
                usuario.getFacultad(),
                usuario.getProgramaAcademico()
        )) {
            return "Todos los campos son obligatorios";
        }

        // Validar que el correo no exista
        if (usuarioDAO.existeCorreo(usuario.getCorreo().trim().toLowerCase())) {
            return "El correo ya está registrado";
        }

        // Procesar contraseña y registrar
        usuario.setContraseña(procesarContraseña(usuario.getContraseña()));
        usuario.setCorreo(usuario.getCorreo().trim().toLowerCase());
        usuarioDAO.registrar(usuario);
        return null; // null indica éxito
    }
}