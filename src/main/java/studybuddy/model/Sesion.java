package studybuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sesion {
    private int id;
    private String asignatura;
    private String tema;
    private int cupoMaximo;
    private int cuposDisponibles;
    private String tipoSesion; // "INDIVIDUAL" o "GRUPAL"
    private String modalidad; // "PRESENCIAL" o "VIRTUAL"
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String sede; // "Bicentenario", "Casa Obando", "Encarnación" o enlace virtual
    private String estado; // "PROGRAMADA", "CANCELADA", "COMPLETADA"
    private int tutorId;
    private String tutorNombre;
    
    // Constructores
    public Sesion() {}
    
    public Sesion(String asignatura, String tema, int cupoMaximo, String tipoSesion, 
                  String modalidad, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, 
                  String sede, int tutorId) {
        this.asignatura = asignatura;
        this.tema = tema;
        this.cupoMaximo = cupoMaximo;
        this.cuposDisponibles = cupoMaximo;
        this.tipoSesion = tipoSesion;
        this.modalidad = modalidad;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.sede = sede;
        this.tutorId = tutorId;
        this.estado = "PROGRAMADA";
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }
    
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    
    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    
    public int getCuposDisponibles() { return cuposDisponibles; }
    public void setCuposDisponibles(int cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }
    
    public String getTipoSesion() { return tipoSesion; }
    public void setTipoSesion(String tipoSesion) { this.tipoSesion = tipoSesion; }
    
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    
    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }
    
    public String getTutorNombre() { return tutorNombre; }
    public void setTutorNombre(String tutorNombre) { this.tutorNombre = tutorNombre; }
}
