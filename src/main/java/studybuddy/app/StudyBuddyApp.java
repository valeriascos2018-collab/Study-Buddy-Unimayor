package studybuddy.app;

import studybuddy.gui.FrmLogin;
import javax.swing.*;
import studybuddy.factory.AppFactory;
/**
 * Clase principal de la aplicación.
 * Punto de entrada que lanza la ventana de Login.
 */
public class StudyBuddyApp {
    
    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing (buena práctica)
        SwingUtilities.invokeLater(() -> {
            AppFactory.iniciarLogin();
        });
    }
}