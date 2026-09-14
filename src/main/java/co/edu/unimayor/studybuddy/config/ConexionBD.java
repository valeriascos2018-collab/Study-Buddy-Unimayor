package co.edu.unimayor.studybuddy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton para gestionar la conexión JDBC a MySQL.
 * Patrón: Singleton con conexión única reutilizable.
 */
public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/studybuddy_unimayor";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static ConexionBD instancia;
    private Connection conexion;
    
    /**
     * Constructor privado para forzar el uso del singleton.
     */
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            this.conexion.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al inicializar conexión BD: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la instancia única de la conexión.
     * @return Instancia singleton de ConexionBD
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    
    /**
     * Obtiene la conexión activa.
     * @return Connection JDBC
     */
    public Connection getConexion() {
        return conexion;
    }
    
    /**
     * Cierra la conexión (útil al finalizar la aplicación).
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}