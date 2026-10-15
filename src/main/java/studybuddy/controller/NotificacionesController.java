/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

/**
 *
 * @author yoban
 */
import studybuddy.gui.FrmMenuPrincipal;
import studybuddy.gui.FrmNotificaciones;
import studybuddy.logic.NotificacionService;
import studybuddy.model.Notificacion;
import studybuddy.model.Usuario;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de la pantalla de notificaciones.
 */
public class NotificacionesController implements ActionListener {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final FrmNotificaciones vista;
    private final NotificacionService service;
    private final Usuario usuario;
    private List<Notificacion> notificaciones = new ArrayList<>();

    public NotificacionesController(FrmNotificaciones vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.service = new NotificacionService();

        this.vista.btnMarcarTodas.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
        this.vista.tblNotificaciones.getSelectionModel()
                .addListSelectionListener(this::alSeleccionarFila);

        // Tanto el botón "Volver" como la X de la ventana terminan aquí y reabren el menú
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new FrmMenuPrincipal(usuario).setVisible(true);
            }
        });

        cargarNotificaciones();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnMarcarTodas) {
            service.marcarTodasComoLeidas(usuario.getId());
            cargarNotificaciones();
        } else if (e.getSource() == vista.btnVolver) {
            vista.dispose();
        }
    }

    private void cargarNotificaciones() {
        notificaciones = service.obtenerNotificaciones(usuario.getId());
        vista.modeloTabla.setRowCount(0);

        for (Notificacion n : notificaciones) {
            vista.modeloTabla.addRow(new Object[]{
                n.isLeida() ? "Leída" : "● Nueva",
                n.getFechaCreacion() != null ? n.getFechaCreacion().format(FORMATO) : "",
                nombreDelTipo(n.getTipo()),
                n.getMensaje()
            });
        }
        vista.txtDetalle.setText(notificaciones.isEmpty()
                ? "No tienes notificaciones por ahora."
                : "Selecciona una notificación para leerla completa.");
        actualizarResumen();
    }

    private void alSeleccionarFila(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        int fila = vista.tblNotificaciones.getSelectedRow();
        if (fila < 0 || fila >= notificaciones.size()) {
            return;
        }
        Notificacion n = notificaciones.get(fila);
        vista.txtDetalle.setText(n.getMensaje());
        vista.txtDetalle.setCaretPosition(0);

        if (!n.isLeida() && service.marcarComoLeida(n.getId(), usuario.getId())) {
            n.setLeida(true);
            vista.modeloTabla.setValueAt("Leída", fila, 0);
            actualizarResumen();
        }
    }

    private void actualizarResumen() {
        long nuevas = notificaciones.stream().filter(n -> !n.isLeida()).count();
        vista.lblResumen.setText(notificaciones.size() + " en total, " + nuevas + " sin leer");
    }

    private String nombreDelTipo(String tipo) {
        if (Notificacion.TIPO_SESION_CANCELADA.equals(tipo)) {
            return "Sesión cancelada";
        }
        if (Notificacion.TIPO_SESION_MODIFICADA.equals(tipo)) {
            return "Sesión modificada";
        }
        return tipo;
    }
}
