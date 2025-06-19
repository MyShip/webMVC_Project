import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MemoRegister;
import bean.UserRegister;

@WebServlet("/MemoRegister")
public class MemoRegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        String error = "";
        MemoDao memo = new MemoDao();
        boolean registerSuccess = false;
        
        try{
            //文字エンコーディングの指定
            req.setCharacterEncoding("utf-8");
            HttpSession session = req.getSession();
            UserRegister user = (UserRegister) session.getAttribute("user");
            
            if (user == null) {
                // セッションが切れている場合はログインページへ
                res.sendRedirect(req.getContextPath() + "/LoginPage.html");
                return;
            }
            String title = req.getParameter("title");
            String message = req.getParameter("message");
            System.out.println("取得したパラメータ - title: " + title + ", message: " + message); // デバッグ
            //DTOオブジェクト宣言
            MemoRegister memoinfo = new MemoRegister();
            
            //パラメータの取得
            memoinfo.setTitle(req.getParameter("title"));
            memoinfo.setMessage(req.getParameter("message"));
            memoinfo.setUserId(user.getId());
            
            //1件登録メソッドを呼び出し
            System.out.println("userId: " + user.getId()); // デバッグ

            //1件登録メソッドを呼び出し
            System.out.println("insert処理開始"); // デバッグ
            int rb = memo.insert(memoinfo);
            System.out.println("insert結果: " + rb); // デバッグ
            if (rb > 0) {
                registerSuccess = true;
                // 成功メッセージをセッションに保存
                System.out.println("メモ登録成功");
                session.setAttribute("successMessage", "メモを登録しました。");
            } else {
                error = "メモの登録に失敗しました。";
                System.out.print(registerSuccess);
                System.out.println("メモ登録失敗: rb = " + rb);
            }
            
        }catch (IllegalStateException e) {
            error ="DB接続エラーの為、登録できませんでした。";
        }catch(Exception e){
            error ="予期せぬエラーが発生しました。<br>"+e;
            System.out.println("エラー");
        }
        
        if (registerSuccess) {
            // 成功時はUserPageにリダイレクト（PRGパターン）
            res.sendRedirect(req.getContextPath() + "/UserPage");
        } else {
            // エラー時はエラーメッセージをセッションに保存してメモ作成ページに戻る
            req.getSession().setAttribute("errorMessage", error);
            System.out.println("メモの保存に失敗しました");
            System.out.println("registerSuccess = " + registerSuccess);
            System.out.println("error = " + error);
            // メモ作成ページのJSPに直接リダイレクト
            req.getRequestDispatcher("/MemoPage.jsp").forward(req, res);
        }
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        // GETアクセス時はメモ作成ページを表示
        req.getRequestDispatcher("/MemoPage.jsp").forward(req, res);
    }
}