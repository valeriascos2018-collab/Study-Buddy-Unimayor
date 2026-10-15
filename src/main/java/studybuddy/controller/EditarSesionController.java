/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.controller;

/**
 *
 * @author yoban
 */

import studybuddy.gui.FrmEditarSesion;
import studybuddy.gui.FrmMenuPrincipal;
import studybuddy.logic.ISesionService;
import studybuddy.model.Sesion;
import studybuddy.model.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;

public class EditarSesionController implements ActionListener {
    private final FrmEditarSesion vista;
    private final ISesionService service;
    private final Usuario usuario; // AGREGADO

    // CORREGIDO: Se añade el usuario al constructor
    public EditarSesionController(FrmEditarSesion vista, ISesionService service, Usuario usuario) {
        this.vista = vista;
        this.service = service;
        this.usuario = usuario;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            guardarCambios();
        } else if (e.getSource() == vista.btnCancelar) {
            vista.dispose();
        }
    }

    private void guardarCambios() {
        try {
            int id = vista.getSesionOriginal().getId();
            String asignatura = vista.txtAsignatura.getText().trim();
            String tema = vista.txtTema.getText().trim();
            int cupo = Integer.parseInt(vista.txtCupoMaximo.getText().trim());
            String modalidad = (String) vista.cboModalidad.getSelectedItem();
            LocalDate fecha = ((Date) vista.spnFecha.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            // CORREGIDO: Forzar segundos y nanosegundos a 0 para evitar conflictos en la BD
            LocalTime hInicio = ((Date) vista.spnHoraInicio.getValue()).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
            LocalTime hFin = ((Date) vista.spnHoraFin.getValue()).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
            
            String sede = (String) vista.cboSede.getSelectedItem();

            String error = service.editarSesion(id, asignatura, tema, cupo, fecha, hInicio, hFin, sede);

            if (error == null) {
                JOptionPane.showMessageDialog(vista, "Sesión actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
                // CORREGIDO: Reabrir el menú pasando el objeto usuario
                new FrmMenuPrincipal(usuario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Verifica los datos ingresados", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}