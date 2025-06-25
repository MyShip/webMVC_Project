import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.MemoRegister;
import bean.UserRegister;
public class MemoDao {

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
 	public int insert(MemoRegister memo){
 		//変数宣言
 		Connection con = null;
 		PreparedStatement pstmt = null;
 
 		//return用変数
 		int count = 0;
 
 		//SQL文
 		String sql = "INSERT INTO messages (title, text, user_id) VALUES (?, ?, ?)";
 		try{
 			con = getConnection();
 			pstmt = con.prepareStatement(sql);
 	        pstmt.setString(1, memo.getTitle());
 	        pstmt.setString(2, memo.getMessage());
 	        pstmt.setInt(3, memo.getUserId());
 	        count = pstmt.executeUpdate();
 		}catch(Exception e){
 			throw new IllegalStateException(e);
 		}finally{
 			//リソースの開放
 			if(pstmt != null){try{pstmt.close();}catch(SQLException ignore){}}
 			if(con != null){try{con.close();}catch(SQLException ignore){}}
 		}
 		return count;
 	}
 	//テーブルに登録されているデータを参照するメソッド
 	public ArrayList<MemoRegister> selectByUserId(UserRegister user){
 	    Connection con = null;
 	    PreparedStatement pstmt = null;
 	    ResultSet rs = null;
 	    
 	    ArrayList<MemoRegister> list = new ArrayList<>();
 	    String sql = "SELECT * FROM messages WHERE user_id = ? ORDER BY id DESC";
 	    try {
 	    	con = getConnection();
 	    	pstmt = con.prepareStatement(sql);
 	    	pstmt.setInt(1, user.getId());
 	    	rs = pstmt.executeQuery();
 	    	
 	    	while(rs.next()) {
 	    		MemoRegister memo = new MemoRegister();
 	            memo.setId(rs.getInt("id"));
 	            memo.setTitle(rs.getString("title"));
 	            memo.setMessage(rs.getString("text"));
 	            memo.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
 	            memo.setUserId(rs.getInt("user_id"));
 	            list.add(memo);
 	    	}
 	    } catch(Exception e) {
 	    	throw new IllegalStateException(e);
 	    }finally {
 	        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
 	        if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
 	        if (con != null) try { con.close(); } catch (SQLException ignore) {}
 	    }
 	    return list;
 	}
 	public MemoRegister update(int id) {
 		Connection con = null;
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		MemoRegister memo = null;
 		try {
 			con = getConnection();
 			String sql = "SELECT * FROM messages WHERE id = ?";
 			pstmt = con.prepareStatement(sql);
 			pstmt.setInt(1, id);
 			rs = pstmt.executeQuery();
 			
 			if(rs.next()) {
 				memo = new MemoRegister();
 				memo.setId(rs.getInt("id"));
 				memo.setTitle(rs.getString("title"));
 				memo.setMessage(rs.getString("text"));
 				memo.setUserId(rs.getInt("user_id"));
 				memo.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
 			}
 	    	
 		 } catch(Exception e) {
 		    throw new IllegalStateException(e);
 		  } finally {
 		    if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
 		    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
 		    if (con != null) try { con.close(); } catch (SQLException ignore) {}
 		  }
 		 
 		return memo;
 	}
 	public void updateMemo(int id, String title, String message) {
 		  Connection con = null;
 		  PreparedStatement pstmt = null;

 		  try {
 		    con = getConnection();
 		    String sql = "UPDATE messages SET title = ?, text = ? WHERE id = ?";
 		    pstmt = con.prepareStatement(sql);
 		    pstmt.setString(1, title);
 		    pstmt.setString(2, message);
 		    pstmt.setInt(3, id);
 		    pstmt.executeUpdate();

 		  } catch(Exception e) {
 		    throw new IllegalStateException(e);
 		  } finally {
 		    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
 		    if (con != null) try { con.close(); } catch (SQLException ignore) {}
 		  }
 		}
 	//登録したメモを削除すメソッド
 	public int delete(int id) {
 	    Connection con = null;
 	    PreparedStatement pstmt = null;
 	    int count = 0;
 	    
 	   try {
 		    String sql = "DELETE FROM messages WHERE id = ?";
 		    con = getConnection();
 		    pstmt = con.prepareStatement(sql);
 		    pstmt.setInt(1, id);
 		    count = pstmt.executeUpdate();
 		  } catch (Exception e) {
 		    throw new IllegalStateException(e);
 		  } finally {
 		    if (pstmt != null) try { pstmt.close(); } catch (Exception ignore) {}
 		    if (con != null) try { con.close(); } catch (Exception ignore) {}
 		  }

 		  return count;
 	}
 }