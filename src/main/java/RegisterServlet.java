import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RegisterBean;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {

    req.setCharacterEncoding("utf-8");
    
    String name = req.getParameter("name");
    String age = req.getParameter("age");
    String[] langs = req.getParameterValues("lang");
    String money  = req.getParameter("money");
    String number1 = req.getParameter("number1");
    int num1 = Integer.parseInt(number1);
    String number2 = req.getParameter("number2");
    int num2 = Integer.parseInt(number2);
    res.setContentType("text/html;charset=utf-8");
    
    RegisterBean rb = new RegisterBean();
    rb.setName(name);
    rb.setAge(age);
    rb.setLangs(langs);
    rb.setMoney(money);
    rb.setNumber1(num1);
    rb.setNumber2(num2);
    
    req.setAttribute("rb", rb);
    
    RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
    rd.forward(req, res);
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {
    doPost(req, res);
  }
}
