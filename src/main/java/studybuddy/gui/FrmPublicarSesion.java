package studybuddy.gui;

import studybuddy.controller.PublicarSesionController;
import javax.swing.*;
import java.awt.*;

public class FrmPublicarSesion extends JFrame {
    
    public JComboBox<String> cboAsignatura;
    public JTextField txtTema;
    public JTextField txtCupoMaximo;
    public JComboBox<String> cboTipoSesion;
    public JComboBox<String> cboModalidad;
    public JSpinner spnFecha;
    public JSpinner spnHoraInicio;
    public JSpinner spnHoraFin;
    public JComboBox<String> cboSede;
    public JButton btnPublicar;
    public JButton btnCancelar;
    
    public FrmPublicarSesion() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Publicar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setPreferredSize(new Dimension(0, 70));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel lblTitulo = new JLabel("Publicar Nueva Sesión de Tutoría");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        
        // Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);
        Color fieldBg = new Color(250, 250, 250);
        
        // Asignatura
        agregarLabel(panelFormulario, gbc, 0, 0, "Asignatura:", labelFont);
        cboAsignatura = new JComboBox<>(new String[]{"Cálculo I", "Cálculo II", "Programación I", "Física Mecánica", "Álgebra Lineal"});
        agregarCampo(panelFormulario, gbc, 1, 0, cboAsignatura, fieldFont, fieldBg);
        
        // Tema
        agregarLabel(panelFormulario, gbc, 0, 1, "Tema específico:", labelFont);
        txtTema = new JTextField(25);
        agregarCampo(panelFormulario, gbc, 1, 1, txtTema, fieldFont, fieldBg);
        
        // Cupo Máximo
        agregarLabel(panelFormulario, gbc, 0, 2, "Cupo Máximo:", labelFont);
        txtCupoMaximo = new JTextField(25);
        agregarCampo(panelFormulario, gbc, 1, 2, txtCupoMaximo, fieldFont, fieldBg);
        
        // Tipo de Sesión
        agregarLabel(panelFormulario, gbc, 0, 3, "Tipo de Sesión:", labelFont);
        cboTipoSesion = new JComboBox<>(new String[]{"Individual", "Grupal"});
        agregarCampo(panelFormulario, gbc, 1, 3, cboTipoSesion, fieldFont, fieldBg);
        
        // Modalidad
        agregarLabel(panelFormulario, gbc, 0, 4, "Modalidad:", labelFont);
        cboModalidad = new JComboBox<>(new String[]{"Presencial", "Virtual"});
        agregarCampo(panelFormulario, gbc, 1, 4, cboModalidad, fieldFont, fieldBg);
        
        // Fecha
        agregarLabel(panelFormulario, gbc, 0, 5, "Fecha:", labelFont);
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(dateEditor);
        agregarCampo(panelFormulario, gbc, 1, 5, spnFecha, fieldFont, fieldBg);
        
        // Hora Inicio
        agregarLabel(panelFormulario, gbc, 0, 6, "Hora Inicio:", labelFont);
        spnHoraInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(spnHoraInicio, "HH:mm");
        spnHoraInicio.setEditor(timeEditorInicio);
        agregarCampo(panelFormulario, gbc, 1, 6, spnHoraInicio, fieldFont, fieldBg);
        
        // Hora Fin
        agregarLabel(panelFormulario, gbc, 0, 7, "Hora Fin:", labelFont);
        spnHoraFin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorFin = new JSpinner.DateEditor(spnHoraFin, "HH:mm");
        spnHoraFin.setEditor(timeEditorFin);
        agregarCampo(panelFormulario, gbc, 1, 7, spnHoraFin, fieldFont, fieldBg);
        
        // Sede
        agregarLabel(panelFormulario, gbc, 0, 8, "Sede / Enlace:", labelFont);
        cboSede = new JComboBox<>(new String[]{"Bicentenario", "Casa Obando", "Encarnación", "Enlace Virtual (Teams/Meet)"});
        agregarCampo(panelFormulario, gbc, 1, 8, cboSede, fieldFont, fieldBg);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        btnPublicar = crearBoton("Publicar Sesión", new Color(0, 102, 204), Color.WHITE);
        btnCancelar = crearBoton("Cancelar", new Color(240, 240, 240), new Color(80, 80, 80));
        panelBotones.add(btnPublicar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    // Métodos auxiliares para mantener el código limpio
    private void agregarLabel(JPanel panel, GridBagConstraints gbc, int x, int y, String texto, Font font) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 0;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(font);
        lbl.setForeground(new Color(51, 51, 51));
        panel.add(lbl, gbc);
    }
    
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int x, int y, JComponent campo, Font font, Color bg) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 1.0;
        campo.setFont(font);
        if (campo instanceof JTextField || campo instanceof JSpinner) {
            campo.setBackground(bg);
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
            ));
        }
        campo.setPreferredSize(new Dimension(0, 36));
        panel.add(campo, gbc);
    }
    
    private JButton crearBoton(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    public void setController(PublicarSesionController controller) {
        this.btnPublicar.addActionListener(controller);
        this.btnCancelar.addActionListener(controller);
    }
}