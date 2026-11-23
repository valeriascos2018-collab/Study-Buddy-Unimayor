/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.gui;

/**
 *
 * @author yoban
 */


import studybuddy.model.Rol;
import studybuddy.model.Usuario;
import studybuddy.controller.ControllerFactory;
import studybuddy.factory.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class FrmMenuPrincipal extends JFrame {

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================

    private static final Color AZUL_OSCURO =
            new Color(7, 52, 94);

    private static final Color AZUL_BOTON =
            new Color(0, 105, 190);

    private static final Color VERDE =
            new Color(34, 153, 87);

    private static final Color NARANJA =
            new Color(214, 138, 22);

    private static final Color ROJO =
            new Color(200, 61, 77);

    private static final Color GRIS =
            new Color(104, 119, 137);

    private static final Color FONDO =
            new Color(241, 244, 248);

    public FrmMenuPrincipal(Usuario usuario) {

        setTitle("StudyBuddy Unimayor - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 720);
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
                        25, 30, 20, 30
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
                        25, 25, 25, 25
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
                "StudyBuddy Unimayor"
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 27)
        );

        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSaludo = new JLabel(
                "¡Hola, " + usuario.getNombreCompleto() + "!"
        );

        lblSaludo.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        lblSaludo.setForeground(
                new Color(215, 229, 244)
        );

        JLabel lblDescripcion = new JLabel(
                "Tu espacio de acompañamiento académico"
        );

        lblDescripcion.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        lblDescripcion.setForeground(
                new Color(215, 229, 244)
        );

        panelTextos.add(lblTitulo);
        panelTextos.add(Box.createVerticalStrut(10));
        panelTextos.add(lblSaludo);
        panelTextos.add(Box.createVerticalStrut(6));
        panelTextos.add(lblDescripcion);

        JLabel lblIcono = new JLabel("\uD83C\uDF93");

        lblIcono.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 42)
        );

        lblIcono.setForeground(Color.WHITE);

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
        // PANEL DE CONTENIDO
        // ==========================

        JPanel panelContenido = new JPanel();

        panelContenido.setLayout(
                new BoxLayout(
                        panelContenido,
                        BoxLayout.Y_AXIS
                )
        );

        panelContenido.setBackground(Color.WHITE);

        panelContenido.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 221, 228)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 35, 25, 35
                        )
                )
        );

        // Título del menú

        JLabel lblMenu = new JLabel(
                "Menú principal"
        );

        lblMenu.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        lblMenu.setForeground(AZUL_OSCURO);

        lblMenu.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panelContenido.add(lblMenu);

        panelContenido.add(
                Box.createVerticalStrut(5)
        );

        JLabel lblIndicacion = new JLabel(
                "Selecciona una opción para continuar"
        );

        lblIndicacion.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        lblIndicacion.setForeground(
                new Color(100, 115, 130)
        );

        lblIndicacion.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panelContenido.add(lblIndicacion);

        panelContenido.add(
                Box.createVerticalStrut(25)
        );

        // ==========================
        // CATÁLOGO DE TUTORÍAS
        // ==========================

        JButton btnCatalogo = crearBotonMenu(
                "  Ver catálogo de tutorías",
                AZUL_BOTON
        );

        btnCatalogo.addActionListener(e -> {

            FrmCatalogoTutorias v =
                    new FrmCatalogoTutorias();

            ControllerFactory.crearCatalogoController(
                    v, usuario
            );

            v.setVisible(true);
            dispose();
        });

        agregarBoton(panelContenido, btnCatalogo);

        // ==========================
        // OPCIONES PARA TUTOR / MONITOR
        // ==========================

        if (usuario.getRol() == Rol.TUTOR_MONITOR) {

            // Publicar sesión

            JButton btnPublicar = crearBotonMenu(
                    "  Publicar sesión",
                    VERDE
            );

            btnPublicar.addActionListener(e -> {

                FrmPublicarSesion v =
                        new FrmPublicarSesion();

                ControllerFactory.crearPublicarSesionController(
                        v, usuario.getId()
                );

                v.setVisible(true);
                dispose();
            });

            agregarBoton(panelContenido, btnPublicar);

            // Mis sesiones

            JButton btnMisSesiones = crearBotonMenu(
                    "  Mis sesiones",
                    NARANJA
            );

            btnMisSesiones.addActionListener(e -> {

                FrmMisSesiones v =
                        new FrmMisSesiones();

                ControllerFactory.crearMisSesionesController(
                        v, usuario
                );

                v.setVisible(true);
                dispose();
            });

            agregarBoton(panelContenido, btnMisSesiones);
        }

        // ==========================
        // NOTIFICACIONES
        // ==========================

        int sinLeer = ServiceFactory
                .crearBandejaNotificaciones()
                .contarNoLeidas(usuario.getId());

        String textoNotificaciones = sinLeer > 0
                ? "  Notificaciones (" + sinLeer + " nuevas)"
                : "  Notificaciones";

        Color colorNotificaciones = sinLeer > 0
                ? ROJO
                : new Color(23, 145, 165);

        JButton btnNotificaciones = crearBotonMenu(
                textoNotificaciones,
                colorNotificaciones
        );

        btnNotificaciones.addActionListener(e -> {

            FrmNotificaciones v =
                    new FrmNotificaciones();

            ControllerFactory.crearNotificacionesController(
                    v, usuario
            );

            v.setVisible(true);
            dispose();
        });

        agregarBoton(panelContenido, btnNotificaciones);

        // ==========================
        // MI PERFIL
        // ==========================

        JButton btnPerfil = crearBotonMenu(
                "  Mi perfil",
                GRIS
        );

        btnPerfil.addActionListener(e -> {

            new FrmPerfil(usuario).setVisible(true);
            dispose();
        });

        agregarBoton(panelContenido, btnPerfil);

        panelPrincipal.add(
                panelContenido,
                BorderLayout.CENTER
        );

        // ==========================
        // PIE DE PÁGINA
        // ==========================

        JPanel panelFooter = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        panelFooter.setBackground(FONDO);

        JLabel lblFooter = new JLabel(
                "StudyBuddy Unimayor | Acompañamiento académico"
        );

        lblFooter.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        lblFooter.setForeground(
                new Color(110, 125, 140)
        );

        panelFooter.add(lblFooter);

        panelPrincipal.add(
                panelFooter,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    /**
     * Agrega un botón al panel manteniendo
     * la misma separación y alineación.
     */
    private void agregarBoton(
            JPanel panel,
            JButton boton
    ) {

        boton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(boton);

        panel.add(
                Box.createVerticalStrut(15)
        );
    }

    /**
     * Crea los botones del menú con estilo institucional.
     * Conserva el efecto hover original.
     */
    private JButton crearBotonMenu(
            String texto,
            Color colorFondo
    ) {

        JButton boton = new JButton(texto);

        boton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        boton.setPreferredSize(
                new Dimension(430, 48)
        );

        boton.setMinimumSize(
                new Dimension(430, 48)
        );

        boton.setMaximumSize(
                new Dimension(430, 48)
        );

        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Efecto hover

        boton.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent evt
            ) {

                boton.setBackground(
                        colorFondo.darker()
                );
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent evt
            ) {

                boton.setBackground(
                        colorFondo
                );
            }
        });

        return boton;
    }
}
