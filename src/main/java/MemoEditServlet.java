import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MemoRegister;

@WebServlet("/MemoEdit")
public class MemoEditServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
	  int id = Integer.parseInt(req.getParameter("id"));
    // セッション取得
	  MemoDao dao = new MemoDao();
	  MemoRegister memo = dao.update(id);
	  
	  if(memo != null) {
		  req.setAttribute("memo", memo);
		  req.getRequestDispatcher("MemoPage.jsp").forward(req, res);
	  } else {
		  res.sendRedirect("UserPage.jsp"); 
	  }
  }
  protected void doGet(HttpServletRequest req, HttpServletResponse res) 
	        throws ServletException, IOException {
	    doPost(req, res); // POSTと同じ処理を実行
  }
}