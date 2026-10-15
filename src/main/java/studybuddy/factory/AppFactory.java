package studybuddy.factory;


import studybuddy.controller.LoginController;
import studybuddy.gui.FrmLogin;
import studybuddy.logic.AutenticacionService;
import studybuddy.logic.IAutenticacionService;
import studybuddy.logic.IValidadorUsuario;

/**
 * Factory principal de la aplicación.
 * Se encarga de instanciar y conectar las dependencias al inicio.
 */

public class AppFactory {

    /**
     * Método principal para iniciar el flujo de Login.
     */
    public static void iniciarLogin() {
        // 1. Crear la Vista (Solo se dibuja, no sabe nada de lógica)
        FrmLogin vista = new FrmLogin();
        
        // 2. Crear el Servicio (La lógica de negocio)
        IAutenticacionService servicio = ServiceFactory.crearAutenticacionService();
        IValidadorUsuario validador = ServiceFactory.crearValidadorUsuario();
        
        // 3. Crear el Controller e INYECTARLE ambas dependencias
        // ¡Aquí es donde se conectan los cables!
        new LoginController(vista, servicio, validador);
        
        // 4. Mostrar la vista al usuario
        vista.setVisible(true);
    }
}