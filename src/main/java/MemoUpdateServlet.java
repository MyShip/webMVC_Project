import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MemoRegister;
import bean.UserRegister;

@WebServlet("/MemoUpdate")
public class MemoUpdateServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    int id = Integer.parseInt(req.getParameter("id"));
    String title = req.getParameter("title");
    String message = req.getParameter("message");

    MemoDao dao = new MemoDao();
    dao.updateMemo(id, title, message);

    HttpSession session = req.getSession();
    UserRegister user = (UserRegister) session.getAttribute("user");

    // ↓ メモ一覧を取得してスコープにセット
    List<MemoRegister> memoList = dao.selectByUserId(user);
    req.setAttribute("memoList", memoList);

    // ↓ JSPにフォワード
    RequestDispatcher rd = req.getRequestDispatcher("/UserPage.jsp");
    rd.forward(req, res);
  }
}