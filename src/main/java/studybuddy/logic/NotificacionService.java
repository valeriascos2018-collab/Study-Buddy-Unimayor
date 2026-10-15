/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.logic;

/**
 *
 * @author yoban
 */
import studybuddy.dao.NotificacionDAO;
import studybuddy.dao.ReservaDAO;
import studybuddy.model.Notificacion;
import studybuddy.model.Sesion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * HU-6: avisa a los estudiantes con reserva cuando el tutor
 * modifica o cancela una sesión.
 */
public class NotificacionService implements INotificadorSesiones, IBandejaNotificaciones {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final int LARGO_MAXIMO_MENSAJE = 500; // igual que la columna de la base de datos

    private final NotificacionDAO notificacionDAO;
    private final ReservaDAO reservaDAO;

    public NotificacionService(NotificacionDAO notificacionDAO, ReservaDAO reservaDAO) {
        this.notificacionDAO = notificacionDAO;
        this.reservaDAO = reservaDAO;
    }

    // ------------------------------------------------------------------
    // Envío (lo usa SesionService)
    // ------------------------------------------------------------------

    /**
     * Notifica la edición de una sesión. Solo avisa si cambió algo que le importa
     * al estudiante (asignatura, tema, modalidad, fecha, horario o sede).
     *
     * @param antes   la sesión tal como estaba antes de editar
     * @param despues la sesión ya con los cambios guardados
     * @return cuántos estudiantes fueron notificados
     */
    @Override
    public int notificarEdicion(Sesion antes, Sesion despues) {
        String cambios = describirCambios(antes, despues);
        if (cambios.isEmpty()) {
            return 0; // por ejemplo, solo cambió el cupo: el estudiante no necesita aviso
        }

        String tutor = nombreTutor(despues);
        String mensaje = "El tutor " + tutor + " modificó tu tutoría de "
                + despues.getAsignatura() + " (" + despues.getTema() + "). "
                + "Cambios: " + cambios + ". Revisa que todavía puedas asistir.";

        return enviarAReservasActivas(despues.getId(), Notificacion.TIPO_SESION_MODIFICADA, mensaje);
    }

    /**
     * Notifica la cancelación de una sesión a los estudiantes con reserva vigente.
     * Cancelar las reservas es responsabilidad de quien llama (SesionService), y debe
     * hacerse DESPUÉS de notificar, mientras las reservas siguen vigentes.
     *
     * @return cuántos estudiantes fueron notificados
     */
    @Override
    public int notificarCancelacion(Sesion sesion) {
        String mensaje = "Tu tutoría de " + sesion.getAsignatura() + " (" + sesion.getTema() + ") "
                + "del " + sesion.getFecha().format(FORMATO_FECHA)
                + " de " + sesion.getHoraInicio().format(FORMATO_HORA)
                + " a " + sesion.getHoraFin().format(FORMATO_HORA)
                + " en " + sesion.getSede()
                + " fue CANCELADA por el tutor " + nombreTutor(sesion)
                + ". Tu reserva quedó cancelada.";

        return enviarAReservasActivas(sesion.getId(), Notificacion.TIPO_SESION_CANCELADA, mensaje);
    }

    /** Cuántos estudiantes tienen reserva vigente en la sesión (para avisarle al tutor). */
    @Override
    public int contarEstudiantesConReserva(int sesionId) {
        return reservaDAO.obtenerEstudiantesConReservaActiva(sesionId).size();
    }

    // ------------------------------------------------------------------
    // Consulta (lo usa la pantalla de notificaciones y el menú)
    // ------------------------------------------------------------------

    @Override
    public List<Notificacion> obtenerNotificaciones(int usuarioId) {
        return notificacionDAO.obtenerPorUsuario(usuarioId);
    }

    @Override
    public int contarNoLeidas(int usuarioId) {
        return notificacionDAO.contarNoLeidas(usuarioId);
    }

    @Override
    public boolean marcarComoLeida(int notificacionId, int usuarioId) {
        return notificacionDAO.marcarComoLeida(notificacionId, usuarioId);
    }

    @Override
    public boolean marcarTodasComoLeidas(int usuarioId) {
        return notificacionDAO.marcarTodasComoLeidas(usuarioId);
    }

    // ------------------------------------------------------------------
    // Auxiliares
    // ------------------------------------------------------------------

    private int enviarAReservasActivas(int sesionId, String tipo, String mensaje) {
        List<Integer> estudiantes = reservaDAO.obtenerEstudiantesConReservaActiva(sesionId);
        if (estudiantes.isEmpty()) {
            return 0;
        }
        String texto = recortar(mensaje);
        List<Notificacion> lista = new ArrayList<>();
        for (Integer estudianteId : estudiantes) {
            lista.add(new Notificacion(estudianteId, sesionId, tipo, texto));
        }
        return notificacionDAO.crearVarias(lista) ? lista.size() : 0;
    }

    private String describirCambios(Sesion antes, Sesion despues) {
        List<String> cambios = new ArrayList<>();

        if (!Objects.equals(antes.getAsignatura(), despues.getAsignatura())) {
            cambios.add("asignatura: " + antes.getAsignatura() + " → " + despues.getAsignatura());
        }
        if (!Objects.equals(antes.getTema(), despues.getTema())) {
            cambios.add("tema: " + antes.getTema() + " → " + despues.getTema());
        }
        if (!Objects.equals(antes.getModalidad(), despues.getModalidad())) {
            cambios.add("modalidad: " + antes.getModalidad() + " → " + despues.getModalidad());
        }
        if (!Objects.equals(antes.getFecha(), despues.getFecha())) {
            cambios.add("fecha: " + antes.getFecha().format(FORMATO_FECHA)
                    + " → " + despues.getFecha().format(FORMATO_FECHA));
        }
        if (!mismaHora(antes.getHoraInicio(), despues.getHoraInicio())
                || !mismaHora(antes.getHoraFin(), despues.getHoraFin())) {
            cambios.add("horario: " + rango(antes) + " → " + rango(despues));
        }
        if (!Objects.equals(antes.getSede(), despues.getSede())) {
            cambios.add("sede: " + antes.getSede() + " → " + despues.getSede());
        }
        return String.join("; ", cambios);
    }

    // Se comparan solo hora y minuto para que los segundos sobrantes no cuenten como cambio
    private boolean mismaHora(LocalTime a, LocalTime b) {
        return a.truncatedTo(ChronoUnit.MINUTES).equals(b.truncatedTo(ChronoUnit.MINUTES));
    }

    private String rango(Sesion s) {
        return s.getHoraInicio().format(FORMATO_HORA) + " - " + s.getHoraFin().format(FORMATO_HORA);
    }

    private String nombreTutor(Sesion sesion) {
        String nombre = sesion.getTutorNombre();
        return (nombre == null || nombre.trim().isEmpty()) ? "responsable" : nombre;
    }

    private String recortar(String mensaje) {
        if (mensaje.length() <= LARGO_MAXIMO_MENSAJE) {
            return mensaje;
        }
        return mensaje.substring(0, LARGO_MAXIMO_MENSAJE - 3) + "...";
    }
}
