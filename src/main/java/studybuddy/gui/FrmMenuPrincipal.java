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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

public class FrmMenuPrincipal extends JFrame {

    // ==========================
    // COLORES INSTITUCIONALES
    // ==========================
    private static final Color AZUL_OSCURO = new Color(7, 52, 94);
    private static final Color AZUL_BOTON = new Color(0, 105, 190);
    private static final Color VERDE = new Color(34, 153, 87);
    private static final Color NARANJA = new Color(214, 138, 22);
    private static final Color ROJO = new Color(200, 61, 77);
    private static final Color TEAL = new Color(23, 145, 165);
    private static final Color GRIS = new Color(104, 119, 137);
    private static final Color FONDO = new Color(241, 244, 248);
    private static final Color BORDE_TARJETA = new Color(215, 221, 228);

    public FrmMenuPrincipal(Usuario usuario) {

        setTitle("StudyBuddy Unimayor - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 720);
        setMinimumSize(new Dimension(560, 650));
        setLocationRelativeTo(null);
        setResizable(false);

        // ==========================
        // PANEL PRINCIPAL
        // ==========================
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(FONDO);
        panelPrincipal.setBorder(new EmptyBorder(24, 30, 20, 30));

        // ==========================
        // ENCABEZADO
        // ==========================
        JPanel panelHeader = new JPanel(new BorderLayout(18, 0));
        panelHeader.setBackground(AZUL_OSCURO);
        panelHeader.setBorder(new EmptyBorder(22, 26, 22, 26));

        JLabel lblIcono = new JLabel(crearIconoVectorial("birrete", 44, Color.WHITE));
        lblIcono.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelTextos = new JPanel();
        panelTextos.setOpaque(false);
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("StudyBuddy Unimayor");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSaludo = new JLabel("¡Hola, " + usuario.getNombreCompleto() + "!");
        lblSaludo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSaludo.setForeground(new Color(225, 237, 250));
        lblSaludo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Tu espacio de acompañamiento académico");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDescripcion.setForeground(new Color(185, 208, 234));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelTextos.add(lblTitulo);
        panelTextos.add(Box.createVerticalStrut(6));
        panelTextos.add(lblSaludo);
        panelTextos.add(Box.createVerticalStrut(3));
        panelTextos.add(lblDescripcion);

        panelHeader.add(lblIcono, BorderLayout.WEST);
        panelHeader.add(panelTextos, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ==========================
        // TARJETA CONTENEDORA
        // ==========================
        JPanel panelTarjeta = new JPanel(new BorderLayout());
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(24, 24, 20, 24)
        ));

        // Cabecera fija dentro de la tarjeta
        JPanel panelCabeceraTarjeta = new JPanel();
        panelCabeceraTarjeta.setLayout(new BoxLayout(panelCabeceraTarjeta, BoxLayout.Y_AXIS));
        panelCabeceraTarjeta.setBackground(Color.WHITE);

        JLabel lblMenu = new JLabel("Menú principal", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 21));
        lblMenu.setForeground(AZUL_OSCURO);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblIndicacion = new JLabel("Selecciona una opción para continuar", SwingConstants.CENTER);
        lblIndicacion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblIndicacion.setForeground(new Color(100, 115, 130));
        lblIndicacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(440, 1));
        separador.setForeground(new Color(230, 235, 240));
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCabeceraTarjeta.add(lblMenu);
        panelCabeceraTarjeta.add(Box.createVerticalStrut(5));
        panelCabeceraTarjeta.add(lblIndicacion);
        panelCabeceraTarjeta.add(Box.createVerticalStrut(14));
        panelCabeceraTarjeta.add(separador);
        panelCabeceraTarjeta.add(Box.createVerticalStrut(12));

        panelTarjeta.add(panelCabeceraTarjeta, BorderLayout.NORTH);

        // ==========================
        // PANEL DE BOTONES (DESPLAZABLE)
        // ==========================
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(new EmptyBorder(8, 12, 8, 12));

        // CATÁLOGO DE TUTORÍAS
        JButton btnCatalogo = crearBotonMenu(
                "Ver catálogo de tutorías",
                AZUL_BOTON,
                crearIconoVectorial("catalogo", 18, Color.WHITE)
        );
        btnCatalogo.addActionListener(e -> {
            FrmCatalogoTutorias v = new FrmCatalogoTutorias();
            ControllerFactory.crearCatalogoController(v, usuario);
            v.setVisible(true);
            dispose();
        });
        agregarBoton(panelBotones, btnCatalogo);

        // OPCIONES PARA TUTOR / MONITOR
        if (usuario.getRol() == Rol.TUTOR_MONITOR) {

            JButton btnPublicar = crearBotonMenu(
                    "Publicar sesión",
                    VERDE,
                    crearIconoVectorial("publicar", 18, Color.WHITE)
            );
            btnPublicar.addActionListener(e -> {
                FrmPublicarSesion v = new FrmPublicarSesion();
                ControllerFactory.crearPublicarSesionController(v, usuario.getId());
                v.setVisible(true);
                dispose();
            });
            agregarBoton(panelBotones, btnPublicar);

            JButton btnMisSesiones = crearBotonMenu(
                    "Mis sesiones",
                    NARANJA,
                    crearIconoVectorial("sesiones", 18, Color.WHITE)
            );
            btnMisSesiones.addActionListener(e -> {
                FrmMisSesiones v = new FrmMisSesiones();
                ControllerFactory.crearMisSesionesController(v, usuario);
                v.setVisible(true);
                dispose();
            });
            agregarBoton(panelBotones, btnMisSesiones);
        }

        // NOTIFICACIONES
        int sinLeer = ServiceFactory
                .crearBandejaNotificaciones()
                .contarNoLeidas(usuario.getId());

        String textoNotificaciones = sinLeer > 0
                ? "Notificaciones (" + sinLeer + " nuevas)"
                : "Notificaciones";

        Color colorNotificaciones = sinLeer > 0 ? ROJO : TEAL;

        JButton btnNotificaciones = crearBotonMenu(
                textoNotificaciones,
                colorNotificaciones,
                crearIconoVectorial("campana", 18, Color.WHITE)
        );
        btnNotificaciones.addActionListener(e -> {
            FrmNotificaciones v = new FrmNotificaciones();
            ControllerFactory.crearNotificacionesController(v, usuario);
            v.setVisible(true);
            dispose();
        });
        agregarBoton(panelBotones, btnNotificaciones);

        // MI PERFIL
        JButton btnPerfil = crearBotonMenu(
                "Mi perfil",
                GRIS,
                crearIconoVectorial("usuario", 18, Color.WHITE)
        );
        btnPerfil.addActionListener(e -> {
            new FrmPerfil(usuario).setVisible(true);
            dispose();
        });
        agregarBoton(panelBotones, btnPerfil);

        // REPORTE DE HORAS
        JButton btnReporte = crearBotonMenu(
                "Reporte de horas",
                TEAL,
                crearIconoVectorial("sesiones", 18, Color.WHITE)
        );
        btnReporte.addActionListener(e -> {
            FrmReporteHoras v = new FrmReporteHoras(usuario.getRol() == Rol.ADMINISTRADOR);
            ControllerFactory.crearReporteController(v, usuario);
            v.setVisible(true);
            dispose();
        });
        agregarBoton(panelBotones, btnReporte, false);

        // ScrollPane limpio y fluido
        JScrollPane scrollMenu = crearScrollEstilizado(panelBotones);
        panelTarjeta.add(scrollMenu, BorderLayout.CENTER);

        panelPrincipal.add(panelTarjeta, BorderLayout.CENTER);

        // ==========================
        // PIE DE PÁGINA
        // ==========================
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setBackground(FONDO);

        JLabel lblFooter = new JLabel("StudyBuddy Unimayor | Acompañamiento académico");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(110, 125, 140));

        panelFooter.add(lblFooter);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JScrollPane crearScrollEstilizado(JPanel contenido) {
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setBackground(Color.WHITE);
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(195, 205, 218);
                this.thumbHighlightColor = new Color(160, 175, 192);
                this.trackColor = Color.WHITE;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return crearBotonCero();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return crearBotonCero();
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isDragging ? AZUL_BOTON : thumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 2, thumbBounds.width - 2, thumbBounds.height - 4, 8, 8);
                g2.dispose();
            }

            private JButton crearBotonCero() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                btn.setMinimumSize(new Dimension(0, 0));
                btn.setMaximumSize(new Dimension(0, 0));
                return btn;
            }
        });

        return scroll;
    }

    private void agregarBoton(JPanel panel, JButton boton) {
        agregarBoton(panel, boton, true);
    }

    private void agregarBoton(JPanel panel, JButton boton, boolean conEspacioInferior) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(boton);
        if (conEspacioInferior) {
            panel.add(Box.createVerticalStrut(14));
        }
    }

    private JButton crearBotonMenu(String texto, Color colorFondo, Icon icono) {
        JButton boton = new JButton(texto, icono);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setIconTextGap(12);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        Dimension dim = new Dimension(440, 48);
        boton.setPreferredSize(dim);
        boton.setMinimumSize(dim);
        boton.setMaximumSize(dim);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });

        return boton;
    }

    private Icon crearIconoVectorial(String tipo, int size, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.translate(x, y);

                switch (tipo) {
                    case "birrete": {
                        Path2D gorro = new Path2D.Double();
                        gorro.moveTo(size * 0.5, size * 0.16);
                        gorro.lineTo(size * 0.94, size * 0.38);
                        gorro.lineTo(size * 0.5, size * 0.60);
                        gorro.lineTo(size * 0.06, size * 0.38);
                        gorro.closePath();
                        g2.fill(gorro);
                        g2.drawArc((int) (size * 0.22), (int) (size * 0.44), (int) (size * 0.56), (int) (size * 0.34), 180, 180);
                        g2.drawLine((int) (size * 0.89), (int) (size * 0.40), (int) (size * 0.89), (int) (size * 0.72));
                        break;
                    }
                    case "catalogo": {
                        g2.drawRoundRect(2, 2, size - 5, size - 4, 3, 3);
                        g2.drawLine(6, 6, size - 6, 6);
                        g2.drawLine(6, 10, size - 6, 10);
                        g2.drawLine(6, 14, size - 9, 14);
                        break;
                    }
                    case "publicar": {
                        g2.drawOval(2, 2, size - 4, size - 4);
                        int mid = size / 2;
                        g2.drawLine(mid, 5, mid, size - 5);
                        g2.drawLine(5, mid, size - 5, mid);
                        break;
                    }
                    case "sesiones": {
                        g2.drawRoundRect(2, 4, size - 4, size - 6, 3, 3);
                        g2.drawLine(2, 8, size - 2, 8);
                        g2.drawLine(6, 2, 6, 5);
                        g2.drawLine(size - 6, 2, size - 6, 5);
                        break;
                    }
                    case "campana": {
                        g2.drawArc(4, 3, size - 8, size - 7, 0, 180);
                        g2.drawLine(4, 8, 3, size - 5);
                        g2.drawLine(size - 4, 8, size - 3, size - 5);
                        g2.drawLine(2, size - 5, size - 2, size - 5);
                        g2.drawArc(size / 2 - 2, size - 5, 4, 3, 180, 180);
                        break;
                    }
                    case "usuario": {
                        int head = (int) (size * 0.36);
                        g2.drawOval((size - head) / 2, 2, head, head);
                        g2.drawArc(3, (int) (size * 0.48), size - 6, (int) (size * 0.55), 0, 180);
                        break;
                    }
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() { return size; }

            @Override
            public int getIconHeight() { return size; }
        };
    }
}