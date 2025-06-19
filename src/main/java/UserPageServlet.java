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

@WebServlet("/UserPage")
public class UserPageServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        
        HttpSession session = req.getSession(false);
        UserRegister user = (UserRegister) session.getAttribute("user");
        
        if (user == null) {
            // セッションが切れている場合はログインページへ
            res.sendRedirect(req.getContextPath() + "/LoginPage.html");
            return;
        }
        
        try {
            // メモ一覧を取得
            MemoDao memoDao = new MemoDao();
            ArrayList<MemoRegister> memoList = memoDao.selectByUserId(user);
            req.setAttribute("memoList", memoList);
            
            // 成功メッセージがあれば取得して削除
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
                req.setAttribute("successMessage", successMessage);
                session.removeAttribute("successMessage");
            }
            
            // エラーメッセージがあれば取得して削除
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
                req.setAttribute("errorMessage", errorMessage);
                session.removeAttribute("errorMessage");
            }
            
        } catch (Exception e) {
            req.setAttribute("errorMessage", "メモの取得に失敗しました: " + e.getMessage());
        }
        
        // UserPage.jspに転送
        req.getRequestDispatcher("/UserPage.jsp").forward(req, res);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        // POSTの場合はGETにリダイレクト
        res.sendRedirect(req.getContextPath() + "/UserPage");
    }
}