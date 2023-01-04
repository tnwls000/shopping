package web.clone.onemorebag.common.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if ("/member/login".equals(request.getRequestURI())) {
            return true;
        }

        if (!isLogin(session)) {
            response.sendRedirect("/member/login");
            return false;
        }

        return true;
    }

    private boolean isLogin(HttpSession session) {
        return session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null;
    }
}
