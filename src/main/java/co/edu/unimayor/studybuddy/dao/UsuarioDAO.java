package co.edu.unimayor.studybuddy.dao;

import co.edu.unimayor.studybuddy.model.Usuario;
import java.sql.SQLException;

/**
 * Interfaz DAO para operaciones CRUD de usuarios.
 * Define el contrato de acceso a datos.
 */
public interface UsuarioDAO {
    
    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario Objeto Usuario a registrar
     * @throws SQLException Si ocurre error en la BD
     */
    void registrar(Usuario usuario) throws SQLException;
    
    /**
     * Obtiene un usuario por su correo electrónico.
     * @param correo Correo del usuario
     * @return Usuario encontrado o null si no existe
     * @throws SQLException Si ocurre error en la BD
     */
    Usuario obtenerPorCorreo(String correo) throws SQLException;
    
    /**
     * Actualiza los datos del perfil de un usuario.
     * @param usuario Objeto Usuario con datos actualizados
     * @throws SQLException Si ocurre error en la BD
     */
    void actualizarPerfil(Usuario usuario) throws SQLException;
    
    /**
     * Verifica si un correo ya existe en la base de datos.
     * @param correo Correo a verificar
     * @return true si existe, false en caso contrario
     * @throws SQLException Si ocurre error en la BD
     */
    boolean existeCorreo(String correo) throws SQLException;
}