/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;

import studybuddy.dao.SesionDAO;
import studybuddy.model.Sesion;
import studybuddy.config.ConexionBD; // Asegúrate de que esta ruta sea correcta
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionDAOImpl implements SesionDAO {
    
    @Override
    public boolean crearSesion(Sesion sesion) {
        String sql = "INSERT INTO sesiones (asignatura, tema, cupo_maximo, cupos_disponibles, " +
                     "tipo_sesion, modalidad, fecha, hora_inicio, hora_fin, sede, estado, tutor_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // CORRECCIÓN: ConexionBD.getConexion() es estático, no necesita getInstancia()
        try (Connection conn = ConexionBD.getConexion();
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
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        sesion.setId(rs.getInt(1));
                    }
                }
            }
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear la sesión: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean actualizarSesion(Sesion sesion) {
        String sql = "UPDATE sesiones SET asignatura=?, tema=?, cupo_maximo=?, " +
                     "tipo_sesion=?, modalidad=?, fecha=?, hora_inicio=?, hora_fin=?, " +
                     "sede=?, estado=? WHERE id=?";
        
        try (Connection conn = ConexionBD.getConexion();
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
            System.err.println("Error al actualizar la sesión: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean cancelarSesion(int id) {
        String sql = "UPDATE sesiones SET estado='CANCELADA' WHERE id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al cancelar la sesión: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Sesion obtenerSesionPorId(int id) {
        String sql = "SELECT s.*, u.nombre as tutor_nombre FROM sesiones s " +
                     "JOIN usuarios u ON s.tutor_id = u.id WHERE s.id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSesion(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la sesión por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT * FROM sesiones WHERE tutor_id=? ORDER BY fecha, hora_inicio";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, tutorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener sesiones por tutor: " + e.getMessage());
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
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener sesiones disponibles: " + e.getMessage());
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
        
        try (Connection conn = ConexionBD.getConexion();
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
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar sesiones: " + e.getMessage());
        }
        
        return sesiones;
    }
    
    @Override
    public boolean verificarCruceHorario(int tutorId, String fecha, String horaInicio, String horaFin) {
        // Lógica simplificada de superposición de horarios: 
        // (InicioNuevo < FinExistente) AND (FinNuevo > InicioExistente)
        String sql = "SELECT COUNT(*) FROM sesiones WHERE tutor_id=? AND fecha=? " +
                     "AND estado='PROGRAMADA' AND hora_inicio < ? AND hora_fin > ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, tutorId);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(horaFin));      // Fin existente debe ser mayor que inicio nuevo
            ps.setTime(4, Time.valueOf(horaInicio));   // Inicio existente debe ser menor que fin nuevo
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si el conteo es > 0, hay cruce
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cruce de horario: " + e.getMessage());
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
        
        // Manejo seguro por si el JOIN no devuelve nombre (ej. si se consulta sin JOIN)
        String tutorNombre = rs.getString("tutor_nombre");
        sesion.setTutorNombre(tutorNombre != null ? tutorNombre : "Desconocido");
        
        return sesion;
    }
}