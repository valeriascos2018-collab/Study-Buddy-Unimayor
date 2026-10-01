package studybuddy.gui;

import studybuddy.controller.CatalogoTutoriasController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmCatalogoTutorias extends JFrame {
    
    public JComboBox<String> cboFiltroAsignatura;
    public JComboBox<String> cboFiltroSede;
    public JComboBox<String> cboFiltroModalidad;
    public JButton btnBuscar;
    public JButton btnLimpiar;
    
    public JTable tblResultados;
    public DefaultTableModel modeloTabla;
    public JButton btnReservar;
    public JButton btnVolver;
    
    public FrmCatalogoTutorias() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Catálogo de Tutorías");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setPreferredSize(new Dimension(0, 70));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel lblTitulo = new JLabel("Catálogo de Tutorías Disponibles");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        
        // Panel de Filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(crearLabel("Asignatura:", labelFont), gbc);
        cboFiltroAsignatura = new JComboBox<>(new String[]{"Todas", "Cálculo I", "Cálculo II", "Programación I", "Física Mecánica"});
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFiltros.add(crearCombo(cboFiltroAsignatura, fieldFont), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        panelFiltros.add(crearLabel("Sede:", labelFont), gbc);
        cboFiltroSede = new JComboBox<>(new String[]{"Todas", "Bicentenario", "Casa Obando", "Encarnación", "Virtual"});
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFiltros.add(crearCombo(cboFiltroSede, fieldFont), gbc);
        
        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0;
        panelFiltros.add(crearLabel("Modalidad:", labelFont), gbc);
        cboFiltroModalidad = new JComboBox<>(new String[]{"Todas", "Presencial", "Virtual"});
        gbc.gridx = 5; gbc.gridy = 0; gbc.weightx = 1.0;
        panelFiltros.add(crearCombo(cboFiltroModalidad, fieldFont), gbc);
        
        gbc.gridx = 6; gbc.gridy = 0; gbc.weightx = 0;
        btnBuscar = crearBoton("Buscar", new Color(0, 102, 204), Color.WHITE);
        btnBuscar.setPreferredSize(new Dimension(100, 34));
        panelFiltros.add(btnBuscar, gbc);
        
        gbc.gridx = 7; gbc.gridy = 0; gbc.weightx = 0;
        btnLimpiar = crearBoton("Limpiar", new Color(240, 240, 240), new Color(80, 80, 80));
        btnLimpiar.setPreferredSize(new Dimension(100, 34));
        panelFiltros.add(btnLimpiar, gbc);
        
        panelPrincipal.add(panelFiltros, BorderLayout.NORTH);
        
        // Tabla de Resultados
        String[] columnas = {"Asignatura", "Tema", "Tutor", "Fecha", "Hora", "Modalidad", "Sede", "Cupos Disp."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblResultados.setRowHeight(30);
        tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblResultados.getTableHeader().setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(tblResultados);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        
        // Botones Inferiores
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        btnReservar = crearBoton("Reservar Cupo", new Color(40, 167, 69), Color.WHITE); // Verde
        btnVolver = crearBoton("Volver", new Color(240, 240, 240), new Color(80, 80, 80));
        
        panelBotones.add(btnReservar);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JLabel crearLabel(String texto, Font font) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(font);
        lbl.setForeground(new Color(51, 51, 51));
        return lbl;
    }
    
    private JComboBox<String> crearCombo(JComboBox<String> combo, Font font) {
        combo.setFont(font);
        combo.setPreferredSize(new Dimension(0, 34));
        return combo;
    }
    
    private JButton crearBoton(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    public void setController(CatalogoTutoriasController controller) {
        this.btnBuscar.addActionListener(controller);
        this.btnLimpiar.addActionListener(controller);
        this.btnReservar.addActionListener(controller);
        this.btnVolver.addActionListener(controller);
    }
}