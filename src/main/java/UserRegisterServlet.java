import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserRegister;

@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        String error = "";
        UserDao userDao = new UserDao();
        UserRegister user = new UserRegister();
        
        try{
            //文字エンコーディングの指定
            req.setCharacterEncoding("utf-8");
            //パラメータの取得
            user.setName(req.getParameter("name"));
            user.setPass(req.getParameter("password"));
            
            if(userDao.isUserExists(user.getName())) {
                error = "このユーザー名は既に使われています";
                // エラー情報をセッションに保存してリダイレクト
                req.getSession().setAttribute("registerError", error);
                res.sendRedirect(req.getContextPath() + "/UserRegister");
            } else {
                int rd = userDao.insert(user);
                if(rd > 0) {
                    // 登録成功時はセッションにユーザー情報を保存してユーザーページへリダイレクト
                    req.getSession().setAttribute("user", user);
                    res.sendRedirect(req.getContextPath() + "/UserPage");
                } else {
                    error = "ユーザー登録に失敗しました";
                    req.getSession().setAttribute("registerError", error);
                    res.sendRedirect(req.getContextPath() + "/UserRegister");
                }
            }
        }catch (IllegalStateException e) {
            error ="DB接続エラーの為、登録できませんでした。";
            req.getSession().setAttribute("registerError", error);
            res.sendRedirect(req.getContextPath() + "/UserRegister");
        }catch(Exception e){
            error ="予期せぬエラーが発生しました。<br>"+e;
            req.getSession().setAttribute("registerError", error);
            res.sendRedirect(req.getContextPath() + "/UserRegister");
        }
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        
        // 既にログイン済みの場合はユーザーページへリダイレクト
        if (session != null && session.getAttribute("user") != null) {
            res.sendRedirect(req.getContextPath() + "/UserPage");
            return;
        }
        
        // エラー情報があれば取得して削除
        String error = "";
        if (session != null) {
            Object errorObj = session.getAttribute("registerError");
            if (errorObj != null) {
                error = errorObj.toString();
                session.removeAttribute("registerError");
            }
        }
        
        // エラー情報をリクエストスコープに設定
        req.setAttribute("error", error);
        
        // 登録フォームを表示
        req.getRequestDispatcher("/UserRegister.html").forward(req, res);
    }
}