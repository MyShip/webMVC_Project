import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class DBManager {
//    private static final String RDB_DRIVE = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:3307/My_Notes";
//    private static final String USER = "root";
//    private static final String PASS = "pass";
//
//    public static Connection getConnection() {
//        try {
//            Class.forName(RDB_DRIVE);
//            return DriverManager.getConnection(URL, USER, PASS);
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//}
//public class DBManager {
//    private static final String DB_URL = "jdbc:mysql://" + 
//        System.getenv("DMYSQL_HOST") + ":" + 
//        System.getenv("DMYSQL_PORT") + "/" + 
//        System.getenv("DMYSQL_NAME") + 
//        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//    
//    private static final String USER = System.getenv("MYSQLUSER");
//    private static final String PASSWORD = System.getenv("MYSQLPASSWORD");
//    
//    static {
//        try {
//            // JDBCドライバーを明示的にロード
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("MySQL JDBC Driver not found", e);
//        }
//    }
//    
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
//    }
//}
public class DBManager {
    private static final String DB_URL = System.getenv("DATABASE_URL");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}