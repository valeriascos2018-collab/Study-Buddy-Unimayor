package co.edu.unimayor.studybuddy.dao.impl;

import co.edu.unimayor.studybuddy.config.ConexionBD;
import co.edu.unimayor.studybuddy.dao.UsuarioDAO;
import co.edu.unimayor.studybuddy.model.Rol;
import co.edu.unimayor.studybuddy.model.Usuario;

import java.sql.*;

/**
 * Implementación concreta del DAO de Usuario usando JDBC.
 * Maneja sentencias preparadas para prevenir SQL Injection.
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    private final Connection conexion;
    
    public UsuarioDAOImpl() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }
    
    @Override
    public void registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_completo, correo, contraseña, facultad, programa_academico, rol) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContraseña()); // En producción usar hash
            ps.setString(4, usuario.getFacultad());
            ps.setString(5, usuario.getProgramaAcademico());
            ps.setString(6, usuario.getRol().name());
            
            ps.executeUpdate();
        }
    }
    
    @Override
    public Usuario obtenerPorCorreo(String correo) throws SQLException {
        String sql = "SELECT id, nombre_completo, correo, contraseña, facultad, programa_academico, rol " +
                     "FROM usuarios WHERE correo = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public void actualizarPerfil(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre_completo=?, facultad=?, programa_academico=? " +
                     "WHERE id=?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getFacultad());
            ps.setString(3, usuario.getProgramaAcademico());
            ps.setInt(4, usuario.getId());
            
            ps.executeUpdate();
        }
    }
    
    @Override
    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Método auxiliar para mapear ResultSet a objeto Usuario.
     */
    private Usuario mapearResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre_completo"),
            rs.getString("correo"),
            rs.getString("contraseña"),
            rs.getString("facultad"),
            rs.getString("programa_academico"),
            Rol.valueOf(rs.getString("rol"))
        );
    }
}
