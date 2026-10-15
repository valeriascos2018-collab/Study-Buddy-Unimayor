/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.model;

/**
 *
 * @author yoban
 */
import java.time.LocalDateTime;

/**
 * Aviso que recibe un estudiante cuando el tutor modifica o cancela
 * una sesión en la que tiene una reserva.
 */
public class Notificacion {

    public static final String TIPO_SESION_MODIFICADA = "SESION_MODIFICADA";
    public static final String TIPO_SESION_CANCELADA = "SESION_CANCELADA";

    private int id;
    private int usuarioId;
    private Integer sesionId; // puede ser nulo si la sesión se elimina
    private String tipo;
    private String mensaje;
    private boolean leida;
    private LocalDateTime fechaCreacion;

    public Notificacion() {}

    public Notificacion(int usuarioId, Integer sesionId, String tipo, String mensaje) {
        this.usuarioId = usuarioId;
        this.sesionId = sesionId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.leida = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public Integer getSesionId() { return sesionId; }
    public void setSesionId(Integer sesionId) { this.sesionId = sesionId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}