/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studybuddy.gui;

/**
 *
 * @author yoban
 */

import studybuddy.controller.CatalogoTutoriasController;
import studybuddy.controller.MisSesionesController;
import studybuddy.controller.PublicarSesionController;
import studybuddy.model.Rol;
import studybuddy.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class FrmMenuPrincipal extends JFrame {

    // Colores institucionales
    private static final Color COLOR_PRIMARIO = new Color(0, 102, 204);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);

    public FrmMenuPrincipal(Usuario usuario) {
        setTitle("StudyBuddy - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo gris claro
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);

        // ===== HEADER =====
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_PRIMARIO);
        panelHeader.setPreferredSize(new Dimension(0, 130));
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.add(Box.createVerticalStrut(25));

        JLabel lblTitulo = new JLabel("StudyBuddy Unimayor");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.add(lblTitulo);

        panelHeader.add(Box.createVerticalStrut(10));

        JLabel lblSaludo = new JLabel("¡Hola, " + usuario.getNombreCompleto() + "!");
        lblSaludo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSaludo.setForeground(new Color(220, 230, 240));
        lblSaludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.add(lblSaludo);

        panelHeader.add(Box.createVerticalStrut(25));
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ===== PANEL DE BOTONES =====
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panelContenido.add(Box.createVerticalStrut(10));

        // --- Botón: Catálogo de tutorías ---
        JButton btnCatalogo = crearBotonMenu("📚 Ver catálogo de tutorías", COLOR_PRIMARIO);
        btnCatalogo.addActionListener(e -> {
            FrmCatalogoTutorias v = new FrmCatalogoTutorias();
            new CatalogoTutoriasController(v, usuario);
            v.setVisible(true);
            dispose();
        });
        btnCatalogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(btnCatalogo);
        panelContenido.add(Box.createVerticalStrut(15));

        // --- Botones exclusivos para Tutor/Monitor ---
        if (usuario.getRol() == Rol.TUTOR_MONITOR) {
            JButton btnPublicar = crearBotonMenu("➕ Publicar sesión", new Color(40, 167, 69));
            btnPublicar.addActionListener(e -> {
                FrmPublicarSesion v = new FrmPublicarSesion();
                new PublicarSesionController(v, usuario.getId());
                v.setVisible(true);
                dispose();
            });
            btnPublicar.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(btnPublicar);
            panelContenido.add(Box.createVerticalStrut(15));

            JButton btnMisSesiones = crearBotonMenu("📋 Mis sesiones", new Color(255, 165, 0));
            btnMisSesiones.addActionListener(e -> {
                FrmMisSesiones v = new FrmMisSesiones();
                new MisSesionesController(v, usuario);
                v.setVisible(true);
                dispose();
            });
            btnMisSesiones.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(btnMisSesiones);
            panelContenido.add(Box.createVerticalStrut(15));
        }

        // --- Botón: Mi perfil ---
        JButton btnPerfil = crearBotonMenu("👤 Mi perfil", new Color(108, 117, 125));
        btnPerfil.addActionListener(e -> {
            new FrmPerfil(usuario).setVisible(true);
            dispose();
        });
        btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(btnPerfil);

        panelContenido.add(Box.createVerticalStrut(20));
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    /**
     * Crea un botón estilizado para el menú.
     * Mantiene exactamente la misma funcionalidad del botón original.
     */
    private JButton crearBotonMenu(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(380, 50));
        boton.setMaximumSize(new Dimension(380, 50));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });

        return boton;
    }
}