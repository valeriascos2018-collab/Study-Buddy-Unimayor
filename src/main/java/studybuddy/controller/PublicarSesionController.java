package studybuddy.controller;

import studybuddy.gui.FrmPublicarSesion;
import studybuddy.logic.SesionService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import java.util.Date;


public class PublicarSesionController implements ActionListener {
    
    private final FrmPublicarSesion vista;
    private final SesionService service;
    private final int tutorId;
    
    public PublicarSesionController(FrmPublicarSesion vista, int tutorId) {
        this.vista = vista;
        this.service = new SesionService();
        this.tutorId = tutorId;
        
        this.vista.btnPublicar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnPublicar) {
            publicarSesion();
        } else if (e.getSource() == vista.btnCancelar) {
            volver();
        }
    }
    
   private void publicarSesion() {
    try {
        // Obtener datos de la vista
        String asignatura = (String) vista.cboAsignatura.getSelectedItem();
        String tema = vista.txtTema.getText().trim();
        
        // Validar que cupo máximo no esté vacío antes de parsear
        String cupoMaximoStr = vista.txtCupoMaximo.getText().trim();
        if (cupoMaximoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El cupo máximo es obligatorio", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int cupoMaximo = Integer.parseInt(cupoMaximoStr);
        String tipoSesion = (String) vista.cboTipoSesion.getSelectedItem();
        String modalidad = (String) vista.cboModalidad.getSelectedItem();

        // 1. Obtener y validar Fecha (Única declaración de 'fecha')
        Object valFecha = vista.spnFecha.getValue();
        if (!(valFecha instanceof Date)) {
            JOptionPane.showMessageDialog(vista, "Selecciona una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate fecha = ((Date) valFecha).toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();

        
        // 1. Obtener y convertir Hora Inicio
        Object valInicio = vista.spnHoraInicio.getValue();
        if (!(valInicio instanceof Date)) {
            JOptionPane.showMessageDialog(vista, "Selecciona una hora de inicio válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.util.Calendar calInicio = java.util.Calendar.getInstance();
        calInicio.setTime((Date) valInicio);
        LocalTime horaInicio = LocalTime.of(
            calInicio.get(java.util.Calendar.HOUR_OF_DAY),
            calInicio.get(java.util.Calendar.MINUTE),
            calInicio.get(java.util.Calendar.SECOND)
        );

// 2. Obtener y convertir Hora Fin
        Object valFin = vista.spnHoraFin.getValue();
        if (!(valFin instanceof Date)) {
            JOptionPane.showMessageDialog(vista, "Selecciona una hora de fin válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.util.Calendar calFin = java.util.Calendar.getInstance();
        calFin.setTime((Date) valFin);
        LocalTime horaFin = LocalTime.of(
            calFin.get(java.util.Calendar.HOUR_OF_DAY),
            calFin.get(java.util.Calendar.MINUTE),
            calFin.get(java.util.Calendar.SECOND)
            );

        // 4. Obtención segura de Combos
        Object sedeObj = vista.cboSede.getSelectedItem();
        String sede = (sedeObj != null) ? sedeObj.toString() : "";
        
        // Validaciones HU-4
        if (asignatura == null || tema.isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "Debes completar todos los campos obligatorios (asignatura, tema, cupo máximo)", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (cupoMaximo <= 0) {
            JOptionPane.showMessageDialog(vista, 
                "El cupo máximo debe ser mayor a cero", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validaciones HU-5
        if (!horaFin.isAfter(horaInicio)) {
            JOptionPane.showMessageDialog(vista, 
                "El horario ingresado no es válido (la hora de fin debe ser posterior a la de inicio)", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Llamar al service
        String error = service.publicarSesion(
            asignatura, tema, cupoMaximo, tipoSesion, modalidad,
            fecha, horaInicio, horaFin, sede, tutorId 
        );
        
        if (error == null) {
            JOptionPane.showMessageDialog(vista, 
                "Sesión publicada exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        } else {
            JOptionPane.showMessageDialog(vista, 
                error, 
                "Error de publicación", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(vista, 
            "El cupo máximo debe ser un número válido", 
            "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        String mensajeError = (ex.getMessage() != null) ? ex.getMessage() : ex.toString();
        JOptionPane.showMessageDialog(vista, 
            "Error del sistema: " + mensajeError, 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
    
    private void volver() {
        vista.dispose();
        // Abrir menú principal del tutor
    }
}