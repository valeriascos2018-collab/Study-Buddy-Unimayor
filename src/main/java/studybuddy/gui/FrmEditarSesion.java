/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.gui;

/**
 *
 * @author yoban
 */


import studybuddy.controller.EditarSesionController;
import studybuddy.model.Sesion;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class FrmEditarSesion extends JFrame {
    
    public JTextField txtAsignatura;
    public JTextField txtTema;
    public JTextField txtCupoMaximo;
    public JComboBox<String> cboModalidad;
    public JSpinner spnFecha;
    public JSpinner spnHoraInicio;
    public JSpinner spnHoraFin;
    public JComboBox<String> cboSede;
    public JButton btnGuardar;
    public JButton btnCancelar;
    
    private final Sesion sesionOriginal;

    public FrmEditarSesion(Sesion sesion) {
        this.sesionOriginal = sesion;
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Editar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setPreferredSize(new Dimension(0, 70));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel lblTitulo = new JLabel("Editar Sesión de Tutoría");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);
        Color fieldBg = new Color(250, 250, 250);
        
        // Campos (Nota: Asignatura y Tema editables, según HU-6)
        agregarLabel(panelFormulario, gbc, 0, 0, "Asignatura:", labelFont);
        txtAsignatura = new JTextField(25);
        agregarCampo(panelFormulario, gbc, 1, 0, txtAsignatura, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 1, "Tema:", labelFont);
        txtTema = new JTextField(25);
        agregarCampo(panelFormulario, gbc, 1, 1, txtTema, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 2, "Cupo Máximo:", labelFont);
        txtCupoMaximo = new JTextField(25);
        agregarCampo(panelFormulario, gbc, 1, 2, txtCupoMaximo, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 3, "Modalidad:", labelFont);
        cboModalidad = new JComboBox<>(new String[]{"Presencial", "Virtual"});
        agregarCampo(panelFormulario, gbc, 1, 3, cboModalidad, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 4, "Fecha:", labelFont);
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy"));
        agregarCampo(panelFormulario, gbc, 1, 4, spnFecha, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 5, "Hora Inicio:", labelFont);
        spnHoraInicio = new JSpinner(new SpinnerDateModel());
        spnHoraInicio.setEditor(new JSpinner.DateEditor(spnHoraInicio, "HH:mm"));
        agregarCampo(panelFormulario, gbc, 1, 5, spnHoraInicio, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 6, "Hora Fin:", labelFont);
        spnHoraFin = new JSpinner(new SpinnerDateModel());
        spnHoraFin.setEditor(new JSpinner.DateEditor(spnHoraFin, "HH:mm"));
        agregarCampo(panelFormulario, gbc, 1, 6, spnHoraFin, fieldFont, fieldBg);
        
        agregarLabel(panelFormulario, gbc, 0, 7, "Sede / Enlace:", labelFont);
        cboSede = new JComboBox<>(new String[]{"Bicentenario", "Casa Obando", "Encarnación", "Enlace Virtual"});
        agregarCampo(panelFormulario, gbc, 1, 7, cboSede, fieldFont, fieldBg);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        btnGuardar = new JButton("Guardar Cambios");
        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnGuardar, new Color(0, 102, 204), Color.WHITE);
        estilizarBoton(btnCancelar, new Color(240, 240, 240), new Color(80, 80, 80));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarDatos() {
        txtAsignatura.setText(sesionOriginal.getAsignatura());
        txtTema.setText(sesionOriginal.getTema());
        txtCupoMaximo.setText(String.valueOf(sesionOriginal.getCupoMaximo()));
        cboModalidad.setSelectedItem(sesionOriginal.getModalidad());
        spnFecha.setValue(java.util.Date.from(sesionOriginal.getFecha().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        spnHoraInicio.setValue(java.util.Date.from(sesionOriginal.getHoraInicio().atDate(LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant()));
        spnHoraFin.setValue(java.util.Date.from(sesionOriginal.getHoraFin().atDate(LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant()));
        cboSede.setSelectedItem(sesionOriginal.getSede());
    }
    
    private void agregarLabel(JPanel p, GridBagConstraints g, int x, int y, String t, Font f) {
        g.gridx = x; g.gridy = y; g.weightx = 0;
        JLabel l = new JLabel(t); l.setFont(f); l.setForeground(new Color(51,51,51)); p.add(l, g);
    }
    private void agregarCampo(JPanel p, GridBagConstraints g, int x, int y, JComponent c, Font f, Color bg) {
        g.gridx = x; g.gridy = y; g.weightx = 1.0;
        c.setFont(f);
        if (c instanceof JTextField || c instanceof JSpinner) {
            c.setBackground(bg);
            c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200,200,200), 1), BorderFactory.createEmptyBorder(6,8,6,8)));
        }
        c.setPreferredSize(new Dimension(0, 36));
        p.add(c, g);
    }
    private void estilizarBoton(JButton b, Color bg, Color fg) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13)); b.setBackground(bg); b.setForeground(fg);
        b.setFocusPainted(false); b.setBorderPainted(false); b.setPreferredSize(new Dimension(150, 40));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void setController(EditarSesionController controller) {
        this.btnGuardar.addActionListener(controller);
        this.btnCancelar.addActionListener(controller);
    }
    
    public Sesion getSesionOriginal() { return sesionOriginal; }
}
