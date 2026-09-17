package studybuddy.model;

import java.time.LocalDateTime;

/**
 * Modelo de dominio para representar un usuario del sistema.
 * Sigue principios de encapsulamiento e inmutabilidad relativa.
 */
public class Usuario {
    
    private int id;
    private String nombreCompleto;
    private String correo;
    private String contraseña;
    private String facultad;
    private String programaAcademico;
    private Rol rol;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    /**
     * Constructor completo.
     */
    public Usuario(int id, String nombreCompleto, String correo, String contraseña, 
                   String facultad, String programaAcademico, Rol rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.contraseña = contraseña;
        this.facultad = facultad;
        this.programaAcademico = programaAcademico;
        this.rol = rol;
    }
    
    /**
     * Constructor para registro (sin ID ni fechas).
     */
    public Usuario(String nombreCompleto, String correo, String contraseña, 
                   String facultad, String programaAcademico, Rol rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.contraseña = contraseña;
        this.facultad = facultad;
        this.programaAcademico = programaAcademico;
        this.rol = rol;
    }
    
    // Getters y Setters
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
    
    public String getProgramaAcademico() { return programaAcademico; }
    public void setProgramaAcademico(String programaAcademico) { this.programaAcademico = programaAcademico; }
    
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    
    @Override
    public String toString() {
        return "Usuario{" + "correo=" + correo + ", rol=" + rol + '}';
    }
}