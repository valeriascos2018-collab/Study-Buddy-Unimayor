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

/**
 * Ventana para editar una sesión de tutoría.
 */
public class FrmEditarSesion extends JFrame {

    // ==========================
    // COMPONENTES PÚBLICOS
    // ==========================

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

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================

    private final Color AZUL_OSCURO =
            new Color(7, 52, 94);

    private final Color VERDE =
            new Color(34, 153, 87);

    private final Color FONDO =
            new Color(241, 244, 248);

    private final Color GRIS =
            new Color(232, 236, 240);

    private final Color TEXTO =
            new Color(30, 45, 60);

    public FrmEditarSesion(Sesion sesion) {

        this.sesionOriginal = sesion;

        initComponents();

        cargarDatos();
    }

    /**
     * Inicializa los componentes de la ventana.
     */
    private void initComponents() {

        setTitle("Study Buddy Unimayor - Editar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        // ==========================
        // PANEL PRINCIPAL
        // ==========================

        JPanel panelPrincipal = new JPanel(
                new BorderLayout(0, 18)
        );

        panelPrincipal.setBackground(FONDO);

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // ==========================
        // ENCABEZADO
        // ==========================

        JPanel panelHeader = new JPanel(
                new BorderLayout(15, 5)
        );

        panelHeader.setBackground(AZUL_OSCURO);

        panelHeader.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JPanel panelTextos = new JPanel();

        panelTextos.setOpaque(false);

        panelTextos.setLayout(
                new BoxLayout(
                        panelTextos,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitulo = new JLabel(
                "Editar Sesión de Tutoría"
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel(
                "Actualiza la información de tu sesión académica"
        );

        lblSubtitulo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        lblSubtitulo.setForeground(
                new Color(215, 229, 244)
        );

        panelTextos.add(lblTitulo);

        panelTextos.add(
                Box.createVerticalStrut(8)
        );

        panelTextos.add(lblSubtitulo);

        JLabel lblIcono = new JLabel("\uD83D\uDCC5");

        lblIcono.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 38)
        );

        panelHeader.add(
                lblIcono,
                BorderLayout.WEST
        );

        panelHeader.add(
                panelTextos,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                panelHeader,
                BorderLayout.NORTH
        );

        // ==========================
        // FORMULARIO
        // ==========================

        JPanel panelFormulario = new JPanel(
                new GridBagLayout()
        );

        panelFormulario.setBackground(Color.WHITE);

        panelFormulario.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 221, 228)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 30, 20, 30
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(7, 10, 7, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.anchor = GridBagConstraints.WEST;

        // Título del formulario

        JLabel lblInformacion = crearSubtitulo(
                "Información de la sesión"
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        panelFormulario.add(
                lblInformacion,
                gbc
        );

        gbc.gridwidth = 1;

        // ==========================
        // ASIGNATURA
        // ==========================

        txtAsignatura = new JTextField(25);

        estilizarCampo(txtAsignatura);

        agregarCampo(
                panelFormulario,
                gbc,
                "Asignatura:",
                1,
                txtAsignatura
        );

        // ==========================
        // TEMA
        // ==========================

        txtTema = new JTextField(25);

        estilizarCampo(txtTema);

        agregarCampo(
                panelFormulario,
                gbc,
                "Tema:",
                2,
                txtTema
        );

        // ==========================
        // CUPO MÁXIMO
        // ==========================

        txtCupoMaximo = new JTextField(25);

        estilizarCampo(txtCupoMaximo);

        agregarCampo(
                panelFormulario,
                gbc,
                "Cupo Máximo:",
                3,
                txtCupoMaximo
        );

        // ==========================
        // MODALIDAD
        // ==========================

        cboModalidad = new JComboBox<>(
                new String[]{
                    "Presencial",
                    "Virtual"
                }
        );

        estilizarCombo(cboModalidad);

        agregarCampo(
                panelFormulario,
                gbc,
                "Modalidad:",
                4,
                cboModalidad
        );

        // ==========================
        // FECHA
        // ==========================

        spnFecha = new JSpinner(
                new SpinnerDateModel()
        );

        spnFecha.setEditor(
                new JSpinner.DateEditor(
                        spnFecha,
                        "dd/MM/yyyy"
                )
        );

        estilizarSpinner(spnFecha);

        agregarCampo(
                panelFormulario,
                gbc,
                "Fecha:",
                5,
                spnFecha
        );

        // ==========================
        // HORA INICIO
        // ==========================

        spnHoraInicio = new JSpinner(
                new SpinnerDateModel()
        );

        spnHoraInicio.setEditor(
                new JSpinner.DateEditor(
                        spnHoraInicio,
                        "HH:mm"
                )
        );

        estilizarSpinner(spnHoraInicio);

        agregarCampo(
                panelFormulario,
                gbc,
                "Hora Inicio:",
                6,
                spnHoraInicio
        );

        // ==========================
        // HORA FIN
        // ==========================

        spnHoraFin = new JSpinner(
                new SpinnerDateModel()
        );

        spnHoraFin.setEditor(
                new JSpinner.DateEditor(
                        spnHoraFin,
                        "HH:mm"
                )
        );

        estilizarSpinner(spnHoraFin);

        agregarCampo(
                panelFormulario,
                gbc,
                "Hora Fin:",
                7,
                spnHoraFin
        );

        // ==========================
        // SEDE
        // ==========================

        cboSede = new JComboBox<>(
                new String[]{
                    "Bicentenario",
                    "Casa Obando",
                    "Encarnación",
                    "Virtual"
                }
        );

        estilizarCombo(cboSede);

        agregarCampo(
                panelFormulario,
                gbc,
                "Sede / Enlace:",
                8,
                cboSede
        );

        panelPrincipal.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        // ==========================
        // BOTONES
        // ==========================

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        15,
                        5
                )
        );

        panelBotones.setBackground(FONDO);

        btnCancelar = new JButton(
                "✕  Cancelar"
        );

        btnGuardar = new JButton(
                "✓  Guardar Cambios"
        );

        estilizarBoton(
                btnCancelar,
                GRIS,
                TEXTO,
                160
        );

        estilizarBoton(
                btnGuardar,
                VERDE,
                Color.WHITE,
                190
        );

        panelBotones.add(btnCancelar);

        panelBotones.add(btnGuardar);

        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    /**
     * Carga los datos originales de la sesión.
     */
    private void cargarDatos() {

        txtAsignatura.setText(
                sesionOriginal.getAsignatura()
        );

        txtTema.setText(
                sesionOriginal.getTema()
        );

        txtCupoMaximo.setText(
                String.valueOf(
                        sesionOriginal.getCupoMaximo()
                )
        );

        cboModalidad.setSelectedItem(
                sesionOriginal.getModalidad()
        );

        spnFecha.setValue(
                java.util.Date.from(
                        sesionOriginal.getFecha()
                                .atStartOfDay(
                                        java.time.ZoneId.systemDefault()
                                )
                                .toInstant()
                )
        );

        spnHoraInicio.setValue(
                java.util.Date.from(
                        sesionOriginal.getHoraInicio()
                                .atDate(LocalDate.now())
                                .atZone(
                                        java.time.ZoneId.systemDefault()
                                )
                                .toInstant()
                )
        );

        spnHoraFin.setValue(
                java.util.Date.from(
                        sesionOriginal.getHoraFin()
                                .atDate(LocalDate.now())
                                .atZone(
                                        java.time.ZoneId.systemDefault()
                                )
                                .toInstant()
                )
        );

        cboSede.setSelectedItem(
                sesionOriginal.getSede()
        );
    }

    /**
     * Crea los subtítulos del formulario.
     */
    private JLabel crearSubtitulo(String texto) {

        JLabel etiqueta = new JLabel(texto);

        etiqueta.setFont(
                new Font("Arial", Font.BOLD, 17)
        );

        etiqueta.setForeground(AZUL_OSCURO);

        return etiqueta;
    }

    /**
     * Agrega etiquetas y componentes al formulario.
     */
    private void agregarCampo(
            JPanel panel,
            GridBagConstraints gbc,
            String texto,
            int fila,
            JComponent componente
    ) {

        JLabel etiqueta = new JLabel(texto);

        etiqueta.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        etiqueta.setForeground(AZUL_OSCURO);

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.30;

        componente.setPreferredSize(
                new Dimension(350, 36)
        );

        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        panel.add(componente, gbc);
    }

    /**
     * Estilo para campos de texto.
     */
    private void estilizarCampo(JTextField campo) {

        campo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        campo.setForeground(TEXTO);

        campo.setBackground(Color.WHITE);

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(180, 195, 208)
                        ),
                        BorderFactory.createEmptyBorder(
                                7, 10, 7, 10
                        )
                )
        );
    }

    /**
     * Estilo para listas desplegables.
     */
    private void estilizarCombo(JComboBox<String> combo) {

        combo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        combo.setBackground(Color.WHITE);

        combo.setForeground(TEXTO);

        combo.setBorder(
                BorderFactory.createLineBorder(
                        new Color(180, 195, 208)
                )
        );
    }

    /**
     * Estilo para selectores de fecha y hora.
     */
    private void estilizarSpinner(JSpinner spinner) {

        spinner.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        spinner.setBackground(Color.WHITE);

        spinner.setBorder(
                BorderFactory.createLineBorder(
                        new Color(180, 195, 208)
                )
        );

        JComponent editor = spinner.getEditor();

        if (editor instanceof JSpinner.DefaultEditor) {

            JTextField campo =
                    ((JSpinner.DefaultEditor) editor).getTextField();

            campo.setFont(
                    new Font("Arial", Font.PLAIN, 14)
            );

            campo.setForeground(TEXTO);

            campo.setBackground(Color.WHITE);

            campo.setBorder(
                    BorderFactory.createEmptyBorder(
                            5, 8, 5, 8
                    )
            );
        }
    }

    /**
     * Estilo institucional para botones.
     */
    private void estilizarBoton(
            JButton boton,
            Color fondo,
            Color letra,
            int ancho
    ) {

        boton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        boton.setBackground(fondo);

        boton.setForeground(letra);

        boton.setFocusPainted(false);

        boton.setBorderPainted(false);

        boton.setOpaque(true);

        boton.setPreferredSize(
                new Dimension(ancho, 43)
        );

        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    /**
     * Conecta los botones con el controlador.
     */
    public void setController(
            EditarSesionController controller
    ) {

        this.btnGuardar.addActionListener(controller);

        this.btnCancelar.addActionListener(controller);
    }

    /**
     * Devuelve la sesión original.
     */
    public Sesion getSesionOriginal() {

        return sesionOriginal;
    }
}
