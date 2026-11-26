package studybuddy.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = System.getenv().getOrDefault(
            "STUDYBUDDY_DB_URL",
            "jdbc:mariadb://localhost:3306/studybuddy_unimayor"
    );
    private static final String USUARIO = System.getenv().getOrDefault("STUDYBUDDY_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("STUDYBUDDY_DB_PASSWORD", "");

    // Instancia única del Singleton
    private static volatile ConexionBD instancia;
    private HikariDataSource dataSource;

    // Constructor privado original
    private ConexionBD() {}

    // Punto de acceso al Singleton
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                    instancia.inicializarPool();
                }
            }
        }
        return instancia;
    }

    private void inicializarPool() {
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

        this.dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection obtenerConexion() throws SQLException {
        return dataSource.getConnection();
    }

    public void cerrarPool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // --- Métodos estáticos de compatibilidad para tus DAOImpl actuales ---
    public static Connection getConexion() throws SQLException {
        return getInstancia().obtenerConexion();
    }

    public static void cerrar() {
        if (instancia != null) {
            instancia.cerrarPool();
        }
    }
}