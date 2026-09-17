package studybuddy.logic;

import studybuddy.dao.UsuarioDAO;
import studybuddy.dao.impl.UsuarioDAOImpl;
import studybuddy.model.Usuario;

import java.sql.SQLException;

/**
 * Servicio de lógica de negocio para autenticación.
 * Valida dominio institucional, campos vacíos y credenciales.
 */
public class AutenticacionService {
    
    private static final String DOMINIO_INSTITUCIONAL = "@unimayor.edu.co";
    private final UsuarioDAO usuarioDAO;
    
    public AutenticacionService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    /**
     * Valida que el correo pertenezca al dominio institucional.
     * @param correo Correo a validar
     * @return true si es válido, false en caso contrario
     */
    public boolean validarDominioInstitucional(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return correo.toLowerCase().endsWith(DOMINIO_INSTITUCIONAL);
    }
    
    /**
     * Valida que ningún campo obligatorio esté vacío.
     * @param campos Arreglo de strings a validar
     * @return true si todos tienen contenido, false si alguno está vacío
     */
    public boolean validarCamposNoVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
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
    public String registrarUsuario(Usuario usuario) throws SQLException {
        // Validar dominio institucional
        if (!validarDominioInstitucional(usuario.getCorreo())) {
            return "El correo debe ser institucional (@unimayor.edu.co)";
        }
        
        // Validar campos no vacíos
        if (!validarCamposNoVacios(
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
