package studybuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sesion {

    private int id;
    private String asignatura;
    private String tema;
    private int cupoMaximo;
    private int cuposDisponibles;
    private String tipoSesion;
    private String modalidad;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String sede;
    private String estado;
    private int tutorId;
    private String tutorNombre;

    // ==========================================
    // CONSTRUCTORES
    // ==========================================

    public Sesion() {
    }

    public Sesion(
            String asignatura,
            String tema,
            int cupoMaximo,
            String tipoSesion,
            String modalidad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            String sede,
            int tutorId) {

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
        this.estado = EstadoSesion.PROGRAMADA.name();
    }

    // ==========================================
    // PATRON BUILDER
    // ==========================================

    public static class Builder {

        private String asignatura;
        private String tema;
        private int cupoMaximo;
        private String tipoSesion;
        private String modalidad;
        private LocalDate fecha;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private String sede;
        private int tutorId;

        public Builder conAsignatura(String asignatura) {
            this.asignatura = asignatura;
            return this;
        }

        public Builder conTema(String tema) {
            this.tema = tema;
            return this;
        }

        public Builder conCupoMaximo(int cupoMaximo) {
            this.cupoMaximo = cupoMaximo;
            return this;
        }

        public Builder conTipoSesion(String tipoSesion) {
            this.tipoSesion = tipoSesion;
            return this;
        }

        public Builder conModalidad(String modalidad) {
            this.modalidad = modalidad;
            return this;
        }

        public Builder conFecha(LocalDate fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder conHoraInicio(LocalTime horaInicio) {
            this.horaInicio = horaInicio;
            return this;
        }

        public Builder conHoraFin(LocalTime horaFin) {
            this.horaFin = horaFin;
            return this;
        }

        public Builder conSede(String sede) {
            this.sede = sede;
            return this;
        }

        public Builder conTutorId(int tutorId) {
            this.tutorId = tutorId;
            return this;
        }

        public Sesion construir() {

            return new Sesion(
                    asignatura,
                    tema,
                    cupoMaximo,
                    tipoSesion,
                    modalidad,
                    fecha,
                    horaInicio,
                    horaFin,
                    sede,
                    tutorId
            );
        }
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public String getTipoSesion() {
        return tipoSesion;
    }

    public void setTipoSesion(String tipoSesion) {
        this.tipoSesion = tipoSesion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean estaCompletada() {
        return EstadoSesion.COMPLETADA.name().equals(estado);
    }

    public boolean estaProgramada() {
        return EstadoSesion.PROGRAMADA.name().equals(estado);
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public String getTutorNombre() {
        return tutorNombre;
    }

    public void setTutorNombre(String tutorNombre) {
        this.tutorNombre = tutorNombre;
    }
}

