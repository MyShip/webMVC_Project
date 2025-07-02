import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // RenderのPostgreSQLサービス用（これが優先）
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            try {
                Class.forName("org.postgresql.Driver");
                System.out.println("Connecting using DATABASE_URL");
                return DriverManager.getConnection(databaseUrl);
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL JDBC driver not found", e);
            }
        }
        
        // 個別パラメータから構築（PostgreSQL用に修正）
        String host = System.getenv("PGHOST");
        String port = System.getenv("PGPORT");
        String database = System.getenv("PGDATABASE");
        String user = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");
        
        // ローカル開発用のフォールバック
        if (host == null) host = "localhost";
        if (port == null) port = "5432";
        if (database == null) database = "my_notes";
        if (user == null) user = "postgres";
        if (password == null) password = "";
        
        // PostgreSQL JDBC URLを構築
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver not found", e);
        }
        
        System.out.println("Connecting to PostgreSQL database: " + host + ":" + port + "/" + database);
        return DriverManager.getConnection(url, user, password);
    }
}