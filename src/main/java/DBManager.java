import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
    private static final String RDB_DRIVE = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/My_Notes";
    private static final String USER = "root";
    private static final String PASS = "pass";

    public static Connection getConnection() {
        try {
            Class.forName(RDB_DRIVE);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}