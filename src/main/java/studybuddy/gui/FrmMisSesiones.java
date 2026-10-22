package studybuddy.gui;

import studybuddy.controller.MisSesionesController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmMisSesiones extends JFrame {
    
    public JTable tblSesiones;
    public DefaultTableModel modeloTabla;
    
    // 1. Agregamos la declaración del nuevo botón
    public JButton btnCompletar; 
    public JButton btnEditar;
    public JButton btnCancelar;
    public JButton btnVolver;
    
    public FrmMisSesiones() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Study Buddy Unimayor - Mis Sesiones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setPreferredSize(new Dimension(0, 70));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel lblTitulo = new JLabel("Gestión de Mis Sesiones de Tutoría");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Asignatura", "Tema", "Fecha", "Hora", "Modalidad", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };
        tblSesiones = new JTable(modeloTabla);
        tblSesiones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblSesiones.setRowHeight(30);
        tblSesiones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblSesiones.getTableHeader().setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(tblSesiones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        // 2. Creamos el nuevo botón (Color verde para indicar acción positiva/completada)
        btnCompletar = crearBoton("Marcar como Completada", new Color(40, 167, 69), Color.WHITE);
        
        btnEditar = crearBoton("Editar Sesión", new Color(0, 102, 204), Color.WHITE);
        btnCancelar = crearBoton("Cancelar Sesión", new Color(220, 53, 69), Color.WHITE);
        btnVolver = crearBoton("Volver", new Color(240, 240, 240), new Color(80, 80, 80));
        
        // 3. Lo agregamos al panel (antes de editar, para que tenga sentido el flujo)
        panelBotones.add(btnCompletar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVolver);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 40)); // Un poco más ancho para que quepa el texto
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    // 4. Registramos el nuevo botón en el controller
    public void setController(MisSesionesController controller) {
        this.btnCompletar.addActionListener(controller); // <-- NUEVO
        this.btnEditar.addActionListener(controller);
        this.btnCancelar.addActionListener(controller);
        this.btnVolver.addActionListener(controller);
    }
}