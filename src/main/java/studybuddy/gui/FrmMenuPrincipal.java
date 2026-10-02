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

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FrmMenuPrincipal extends JFrame {

    public FrmMenuPrincipal(Usuario usuario) {
        setTitle("StudyBuddy - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));
        ((javax.swing.JComponent) getContentPane()).setBorder(new EmptyBorder(20, 30, 20, 30));

        add(new JLabel("Hola, " + usuario.getNombreCompleto(), SwingConstants.CENTER));

        JButton btnCatalogo = new JButton("Ver catálogo de tutorías");
        btnCatalogo.addActionListener(e -> {
            FrmCatalogoTutorias v = new FrmCatalogoTutorias();
            new CatalogoTutoriasController(v, usuario);
            v.setVisible(true);
            dispose();
        });
        add(btnCatalogo);

        if (usuario.getRol() == Rol.TUTOR_MONITOR) {
            JButton btnPublicar = new JButton("Publicar sesión");
            btnPublicar.addActionListener(e -> {
                FrmPublicarSesion v = new FrmPublicarSesion();
                new PublicarSesionController(v, usuario.getId());
                v.setVisible(true);
                dispose();
            });
            add(btnPublicar);

            JButton btnMisSesiones = new JButton("Mis sesiones");
            btnMisSesiones.addActionListener(e -> {
                FrmMisSesiones v = new FrmMisSesiones();
                new MisSesionesController(v, usuario);
                v.setVisible(true);
                dispose();
            });
            add(btnMisSesiones);
        }

        JButton btnPerfil = new JButton("Mi perfil");
        btnPerfil.addActionListener(e -> {
            new FrmPerfil(usuario).setVisible(true);
            dispose();
        });
        add(btnPerfil);

        pack();
        setLocationRelativeTo(null);
    }
}
