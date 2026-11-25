/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

/**
 *
 * @author yoban
 */
import studybuddy.gui.FrmReporteHoras;
import studybuddy.logic.IReporteService;
import studybuddy.model.Rol;
import studybuddy.model.Usuario;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class ReporteController implements ActionListener {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FrmReporteHoras vista;
    private final IReporteService reporteService;
    private final Usuario usuario;

    public ReporteController(FrmReporteHoras vista, IReporteService reporteService, Usuario usuario) {
        this.vista = vista; this.reporteService = reporteService; this.usuario = usuario;
        vista.btnGenerar.addActionListener(this);
        vista.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGenerar) generarReporte();
        else if (e.getSource() == vista.btnVolver) vista.dispose();
    }

    private void generarReporte() {
        try {
            LocalDate ini = LocalDate.parse(vista.txtFechaInicio.getText().trim(), FMT);
            LocalDate fin = LocalDate.parse(vista.txtFechaFin.getText().trim(), FMT);
            LocalDateTime inicio = ini.atStartOfDay();
            LocalDateTime finDia = fin.atTime(23, 59, 59);

            int idTutor;
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                String sel = (String) vista.cmbTutores.getSelectedItem();
                if (sel == null) throw new IllegalArgumentException("Seleccione un tutor/monitor.");
                idTutor = Integer.parseInt(sel.split(" - ")[0]);
            } else {
                idTutor = usuario.getId(); // el tutor se reporta a sí mismo
            }

            Map<String, Object> r = reporteService.generarReporteHoras(idTutor, inicio, finDia);

            // HU-11 CA2: sin datos en el periodo
            if (Boolean.TRUE.equals(r.get("sinDatos"))) {
                vista.txtResultado.setText("");
                JOptionPane.showMessageDialog(vista,
                    "No hay datos de horas impartidas en el periodo seleccionado",
                    "Reporte de horas", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // HU-11 CA1: reporte exitoso
            vista.txtResultado.setText(String.format(
                "Horas totales impartidas: %s%nSesiones completadas: %s%nAsistencias: %s%nInasistencias: %s%nPeriodo: %s a %s",
                r.get("totalHoras"), r.get("totalSesiones"),
                r.get("totalAsistencias"), r.get("totalInasistencias"), ini, fin));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, "Fechas inválidas. Use el formato dd/MM/yyyy",
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
