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
import javax.swing.table.JTableHeader;
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

    // Colores institucionales
    private final Color AZUL_OSCURO = new Color(7, 52, 94);
    private final Color AZUL_BOTON = new Color(0, 105, 190);
    private final Color VERDE = new Color(34, 153, 87);
    private final Color FONDO = new Color(241, 244, 248);
    private final Color GRIS = new Color(232, 236, 240);
    private final Color TEXTO = new Color(30, 45, 60);

    public FrmNotificaciones() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Study Buddy Unimayor - Notificaciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // ==========================
        // PANEL PRINCIPAL
        // ==========================

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(FONDO);

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // ==========================
        // ENCABEZADO
        // ==========================

        JPanel panelHeader = new JPanel(new BorderLayout(15, 5));
        panelHeader.setBackground(AZUL_OSCURO);

        panelHeader.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);

        panelTitulo.setLayout(
            new BoxLayout(panelTitulo, BoxLayout.Y_AXIS)
        );

        JLabel lblTitulo = new JLabel("Mis notificaciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 27));
        lblTitulo.setForeground(Color.WHITE);

        lblResumen = new JLabel(
            "Mantente informado sobre los cambios en tus sesiones académicas"
        );

        lblResumen.setFont(new Font("Arial", Font.PLAIN, 15));
        lblResumen.setForeground(new Color(215, 229, 244));

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createVerticalStrut(8));
        panelTitulo.add(lblResumen);

        JLabel lblIcono = new JLabel("\uD83D\uDD14");
        lblIcono.setFont(
            new Font("Segoe UI Emoji", Font.PLAIN, 35)
        );
        lblIcono.setForeground(Color.WHITE);

        panelHeader.add(lblIcono, BorderLayout.WEST);
        panelHeader.add(panelTitulo, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ==========================
        // PANEL CENTRAL
        // ==========================

        JPanel panelCentro = new JPanel(new BorderLayout(0, 15));
        panelCentro.setBackground(Color.WHITE);

        panelCentro.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(215, 221, 228)
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
        );

        // Título de la tabla
        JLabel lblTabla = new JLabel("Notificaciones recibidas");
        lblTabla.setFont(new Font("Arial", Font.BOLD, 17));
        lblTabla.setForeground(AZUL_OSCURO);

        JPanel panelTituloTabla = new JPanel(new BorderLayout());
        panelTituloTabla.setBackground(Color.WHITE);
        panelTituloTabla.add(lblTabla, BorderLayout.WEST);

        panelCentro.add(panelTituloTabla, BorderLayout.NORTH);

        // ==========================
        // TABLA DE NOTIFICACIONES
        // ==========================

        String[] columnas = {
            "Estado", "Fecha", "Tipo", "Mensaje"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblNotificaciones = new JTable(modeloTabla);

        tblNotificaciones.setFont(
            new Font("Arial", Font.PLAIN, 14)
        );

        tblNotificaciones.setRowHeight(38);

        tblNotificaciones.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tblNotificaciones.setBackground(Color.WHITE);
        tblNotificaciones.setForeground(TEXTO);

        tblNotificaciones.setGridColor(
            new Color(225, 230, 235)
        );

        tblNotificaciones.setShowVerticalLines(true);
        tblNotificaciones.setShowHorizontalLines(true);

        tblNotificaciones.setSelectionBackground(
            new Color(219, 234, 249)
        );

        tblNotificaciones.setSelectionForeground(TEXTO);

        // Encabezado de tabla
        JTableHeader encabezado = tblNotificaciones.getTableHeader();

        encabezado.setFont(
            new Font("Arial", Font.BOLD, 14)
        );

        encabezado.setBackground(AZUL_OSCURO);
        encabezado.setForeground(Color.WHITE);
        encabezado.setPreferredSize(new Dimension(0, 43));
        encabezado.setReorderingAllowed(false);

        // Ancho de columnas
        tblNotificaciones.getColumnModel()
            .getColumn(0).setPreferredWidth(100);

        tblNotificaciones.getColumnModel()
            .getColumn(1).setPreferredWidth(140);

        tblNotificaciones.getColumnModel()
            .getColumn(2).setPreferredWidth(160);

        tblNotificaciones.getColumnModel()
            .getColumn(3).setPreferredWidth(480);

        // ==========================
        // RENDERIZADOR
        // ==========================

        DefaultTableCellRenderer renderizador =
            new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
            ) {

                Component c = super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column
                );

                Object estado = table.getModel()
                    .getValueAt(row, 0);

                boolean nueva = estado != null &&
                    estado.toString().contains("Nueva");

                c.setFont(
                    new Font(
                        "Arial",
                        nueva ? Font.BOLD : Font.PLAIN,
                        13
                    )
                );

                if (!isSelected) {
                    c.setBackground(
                        row % 2 == 0
                            ? Color.WHITE
                            : new Color(246, 248, 251)
                    );

                    c.setForeground(
                        nueva ? AZUL_OSCURO : TEXTO
                    );
                }

                setBorder(
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                );

                return c;
            }
        };

        for (int i = 0;
             i < tblNotificaciones.getColumnCount();
             i++) {

            tblNotificaciones.getColumnModel()
                .getColumn(i)
                .setCellRenderer(renderizador);
        }

        JScrollPane scrollTabla = new JScrollPane(tblNotificaciones);

        scrollTabla.setBorder(
            BorderFactory.createLineBorder(
                new Color(215, 221, 228)
            )
        );

        scrollTabla.setPreferredSize(
            new Dimension(0, 280)
        );

        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        // ==========================
        // DETALLE DE NOTIFICACIÓN
        // ==========================

        JPanel panelDetalle = new JPanel(new BorderLayout(0, 10));
        panelDetalle.setBackground(Color.WHITE);

        JLabel lblDetalle = new JLabel(
            "Detalle de la notificación seleccionada"
        );

        lblDetalle.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        lblDetalle.setForeground(AZUL_OSCURO);

        panelDetalle.add(lblDetalle, BorderLayout.NORTH);

        txtDetalle = new JTextArea(5, 40);

        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);

        txtDetalle.setFont(
            new Font("Arial", Font.PLAIN, 14)
        );

        txtDetalle.setForeground(TEXTO);
        txtDetalle.setBackground(
            new Color(247, 249, 252)
        );

        txtDetalle.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        txtDetalle.setText(
            "Selecciona una notificación para leerla completa."
        );

        JScrollPane scrollDetalle = new JScrollPane(txtDetalle);

        scrollDetalle.setBorder(
            BorderFactory.createLineBorder(
                new Color(215, 221, 228)
            )
        );

        panelDetalle.add(scrollDetalle, BorderLayout.CENTER);

        panelCentro.add(panelDetalle, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ==========================
        // PANEL DE BOTONES
        // ==========================

        JPanel panelBotones = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 15, 5)
        );

        panelBotones.setBackground(FONDO);

        // Botón marcar todas
        btnMarcarTodas = crearBoton(
            "✓  Marcar todas como leídas",
            AZUL_BOTON,
            Color.WHITE,
            240
        );

        // Botón volver
        btnVolver = crearBoton(
            "←  Volver",
            GRIS,
            TEXTO,
            150
        );

        panelBotones.add(btnVolver);
        panelBotones.add(btnMarcarTodas);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea botones con el estilo institucional.
     */
    private JButton crearBoton(
        String texto,
        Color fondo,
        Color letra,
        int ancho
    ) {

        JButton btn = new JButton(texto);

        btn.setFont(
            new Font("Arial", Font.BOLD, 14)
        );

        btn.setBackground(fondo);
        btn.setForeground(letra);

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        btn.setPreferredSize(
            new Dimension(ancho, 43)
        );

        btn.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return btn;
    }
}

