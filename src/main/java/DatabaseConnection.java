import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        // まずMYSQL_URLを試す（Railway内部接続用）
        String mysqlUrl = System.getenv("MYSQL_URL");
        
        if (mysqlUrl != null && !mysqlUrl.isEmpty()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC driver not found", e);
            }
            return DriverManager.getConnection(mysqlUrl);
        }

        // MYSQL_URLが設定されていない場合は個別の環境変数から構築
        String host = System.getenv("MYSQL_HOST");
        String port = System.getenv("MYSQL_PORT");
        String database = System.getenv("MYSQL_DATABASE");
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        String user = System.getenv("MYSQL_USER");

        // デバッグ用の出力（本番環境では削除してください）
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("Database: " + database);
        System.out.println("User: " + user);
        System.out.println("Password: " + (password != null ? "[SET]" : "[NOT SET]"));

        if (host == null || port == null || database == null || password == null) {
            throw new SQLException("Database environment variables not properly set");
        }

        // userが設定されていない場合はrootを使用
        if (user == null) {
            user = "root";
        }

        // JDBC URLを構築（SSL無効化とタイムゾーン設定を追加）
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + 
                     "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}