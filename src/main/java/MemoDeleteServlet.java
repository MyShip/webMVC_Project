import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MemoRegister;
import bean.UserRegister;
@WebServlet("/MemoDelete")
public class MemoDeleteServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws IOException, ServletException {
		
		String error = "";
		try {
		      req.setCharacterEncoding("utf-8");
		      int id = Integer.parseInt(req.getParameter("id"));

		      MemoDao memoDao = new MemoDao();
		      memoDao.delete(id);

		      // セッションからユーザー取得
		      HttpSession session = req.getSession();
		      UserRegister user = (UserRegister) session.getAttribute("user");

		      // 最新のメモ一覧を取得
		      ArrayList<MemoRegister> memoList = memoDao.selectByUserId(user);
		      req.setAttribute("memoList", memoList);

		    } catch (Exception e) {
		      error = "削除処理中にエラーが発生しました。" + e;
		    } finally {
		      req.setAttribute("error", error);
		      req.getRequestDispatcher("/UserPage.jsp").forward(req, res);
		 }
	}
	
}