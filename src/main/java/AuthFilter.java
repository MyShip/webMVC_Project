import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final String LOGIN_PAGE = "/LoginPage.html";
    private static final String[] EXCLUDED_PATHS = {
        "/UserLogin", "/UserRegister","/UserRegister.html" , "/LoginPage.html", 
        "/TopPage.html" 
    };
    private static final String STATIC_RESOURCE_PATTERN = 
        ".*\\.(css|js|png|jpg|gif|ico|svg|webp|woff|woff2|ttf|map)$";

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI().substring(request.getContextPath().length());
        path = path.toLowerCase(); // 大文字小文字を統一
        
        if (isExcludedPath(path) || isStaticResource(path)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        Object loginUser = (session != null) ? session.getAttribute("user") : null;

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        chain.doFilter(req, res);
    }
    
    private boolean isExcludedPath(String path) {
        for (String excluded : EXCLUDED_PATHS) {
            if (path.equals(excluded.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isStaticResource(String path) {
        return path.matches(STATIC_RESOURCE_PATTERN);
    }
}