/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.model;
import java.time.LocalDate;
/**
 *
 * @author yoban
 */
public class Reserva {
    private int id;
    private int estudianteId;
    private String estudianteNombre;
    private int sesionId;
    private String estado; // "PROGRAMADA", "ASISTIO", "NO_ASISTIO", "CANCELADA"

    public Reserva() {}

    public Reserva(int estudianteId, int sesionId) {
        this.estudianteId = estudianteId;
        this.sesionId = sesionId;
        this.estado = "PROGRAMADA";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }
    public int getSesionId() { return sesionId; }
    public void setSesionId(int sesionId) { this.sesionId = sesionId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
