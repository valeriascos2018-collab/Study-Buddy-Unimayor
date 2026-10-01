/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;

import studybuddy.dao.SesionDAO;
import studybuddy.model.Sesion;
import studybuddy.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionDAOImpl implements SesionDAO {
    
    @Override
    public boolean crearSesion(Sesion sesion) {
        String sql = "INSERT INTO sesiones (asignatura, tema, cupo_maximo, cupos_disponibles, " +
                     "tipo_sesion, modalidad, fecha, hora_inicio, hora_fin, sede, estado, tutor_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, sesion.getAsignatura());
            ps.setString(2, sesion.getTema());
            ps.setInt(3, sesion.getCupoMaximo());
            ps.setInt(4, sesion.getCuposDisponibles());
            ps.setString(5, sesion.getTipoSesion());
            ps.setString(6, sesion.getModalidad());
            ps.setDate(7, Date.valueOf(sesion.getFecha()));
            ps.setTime(8, Time.valueOf(sesion.getHoraInicio()));
            ps.setTime(9, Time.valueOf(sesion.getHoraFin()));
            ps.setString(10, sesion.getSede());
            ps.setString(11, sesion.getEstado());
            ps.setInt(12, sesion.getTutorId());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    sesion.setId(rs.getInt(1));
                }
            }
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean actualizarSesion(Sesion sesion) {
        String sql = "UPDATE sesiones SET asignatura=?, tema=?, cupo_maximo=?, " +
                     "tipo_sesion=?, modalidad=?, fecha=?, hora_inicio=?, hora_fin=?, " +
                     "sede=?, estado=? WHERE id=?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sesion.getAsignatura());
            ps.setString(2, sesion.getTema());
            ps.setInt(3, sesion.getCupoMaximo());
            ps.setString(4, sesion.getTipoSesion());
            ps.setString(5, sesion.getModalidad());
            ps.setDate(6, Date.valueOf(sesion.getFecha()));
            ps.setTime(7, Time.valueOf(sesion.getHoraInicio()));
            ps.setTime(8, Time.valueOf(sesion.getHoraFin()));
            ps.setString(9, sesion.getSede());
            ps.setString(10, sesion.getEstado());
            ps.setInt(11, sesion.getId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean cancelarSesion(int id) {
        String sql = "UPDATE sesiones SET estado='CANCELADA' WHERE id=?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Sesion obtenerSesionPorId(int id) {
        String sql = "SELECT s.*, u.nombre as tutor_nombre FROM sesiones s " +
                     "JOIN usuarios u ON s.tutor_id = u.id WHERE s.id=?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapearSesion(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT * FROM sesiones WHERE tutor_id=? ORDER BY fecha, hora_inicio";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, tutorId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> obtenerTodasLasSesionesDisponibles() {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT s.*, u.nombre as tutor_nombre FROM sesiones s " +
                     "JOIN usuarios u ON s.tutor_id = u.id " +
                     "WHERE s.estado='PROGRAMADA' AND s.cupos_disponibles > 0 " +
                     "ORDER BY s.fecha, s.hora_inicio";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarSesiones(String asignatura, String sede, String modalidad) {
        List<Sesion> sesiones = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT s.*, u.nombre as tutor_nombre FROM sesiones s " +
            "JOIN usuarios u ON s.tutor_id = u.id " +
            "WHERE s.estado='PROGRAMADA' AND s.cupos_disponibles > 0"
        );
        
        if (asignatura != null && !asignatura.isEmpty()) {
            sql.append(" AND s.asignatura LIKE ?");
        }
        if (sede != null && !sede.isEmpty()) {
            sql.append(" AND s.sede = ?");
        }
        if (modalidad != null && !modalidad.isEmpty()) {
            sql.append(" AND s.modalidad = ?");
        }
        sql.append(" ORDER BY s.fecha, s.hora_inicio");
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (asignatura != null && !asignatura.isEmpty()) {
                ps.setString(paramIndex++, "%" + asignatura + "%");
            }
            if (sede != null && !sede.isEmpty()) {
                ps.setString(paramIndex++, sede);
            }
            if (modalidad != null && !modalidad.isEmpty()) {
                ps.setString(paramIndex++, modalidad);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sesiones;
    }
    
    @Override
    public boolean verificarCruceHorario(int tutorId, String fecha, String horaInicio, String horaFin) {
        String sql = "SELECT COUNT(*) FROM sesiones WHERE tutor_id=? AND fecha=? " +
                     "AND estado='PROGRAMADA' AND " +
                     "((hora_inicio <= ? AND hora_fin >= ?) OR " +
                     "(hora_inicio <= ? AND hora_fin >= ?))";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, tutorId);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(horaFin));
            ps.setTime(4, Time.valueOf(horaInicio));
            ps.setTime(5, Time.valueOf(horaInicio));
            ps.setTime(6, Time.valueOf(horaFin));
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método auxiliar para mapear ResultSet a objeto Sesion
    private Sesion mapearSesion(ResultSet rs) throws SQLException {
        Sesion sesion = new Sesion();
        sesion.setId(rs.getInt("id"));
        sesion.setAsignatura(rs.getString("asignatura"));
        sesion.setTema(rs.getString("tema"));
        sesion.setCupoMaximo(rs.getInt("cupo_maximo"));
        sesion.setCuposDisponibles(rs.getInt("cupos_disponibles"));
        sesion.setTipoSesion(rs.getString("tipo_sesion"));
        sesion.setModalidad(rs.getString("modalidad"));
        sesion.setFecha(rs.getDate("fecha").toLocalDate());
        sesion.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
        sesion.setHoraFin(rs.getTime("hora_fin").toLocalTime());
        sesion.setSede(rs.getString("sede"));
        sesion.setEstado(rs.getString("estado"));
        sesion.setTutorId(rs.getInt("tutor_id"));
        sesion.setTutorNombre(rs.getString("tutor_nombre"));
        return sesion;
    }
}