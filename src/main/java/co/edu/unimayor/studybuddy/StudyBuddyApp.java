package co.edu.unimayor.studybuddy;

import co.edu.unimayor.studybuddy.gui.FrmLogin;
import javax.swing.*;

/**
 * Clase principal de la aplicación.
 * Punto de entrada que lanza la ventana de Login.
 */
public class StudyBuddyApp {
    
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Lanzar aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new FrmLogin().setVisible(true);
        });
    }
}
