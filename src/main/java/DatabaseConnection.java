import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        // まずMYSQL_URLを試す（Railway推奨）
        String mysqlUrl = System.getenv("MYSQL_URL");
        
        if (mysqlUrl != null && !mysqlUrl.isEmpty()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC driver not found", e);
            }
            return DriverManager.getConnection(mysqlUrl);
        }
        
        // MYSQL_URLがない場合は個別の環境変数から構築
        String host = System.getenv("MYSQL_HOST");
        String port = System.getenv("MYSQL_PORT");
        String database = System.getenv("MYSQL_DATABASE");
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        String user = System.getenv("MYSQLUSER");
        
        // デバッグ用（本番では削除推奨）
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("Database: " + database);
        
        if (host == null || port == null || database == null || user == null || password == null) {
            throw new SQLException("Database connection environment variables not properly set");
        }
        
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
        
        return DriverManager.getConnection(url, user, password);
    }
}