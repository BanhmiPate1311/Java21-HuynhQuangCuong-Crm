package filter;

import model.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// urlPatterns: Khi người dùng gọi link được quy định trong đây thì filter được kích hoạt
@WebFilter(urlPatterns = {"/login"})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Nơi quy định rule
        System.out.println("Filter đã được kích hoạt");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        if (user != null && user.getEmail() != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }

    private boolean isUrlAllowed(String requestedUrl) {
        // Kiểm tra xem đường link có được khai báo trong danh sách cho phép hay không
        // Trả về true nếu được phép, false nếu không được phép

        // Ví dụ:
        List<String> allowedUrls = Arrays.asList("/login", "/task", "/about");

        return allowedUrls.contains(requestedUrl);
    }
}
