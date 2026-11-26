package studybuddy.app;


import studybuddy.controller.ControllerFactory;
import studybuddy.config.ConexionBD; // Cambia a studybuddy.util.ConexionBD si está en util
import studybuddy.gui.FrmLogin;
import javax.swing.SwingUtilities;
/**
 * Clase principal de la aplicación.
 * Punto de entrada que lanza la ventana de Login.
 */

public class StudyBuddyApp {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConexionBD.getInstancia().cerrarPool();
        }));

        SwingUtilities.invokeLater(() -> {
            FrmLogin vistaLogin = new FrmLogin();
            ControllerFactory.crearLoginController(vistaLogin);
            vistaLogin.setVisible(true);
        });
    }
}