package studybuddy.controller;


import studybuddy.factory.ServiceFactory;
import studybuddy.gui.FrmLogin;
import studybuddy.gui.FrmMenuPrincipal;
import studybuddy.gui.FrmRegistro;
import studybuddy.logic.IAutenticacionService;
import studybuddy.logic.IValidadorUsuario;
import studybuddy.model.Rol;
import studybuddy.model.Usuario;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginController {

    private final FrmLogin vista;
    private final IAutenticacionService servicio;
    private final IValidadorUsuario validador;

    public LoginController(FrmLogin vista, IAutenticacionService servicio, IValidadorUsuario validador) {
        this.vista = vista;
        this.servicio = servicio;
        this.validador = validador;

        this.vista.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarLogin();
            }
        });

        this.vista.btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    }

    private void manejarLogin() {
        String correo = vista.txtCorreo.getText().trim();
        String password = new String(vista.txtPassword.getPassword()).trim();

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validador.validarDominioInstitucional(correo)) {
            JOptionPane.showMessageDialog(vista, "El correo debe ser institucional (@unimayor.edu.co).", "Error de dominio", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuarioAutenticado = servicio.autenticar(correo, password);

        if (usuarioAutenticado != null) {
            IValidadorUsuario estrategiaRol = obtenerEstrategiaPorRol(usuarioAutenticado.getRol());

            JOptionPane.showMessageDialog(vista,
                    "¡Bienvenido, " + usuarioAutenticado.getNombreCompleto() + "!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioAutenticado);
            menu.setVisible(true);
            vista.dispose();

        } else {
            JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            vista.txtPassword.setText("");
            vista.txtCorreo.requestFocus();
        }
    }

    private IValidadorUsuario obtenerEstrategiaPorRol(Rol rol) {
        if (rol == null) {
            return validador;
        }
        return switch (rol.name()) {
            case "ESTUDIANTE"           -> ServiceFactory.crearValidadorEstudiante();
            case "TUTOR", "MENTOR"      -> ServiceFactory.crearValidadorTutor();
            case "ADMIN", "ADMINISTRADOR" -> ServiceFactory.crearValidadorAdministrador();
            default                     -> validador;
        };
    }

    private void abrirRegistro() {
        vista.setVisible(false);

        FrmRegistro vistaRegistro = new FrmRegistro();
        ControllerFactory.crearRegistroController(vistaRegistro);
        vistaRegistro.setVisible(true);

        vistaRegistro.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                vista.setVisible(true);
            }
        });
    }
}