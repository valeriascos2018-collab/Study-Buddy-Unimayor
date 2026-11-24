/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.dao.impl;

import studybuddy.dao.SesionDAO;
import studybuddy.model.Sesion;
import studybuddy.config.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class SesionDAOImpl implements SesionDAO {

    @Override
    public boolean crearSesion(Sesion sesion) {

        String sql = "INSERT INTO sesiones " +
                "(asignatura, tema, cupo_maximo, cupos_disponibles, " +
                "tipo_sesion, modalidad, fecha, hora_inicio, hora_fin, " +
                "sede, estado, tutor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

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

            int filas = ps.executeUpdate();

            if (filas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        sesion.setId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear sesión: " + e.getMessage());
        }

        return false;
    }

    // ==========================================
    // ACTUALIZAR SESION
    // ==========================================

    @Override
    public boolean actualizarSesion(Sesion sesion) {

        String sql = "UPDATE sesiones SET " +
                "asignatura=?, tema=?, cupo_maximo=?, " +
                "tipo_sesion=?, modalidad=?, fecha=?, " +
                "hora_inicio=?, hora_fin=?, sede=?, estado=? " +
                "WHERE id=?";

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
            System.err.println("Error al actualizar sesión: " + e.getMessage());
        }

        return false;
    }

    // ==========================================
    // CANCELAR SESION
    // ==========================================

    @Override
    public boolean cancelarSesion(int id) {

        String sql = "UPDATE sesiones SET estado='CANCELADA' WHERE id=?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al cancelar sesión: " + e.getMessage());
        }

        return false;
    }

    // ==========================================
    // OBTENER SESION POR ID
    // ==========================================

    @Override
    public Sesion obtenerSesionPorId(int id) {

        String sql = "SELECT s.*, u.nombre_completo AS tutor_nombre " +
                "FROM sesiones s " +
                "JOIN usuarios u ON s.tutor_id = u.id " +
                "WHERE s.id=?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearSesion(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener sesión: " + e.getMessage());
        }

        return null;
    }

    // ==========================================
    // OBTENER SESIONES POR TUTOR
    // ==========================================

    @Override
    public List<Sesion> obtenerSesionesPorTutor(int tutorId) {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT s.*, u.nombre_completo AS tutor_nombre " +
                "FROM sesiones s " +
                "JOIN usuarios u ON s.tutor_id = u.id " +
                "WHERE s.tutor_id=? " +
                "ORDER BY s.fecha, s.hora_inicio";

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

    // ==========================================
    // OBTENER TODAS LAS SESIONES DISPONIBLES
    // ==========================================

    @Override
    public List<Sesion> obtenerTodasLasSesionesDisponibles() {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT s.*, u.nombre_completo AS tutor_nombre " +
                "FROM sesiones s " +
                "JOIN usuarios u ON s.tutor_id = u.id " +
                "WHERE s.estado='PROGRAMADA' " +
                "AND s.cupos_disponibles > 0 " +
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

    // ==========================================
    // BUSCAR SESIONES
    // ==========================================

    @Override
    public List<Sesion> buscarSesiones(
            String asignatura,
            String sede,
            String modalidad) {

        List<Sesion> sesiones = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT s.*, u.nombre_completo AS tutor_nombre " +
                "FROM sesiones s " +
                "JOIN usuarios u ON s.tutor_id = u.id " +
                "WHERE s.estado='PROGRAMADA' " +
                "AND s.cupos_disponibles > 0"
        );

        if (asignatura != null && !asignatura.trim().isEmpty()) {
            sql.append(" AND s.asignatura LIKE ?");
        }

        if (sede != null && !sede.trim().isEmpty()) {
            sql.append(" AND s.sede = ?");
        }

        if (modalidad != null && !modalidad.trim().isEmpty()) {
            sql.append(" AND s.modalidad = ?");
        }

        sql.append(" ORDER BY s.fecha, s.hora_inicio");

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int indice = 1;

            if (asignatura != null && !asignatura.trim().isEmpty()) {
                ps.setString(indice++, "%" + asignatura + "%");
            }

            if (sede != null && !sede.trim().isEmpty()) {
                ps.setString(indice++, sede);
            }

            if (modalidad != null && !modalidad.trim().isEmpty()) {
                ps.setString(indice++, modalidad);
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

    // ==========================================
    // ACTUALIZAR SESIONES PASADAS
    // ==========================================

    @Override
    public void actualizarSesionesPasadas() {

        String sql = "UPDATE sesiones " +
                "SET estado='COMPLETADA' " +
                "WHERE estado='PROGRAMADA' " +
                "AND fecha < CURRENT_DATE";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println(
                        "Se actualizaron " + filas +
                        " sesiones pasadas a COMPLETADA."
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar sesiones pasadas: " + e.getMessage());
        }
    }

    // ==========================================
    // VERIFICAR CRUCE DE HORARIOS
    // ==========================================

    @Override
    public boolean verificarCruceHorario(
            int tutorId,
            String fecha,
            String horaInicio,
            String horaFin) {

        String sql = "SELECT COUNT(*) FROM sesiones " +
                "WHERE tutor_id=? " +
                "AND fecha=? " +
                "AND estado='PROGRAMADA' " +
                "AND hora_inicio < ? " +
                "AND hora_fin > ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tutorId);
            ps.setDate(2, Date.valueOf(LocalDate.parse(fecha)));
            ps.setTime(3, Time.valueOf(LocalTime.parse(horaFin)));
            ps.setTime(4, Time.valueOf(LocalTime.parse(horaInicio)));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar horario: " + e.getMessage());
        }

        return false;
    }

    // ==========================================
    // BUSCAR SESIONES POR USUARIO Y RANGO
    // ==========================================

    @Override
    public List<Sesion> buscarSesionesPorUsuarioYRango(
            int idUsuario,
            LocalDateTime inicio,
            LocalDateTime fin) {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT s.*, u.nombre_completo AS tutor_nombre " +
                "FROM sesiones s " +
                "JOIN usuarios u ON s.tutor_id = u.id " +
                "WHERE s.tutor_id=? " +
                "AND (s.fecha + s.hora_inicio) >= ? " +
                "AND (s.fecha + s.hora_inicio) <= ? " +
                "ORDER BY s.fecha, s.hora_inicio";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar sesiones por rango: " + e.getMessage());
        }

        return sesiones;
    }

    // ==========================================
    // CALCULAR HORAS COMPLETADAS
    // ==========================================

    @Override
    public double calcularHorasCompletadas(
            int idUsuario,
            LocalDateTime inicio,
            LocalDateTime fin) {

        String sql = "SELECT COALESCE(" +
                "SUM(EXTRACT(EPOCH FROM (hora_fin - hora_inicio)) / 3600.0), 0) " +
                "FROM sesiones " +
                "WHERE tutor_id=? " +
                "AND estado='COMPLETADA' " +
                "AND (fecha + hora_inicio) >= ? " +
                "AND (fecha + hora_inicio) <= ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al calcular horas completadas: " + e.getMessage());
        }

        return 0.0;
    }

    // ==========================================
    // CONTAR SESIONES COMPLETADAS
    // ==========================================

    @Override
    public int contarSesionesCompletadas(
            int idUsuario,
            LocalDateTime inicio,
            LocalDateTime fin) {

        String sql = "SELECT COUNT(*) " +
                "FROM sesiones " +
                "WHERE tutor_id=? " +
                "AND estado='COMPLETADA' " +
                "AND (fecha + hora_inicio) >= ? " +
                "AND (fecha + hora_inicio) <= ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al contar sesiones completadas: " + e.getMessage());
        }

        return 0;
    }

    // ==========================================
    // MAPEAR RESULTSET A OBJETO SESION
    // ==========================================

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

        sesion.setHoraInicio(
                rs.getTime("hora_inicio").toLocalTime()
        );

        sesion.setHoraFin(
                rs.getTime("hora_fin").toLocalTime()
        );

        sesion.setSede(rs.getString("sede"));
        sesion.setEstado(rs.getString("estado"));
        sesion.setTutorId(rs.getInt("tutor_id"));

        String tutorNombre = rs.getString("tutor_nombre");

        sesion.setTutorNombre(
                tutorNombre != null ? tutorNombre : "Desconocido"
        );

        return sesion;
    }
}
