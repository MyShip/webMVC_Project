import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://" + 
        System.getenv("MYSQLHOST") + ":" + 
        System.getenv("MYSQLPORT") + "/" + 
        System.getenv("MYSQL_DATABASE");
    private static final String USER = System.getenv("MYSQLUSER");
    private static final String PASSWORD = System.getenv("MYSQLPASSWORD");
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}