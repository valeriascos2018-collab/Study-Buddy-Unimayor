package studybuddy.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    // Puede sobrescribirse con variables de entorno sin modificar el código.
    private static final String URL = System.getenv().getOrDefault(
            "STUDYBUDDY_DB_URL",
            "jdbc:mariadb://localhost:3306/studybuddy_unimayor"
    );
    private static final String USUARIO = System.getenv().getOrDefault("STUDYBUDDY_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("STUDYBUDDY_DB_PASSWORD", "");

    private static HikariDataSource dataSource;

    // Constructor privado para evitar instanciación
    private ConexionBD() {}
    

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setDriverClassName("org.mariadb.jdbc.Driver");
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