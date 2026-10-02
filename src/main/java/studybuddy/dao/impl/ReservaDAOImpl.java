/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;

import studybuddy.dao.ReservaDAO;
import studybuddy.model.Reserva;
import studybuddy.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
/**
 *
 * @author yoban
 */


public class ReservaDAOImpl implements ReservaDAO {

    @Override
    public boolean crearReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (estudiante_id, sesion_id, estado) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, reserva.getEstudianteId());
            ps.setInt(2, reserva.getSesionId());
            ps.setString(3, reserva.getEstado());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) reserva.setId(rs.getInt(1));
            }
            return filas > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public List<Reserva> obtenerReservasPorSesion(int sesionId) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, u.nombre as estudiante_nombre FROM reservas r " +
                     "JOIN usuarios u ON r.estudiante_id = u.id WHERE r.sesion_id = ?";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sesionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setEstudianteId(rs.getInt("estudiante_id"));
                r.setEstudianteNombre(rs.getString("estudiante_nombre"));
                r.setSesionId(rs.getInt("sesion_id"));
                r.setEstado(rs.getString("estado"));
                reservas.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reservas;
    }

    @Override
    public boolean verificarCruceHorarioEstudiante(int estudianteId, String fecha, String horaInicio, String horaFin) {
        String sql = "SELECT COUNT(*) FROM reservas r JOIN sesiones s ON r.sesion_id = s.id " +
                     "WHERE r.estudiante_id = ? AND s.fecha = ? AND s.estado = 'PROGRAMADA' AND " +
                     "((s.hora_inicio <= ? AND s.hora_fin >= ?) OR (s.hora_inicio <= ? AND s.hora_fin >= ?))";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, estudianteId);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(horaFin));
            ps.setTime(4, Time.valueOf(horaInicio));
            ps.setTime(5, Time.valueOf(horaInicio));
            ps.setTime(6, Time.valueOf(horaFin));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean actualizarEstado(int reservaId, String estado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, reservaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}