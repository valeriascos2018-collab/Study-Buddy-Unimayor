package studybuddy.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/studybuddy_unimayor?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // ⚠️ Ver Paso 5 para mejorar esto

    private static HikariDataSource dataSource;

    // Constructor privado para evitar instanciación
    private ConexionBD() {}

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USUARIO);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(10);
            
            // Optimizaciones de HikariCP
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    /** 
     * Cada llamada devuelve una conexión del pool. 
     * ¡DEBES CERRARLA al terminar! (Usa try-with-resources)
     */
    public static Connection getConexion() throws SQLException {
        return getDataSource().getConnection();
    }

    /** Cierra el pool de conexiones (útil al cerrar la aplicación) */
    public static void cerrar() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}