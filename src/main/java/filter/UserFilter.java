package filter;

import model.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/profile","/profile/edit"})
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        if (user != null && user.getEmail() != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
