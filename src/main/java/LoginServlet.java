import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MemoRegister;
import bean.UserRegister;

@WebServlet("/UserLogin")
public class LoginServlet extends HttpServlet{
	 protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		    req.setCharacterEncoding("UTF-8");

		    String name = req.getParameter("name");
		    String password = req.getParameter("password");

		    UserDao dao = new UserDao();
		    UserRegister user = dao.findUser(name, password); // ↓で定義するメソッド

		    if (user != null) {
		      HttpSession session = req.getSession();
		      session.setAttribute("user", user);
		      // ✅ メモ一覧を取得
		      MemoDao memoDao = new MemoDao();
		      List<MemoRegister> memoList = memoDao.selectByUserId(user);

		      // ✅ memoList をリクエストにセット
		      req.setAttribute("memoList", memoList);

		      // ✅ フォワードで JSP に渡す
		      req.getRequestDispatcher("UserPage.jsp").forward(req, res);
		      
		    } else {
		      req.setAttribute("error", "ユーザー名またはパスワードが違います");
		      req.getRequestDispatcher("LoginPage.html").forward(req, res);
		    }
	}
}