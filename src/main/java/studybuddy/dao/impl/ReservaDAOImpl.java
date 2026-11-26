/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;


import studybuddy.config.ConexionBD;
import studybuddy.dao.ReservaDAO;
import studybuddy.model.Reserva;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {

    /** HU-8: inserta una reserva individual reutilizando la transacción grupal. */
    @Override
    public boolean crearReserva(Reserva reserva) {
        if (reserva == null) return false;
        return crearReservas(List.of(reserva));
    }

    /** HU-8: inserta las reservas y descuenta los cupos en una única transacción. */
    @Override
    public boolean crearReservas(List<Reserva> reservas) {
        if (reservas == null || reservas.isEmpty()) return false;

        final String bloquear = "SELECT cupos_disponibles, estado, fecha, hora_inicio, hora_fin "
                + "FROM sesiones WHERE id = ? FOR UPDATE";
        final String cruce = "SELECT COUNT(*) FROM reservas r "
                + "JOIN sesiones s ON s.id = r.sesion_id "
                + "WHERE r.estudiante_id = ? AND r.estado IN ('CONFIRMADA','PROGRAMADA') "
                + "AND s.estado = 'PROGRAMADA' AND s.fecha = ? "
                + "AND s.hora_inicio < ? AND s.hora_fin > ?";
        final String insertar = "INSERT INTO reservas (estudiante_id, sesion_id, estado) VALUES (?, ?, 'CONFIRMADA')";
        final String descontar = "UPDATE sesiones SET cupos_disponibles = cupos_disponibles - ? "
                + "WHERE id = ? AND cupos_disponibles >= ? AND estado = 'PROGRAMADA'";

        int sesionId = reservas.get(0).getSesionId();
        int cantidad = reservas.size();

        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false);
            try {
                // 1. Bloquear sesión y verificar cupos
                int cupos;
                Date fecha;
                Time inicio, fin;
                try (PreparedStatement ps = conn.prepareStatement(bloquear)) {
                    ps.setInt(1, sesionId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("La sesión no existe.");
                        cupos = rs.getInt("cupos_disponibles");
                        if (!"PROGRAMADA".equalsIgnoreCase(rs.getString("estado")))
                            throw new SQLException("La sesión no está disponible para reservas.");
                        if (cupos < cantidad)
                            throw new SQLException("No hay suficientes cupos para el grupo (" + cantidad + " estudiantes).");
                        fecha = rs.getDate("fecha");
                        inicio = rs.getTime("hora_inicio");
                        fin = rs.getTime("hora_fin");
                    }
                }

                if (fecha.toLocalDate().isBefore(java.time.LocalDate.now()))
                    throw new SQLException("No se puede reservar una sesión cuya fecha ya pasó.");

                // 2. Validar cada estudiante del grupo
                for (Reserva reserva : reservas) {
                    // Bloquear fila del estudiante para serialización
                    try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM usuarios WHERE id = ? FOR UPDATE")) {
                        ps.setInt(1, reserva.getEstudianteId());
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) throw new SQLException("El estudiante " + reserva.getEstudianteId() + " no existe.");
                        }
                    }

                    // Verificar cruce de horario
                    try (PreparedStatement ps = conn.prepareStatement(cruce)) {
                        ps.setInt(1, reserva.getEstudianteId());
                        ps.setDate(2, fecha);
                        ps.setTime(3, fin);
                        ps.setTime(4, inicio);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0)
                                throw new SQLException("El estudiante " + reserva.getEstudianteId() + " ya tiene una reserva en ese horario.");
                        }
                    }
                }

                // 3. Insertar todas las reservas
                try (PreparedStatement ps = conn.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {
                    for (Reserva reserva : reservas) {
                        ps.setInt(1, reserva.getEstudianteId());
                        ps.setInt(2, reserva.getSesionId());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        int idx = 0;
                        while (keys.next() && idx < reservas.size()) {
                            reservas.get(idx).setId(keys.getInt(1));
                            idx++;
                        }
                    }
                }

                // 4. Descontar cupos (todos de una vez)
                try (PreparedStatement ps = conn.prepareStatement(descontar)) {
                    ps.setInt(1, cantidad);
                    ps.setInt(2, sesionId);
                    ps.setInt(3, cantidad);
                    if (ps.executeUpdate() != 1) throw new SQLException("No fue posible descontar los cupos.");
                }

                conn.commit();
                return true;
            } catch (SQLException | RuntimeException ex) {
                try { conn.rollback(); } catch (SQLException rollbackEx) { ex.addSuppressed(rollbackEx); }
                System.err.println("No se pudo crear la reserva grupal: " + ex.getMessage());
                return false;
            } finally {
                try { conn.setAutoCommit(true); } catch (SQLException ignored) { }
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión al reservar grupo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Integer> obtenerEstudiantesConReservaActiva(int sesionId) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT DISTINCT estudiante_id FROM reservas "
                   + "WHERE sesion_id = ? AND estado IN ('CONFIRMADA','PROGRAMADA')";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sesionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    @Override
    public int cancelarReservasDeSesion(int sesionId) {
        String sql = "UPDATE reservas SET estado = 'CANCELADA' "
                   + "WHERE sesion_id = ? AND estado IN ('CONFIRMADA','PROGRAMADA')";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sesionId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Reserva> obtenerReservasPorSesion(int sesionId) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, u.nombre_completo as estudiante_nombre FROM reservas r "
                   + "JOIN usuarios u ON r.estudiante_id = u.id WHERE r.sesion_id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sesionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva r = new Reserva();
                    r.setId(rs.getInt("id"));
                    r.setEstudianteId(rs.getInt("estudiante_id"));
                    r.setEstudianteNombre(rs.getString("estudiante_nombre"));
                    r.setSesionId(rs.getInt("sesion_id"));
                    r.setEstado(rs.getString("estado"));
                    reservas.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public boolean verificarCruceHorarioEstudiante(int estudianteId, String fecha,
                                                   String horaInicio, String horaFin) {
        String sql = "SELECT COUNT(*) FROM reservas r JOIN sesiones s ON r.sesion_id = s.id "
                   + "WHERE r.estudiante_id = ? AND r.estado IN ('CONFIRMADA','PROGRAMADA') "
                   + "AND s.fecha = ? AND s.estado = 'PROGRAMADA' "
                   + "AND s.hora_inicio < ? AND s.hora_fin > ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, estudianteId);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(horaFin));
            ps.setTime(4, Time.valueOf(horaInicio));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cruce de horario: " + e.getMessage());
            return true; // Fallar de forma segura: no permitir reserva si no se pudo validar.
        }
    }

    @Override
    public boolean actualizarEstado(int reservaId, String estado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, reservaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void actualizar(Reserva reserva) {
        String sql = "UPDATE reservas SET estudiante_id=?, sesion_id=?, estado=? WHERE id=?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reserva.getEstudianteId());
            ps.setInt(2, reserva.getSesionId());
            ps.setString(3, reserva.getEstado());
            ps.setInt(4, reserva.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar la reserva: " + e.getMessage(), e);
        }
    }

    @Override
    public Reserva obtenerPorId(int id) {
        String sql = "SELECT * FROM reservas WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setId(rs.getInt("id"));
                    reserva.setEstudianteId(rs.getInt("estudiante_id"));
                    reserva.setSesionId(rs.getInt("sesion_id"));
                    reserva.setEstado(rs.getString("estado"));
                    return reserva;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reserva: " + e.getMessage());
        }
        return null;
    }
}