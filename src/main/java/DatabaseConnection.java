import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // RenderのPostgreSQLサービス用
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL JDBC driver not found", e);
            }
            return DriverManager.getConnection(databaseUrl);
        }
        
        // 個別パラメータから構築
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String database = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        
        // ローカル開発用のフォールバック
        if (host == null) host = "localhost";
        if (port == null) port = "5432";
        if (database == null) database = "my_notes";
        if (user == null) user = "postgres";
        if (password == null) password = "";
        
        // PostgreSQL JDBC URLを構築
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database + 
                     "?sslmode=require&user=" + user + "&password=" + password;
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver not found", e);
        }
        
        System.out.println("Connecting to PostgreSQL database: " + host + ":" + port + "/" + database);
        return DriverManager.getConnection(url);
    }
}