package studybuddy.controller;

import studybuddy.gui.FrmPublicarSesion;
import studybuddy.logic.SesionService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;

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
        // Obtener datos de la vista
        String asignatura = (String) vista.cboAsignatura.getSelectedItem();
        String tema = vista.txtTema.getText().trim();
        int cupoMaximo = Integer.parseInt(vista.txtCupoMaximo.getText());
        String tipoSesion = (String) vista.cboTipoSesion.getSelectedItem();
        String modalidad = (String) vista.cboModalidad.getSelectedItem();
        LocalDate fecha = vista.dateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalTime horaInicio = vista.spinnerHoraInicio.getValue() != null ? 
            LocalTime.parse(vista.spinnerHoraInicio.getValue().toString()) : null;
        LocalTime horaFin = vista.spinnerHoraFin.getValue() != null ? 
            LocalTime.parse(vista.spinnerHoraFin.getValue().toString()) : null;
        String sede = (String) vista.cboSede.getSelectedItem();
        
        // Llamar al service
        String error = service.publicarSesion(asignatura, tema, cupoMaximo, tipoSesion,
                                              modalidad, fecha, horaInicio, horaFin, sede, tutorId);
        
        if (error == null) {
            JOptionPane.showMessageDialog(vista, "Sesión publicada exitosamente", 
                                         "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        } else {
            JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volver() {
        vista.dispose();
        // Abrir menú principal del tutor
    }
}