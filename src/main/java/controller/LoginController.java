package controller;

import config.MysqlConfig;
import model.UserModel;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //cookie
//        Cookie cookie = new Cookie("username","nguyenvana");
//        //Yêu cầu client khởi tạo cookie
//        resp.addCookie(cookie);
//        Cookie[] cookies = req.getCookies();
//        for (Cookie item : cookies) {
//            if(item.getName().equals("username")){
//                System.out.println("Kiểm tra " + item.getValue());
//            }
//        }

//        HttpSession session = req.getSession();
//        session.setAttribute("password", "123456");
//
//        System.out.println("Session: " + session.getAttribute("password"));

        Cookie[] cookies = req.getCookies();
        String userName = "";
        String password = "";

        for (Cookie item : cookies) {
            if (item.getName().equals("username")) {
                userName = item.getValue();
            }

            if (item.getName().equals("password")) {
                password = item.getValue();
            }
        }

        req.setAttribute("username", userName);
        req.setAttribute("password", password);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    /**
     * Tạo ra checkbox nh mật khẩu, khi người dùng click chọn checkbox thì khi đăng nhập thành công sẽ lưu lại giá trị đăng nhập là email, password
     * Khi quay lại màn hình login thì tự động điền email và password vào
     *
     * @param req
     * @param resp
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String email = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        UserModel user = loginService.checkLogin(email, password);
        System.out.println("user: " + user.getEmail());
        if (user.getEmail() != null) {
            HttpSession session = req.getSession();
            // đặt thời gian sống cho session
            session.setMaxInactiveInterval(60 * 60 * 1);
            session.setAttribute("user", user);
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/index");
        } else {
//            PrintWriter writer = resp.getWriter();
//            writer.println("Login fail");
//            writer.close();
            String errorMessage = "Sai email hoặc mật khẩu";
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
