/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.gui;

/**
 *
 * @author yoban
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Pantalla donde el estudiante (o el tutor, si también tiene reservas)
 * ve los avisos de sesiones modificadas o canceladas.
 */
public class FrmNotificaciones extends JFrame {

    public JTable tblNotificaciones;
    public DefaultTableModel modeloTabla;
    public JTextArea txtDetalle;
    public JButton btnMarcarTodas;
    public JButton btnVolver;
    public JLabel lblResumen;

    public FrmNotificaciones() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Study Buddy Unimayor - Notificaciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Encabezado
        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setPreferredSize(new Dimension(0, 80));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel lblTitulo = new JLabel("Mis notificaciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblResumen = new JLabel("", SwingConstants.CENTER);
        lblResumen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblResumen.setForeground(new Color(220, 230, 240));
        panelHeader.add(lblTitulo);
        panelHeader.add(lblResumen);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Estado", "Fecha", "Tipo", "Mensaje"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNotificaciones = new JTable(modeloTabla);
        tblNotificaciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblNotificaciones.setRowHeight(28);
        tblNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblNotificaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblNotificaciones.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblNotificaciones.getColumnModel().getColumn(1).setPreferredWidth(130);
        tblNotificaciones.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblNotificaciones.getColumnModel().getColumn(3).setPreferredWidth(430);

        // Las notificaciones nuevas se ven en negrita
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object estado = table.getModel().getValueAt(row, 0);
                boolean nueva = estado != null && estado.toString().contains("Nueva");
                c.setFont(c.getFont().deriveFont(nueva ? Font.BOLD : Font.PLAIN));
                return c;
            }
        };
        for (int i = 0; i < tblNotificaciones.getColumnCount(); i++) {
            tblNotificaciones.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }

        JScrollPane scrollTabla = new JScrollPane(tblNotificaciones);
        scrollTabla.setPreferredSize(new Dimension(0, 250));

        // Detalle del mensaje seleccionado
        txtDetalle = new JTextArea(4, 40);
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
        txtDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDetalle.setText("Selecciona una notificación para leerla completa.");
        JScrollPane scrollDetalle = new JScrollPane(txtDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle"));

        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelCentro.add(scrollTabla, BorderLayout.CENTER);
        panelCentro.add(scrollDetalle, BorderLayout.SOUTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        btnMarcarTodas = crearBoton("Marcar todas como leídas", new Color(0, 102, 204), Color.WHITE, 260);
        btnVolver = crearBoton("Volver", new Color(240, 240, 240), new Color(80, 80, 80), 150);
        panelBotones.add(btnMarcarTodas);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color fondo, Color letra, int ancho) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(fondo);
        btn.setForeground(letra);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(ancho, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

