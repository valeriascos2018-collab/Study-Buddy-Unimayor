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
        
        
        Date fechaDate = (Date) vista.spnFecha.getValue();
        LocalDate fecha = fechaDate.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();
        
        // ✅ CORRECTO: Obtener horas de los JSpinners (spnHoraInicio y spnHoraFin)
        Date horaInicioDate = (Date) vista.spnHoraInicio.getValue();
        LocalTime horaInicio = horaInicioDate.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalTime();
        
        Date horaFinDate = (Date) vista.spnHoraFin.getValue();
        LocalTime horaFin = horaFinDate.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalTime();
        
        String sede = (String) vista.cboSede.getSelectedItem();
        
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
            fecha, horaInicio, horaFin, sede, tutor.getId()
        );
        
        if (error == null) {
            JOptionPane.showMessageDialog(vista, 
                "Sesión publicada exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volverAlMenu();
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
        JOptionPane.showMessageDialog(vista, 
            "Error del sistema: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        }
    }
    
    private void volver() {
        vista.dispose();
        // Abrir menú principal del tutor
    }
}