/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.model;

/**
 *
 * @author yoban
 */
public enum Sede {
    BICENTENARIO("Bicentenario", true),
    CASA_OBANDO("Casa Obando", true),
    ENCARNACION("Encarnación", true),
    VIRTUAL("Virtual", false);

    private final String nombre;
    private final boolean presencial;

    Sede(String nombre, boolean presencial) {
        this.nombre = nombre;
        this.presencial = presencial;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPresencial() {
        return presencial;
    }

    /** Nombres de todas las sedes, en el orden en que se muestran en los combos. */
    public static String[] nombres() {
        Sede[] todas = values();
        String[] nombres = new String[todas.length];
        for (int i = 0; i < todas.length; i++) {
            nombres[i] = todas[i].nombre;
        }
        return nombres;
    }

    /** true si el texto coincide exactamente con el nombre de una sede presencial. */
    public static boolean esPresencialValida(String sede) {
        for (Sede s : values()) {
            if (s.presencial && s.nombre.equals(sede)) {
                return true;
            }
        }
        return false;
    }
}

