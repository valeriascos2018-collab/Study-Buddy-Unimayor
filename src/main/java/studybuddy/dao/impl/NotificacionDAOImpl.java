/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;

/**
 *
 * @author yoban
 */

import studybuddy.config.ConexionBD;
import studybuddy.dao.NotificacionDAO;
import studybuddy.model.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAOImpl implements NotificacionDAO {

    // La fecha se guarda como texto en una columna DATETIME para evitar
    // desfases de zona horaria entre Java y MySQL/MariaDB.
    private static final DateTimeFormatter FORMATO_BD =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean crearVarias(List<Notificacion> notificaciones) {
        if (notificaciones == null || notificaciones.isEmpty()) {
            return true;
        }
        String sql = "INSERT INTO notificaciones "
                   + "(usuario_id, sesion_id, tipo, mensaje, leida, fecha_creacion) "
                   + "VALUES (?, ?, ?, ?, 0, ?)";
        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (Notificacion n : notificaciones) {
                    ps.setInt(1, n.getUsuarioId());
                    if (n.getSesionId() == null) {
                        ps.setNull(2, Types.INTEGER);
                    } else {
                        ps.setInt(2, n.getSesionId());
                    }
                    ps.setString(3, n.getTipo());
                    ps.setString(4, n.getMensaje());
                    ps.setString(5, n.getFechaCreacion().format(FORMATO_BD));
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error al crear notificaciones: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Notificacion> obtenerPorUsuario(int usuarioId) {
        List<Notificacion> lista = new ArrayList<>();
        String sql = "SELECT id, usuario_id, sesion_id, tipo, mensaje, leida, "
                   + "CAST(fecha_creacion AS CHAR) AS fecha_texto "
                   + "FROM notificaciones WHERE usuario_id = ? "
                   + "ORDER BY fecha_creacion DESC, id DESC";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notificacion n = new Notificacion();
                    n.setId(rs.getInt("id"));
                    n.setUsuarioId(rs.getInt("usuario_id"));
                    int sesionId = rs.getInt("sesion_id");
                    n.setSesionId(rs.wasNull() ? null : sesionId);
                    n.setTipo(rs.getString("tipo"));
                    n.setMensaje(rs.getString("mensaje"));
                    n.setLeida(rs.getBoolean("leida"));
                    String texto = rs.getString("fecha_texto");
                    if (texto != null && texto.length() >= 19) {
                        n.setFechaCreacion(LocalDateTime.parse(texto.substring(0, 19), FORMATO_BD));
                    }
                    lista.add(n);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener notificaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int contarNoLeidas(int usuarioId) {
        String sql = "SELECT COUNT(*) FROM notificaciones WHERE usuario_id = ? AND leida = 0";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al contar notificaciones: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean marcarComoLeida(int notificacionId, int usuarioId) {
        String sql = "UPDATE notificaciones SET leida = 1 WHERE id = ? AND usuario_id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificacionId);
            ps.setInt(2, usuarioId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar notificación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean marcarTodasComoLeidas(int usuarioId) {
        String sql = "UPDATE notificaciones SET leida = 1 WHERE usuario_id = ? AND leida = 0";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al marcar notificaciones: " + e.getMessage());
            return false;
        }
    }
}

