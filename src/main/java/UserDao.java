import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.UserRegister;
public class UserDao {

	// MySQL Connector/J 8.0対応 + Railway環境対応
	private static final String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";

	private static final String URL = System.getenv("DB_URL") != null ?
	    System.getenv("DB_URL") :
	    "jdbc:mysql://localhost:3307/My_Notes?useSSL=false&serverTimezone=UTC";

	private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
	private static final String PASS = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "pass";

 
 	//データベース接続を行うメソッド
 	public static Connection getConnection(){
 		try{
 			Class.forName(RDB_DRIVE);
 			Connection con = DriverManager.getConnection(URL, USER, PASS);
 			return con;
 		}catch(Exception e){
 			throw new IllegalStateException(e);
 		}
 	}
 
 	//データベースへデータを登録するメソッド
 	public int insert(UserRegister user){
 	    Connection con = null;
 	    PreparedStatement pstmt = null;
 	    ResultSet rs = null;
 	    int generatedId = 0;

 	    String sql = "INSERT INTO users (name, password) VALUES (?, ?)";

 	    try {
 	        con = getConnection();
 	        // RETURN_GENERATED_KEYSを指定して自動生成されたキーを取得可能にする
 	        pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 	        pstmt.setString(1, user.getName());
 	        pstmt.setString(2, user.getPass());

 	        int count = pstmt.executeUpdate();
 	        
 	        if (count > 0) {
 	            // 自動生成されたIDを取得
 	            rs = pstmt.getGeneratedKeys();
 	            if (rs.next()) {
 	                generatedId = rs.getInt(1);
 	                user.setId(generatedId); // UserRegisterオブジェクトにIDを設定
 	            }
 	        }

 	    } catch(Exception e){
 	        throw new IllegalStateException(e);
 	    } finally {
 	        if(rs != null){
 	            try{ rs.close(); } catch(SQLException ignore) {}
 	        }
 	        if(pstmt != null){
 	            try{ pstmt.close(); } catch(SQLException ignore) {}
 	        }
 	        if(con != null){
 	            try{ con.close(); } catch(SQLException ignore) {}
 	        }
 	    }
 	    return generatedId; // 生成されたIDを返す（0の場合は失敗）
 	}
 	public boolean isUserExists(String username) {
 		boolean exists = false;
 	    try (Connection conn = DBManager.getConnection()) {
 	        String sql = "SELECT COUNT(*) FROM users WHERE name = ?";
 	        PreparedStatement pstmt = conn.prepareStatement(sql);
 	        pstmt.setString(1, username);
 	        ResultSet rs = pstmt.executeQuery();

 	        if (rs.next()) {
 	            exists = rs.getInt(1) > 0;
 	        }
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }
 	    return exists;
 	}
 	//ログイン機能
 	public UserRegister findUser(String name, String password) {
 		
 		  Connection con = null;
 		  PreparedStatement pstmt = null;
 		  ResultSet rs = null;
 		
 		  String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
 		  
 		  try {
 			  con = getConnection();
 			  pstmt = con.prepareStatement(sql);
 			  pstmt.setString(1, name);
 			  pstmt.setString(2, password);
 			  rs = pstmt.executeQuery();
 			  
 			  if(rs.next()) {
 				  UserRegister user = new UserRegister();
 			      user.setId(rs.getInt("id"));
 			      user.setName(rs.getString("name"));
 			      user.setPass(rs.getString("password"));
 			      return user;
 			  }
 		 } catch(Exception e) {
 		    throw new IllegalStateException(e);
 		 } finally {
 		    try { if (rs != null) rs.close(); } catch (SQLException ignore) {}
 		    try { if (pstmt != null) pstmt.close(); } catch (SQLException ignore) {}
 		    try { if (con != null) con.close(); } catch (SQLException ignore) {}
 		 }
 		return null;
 	}
}