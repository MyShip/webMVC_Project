import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    
    // セッション取得
	  HttpSession session = req.getSession(false);
	  if (session != null) {
	      session.invalidate(); // セッション無効化
	  }
	  Cookie[] cookies = req.getCookies();
      if (cookies != null) {
          for (Cookie cookie : cookies) {
              if ("JSESSIONID".equals(cookie.getName())) {
                  cookie.setMaxAge(0);
                  cookie.setPath("/");
                  res.addCookie(cookie);
              }
          }
      }

    // ログインページなどにリダイレクト
    res.sendRedirect(req.getContextPath() + "/LoginPage.html");
  }
  protected void doGet(HttpServletRequest req, HttpServletResponse res) 
	        throws ServletException, IOException {
	    doPost(req, res); // POSTと同じ処理を実行
  }
}