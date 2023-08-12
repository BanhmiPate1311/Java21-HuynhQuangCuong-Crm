package filter;

import model.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user","/task", "/task/add", "/task/delete","/task/edit","/task/details"})
public class LeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        if (user != null && (user.getRoleId() == 1 || user.getRoleId() == 2)) {
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
