import model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Yêu cầu sử dụng giao diện và trả giao diện ra browser cho client
//        int a = 5;
//        int b = 10;
//        int result = a + b;
//        req.setAttribute("kq", result);  //Servlet truyền biến ra cho file jsp


        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    // Định nghĩa một đường dẫn /signup
    // Khi gõ vào đường dẫn "Đây là page đăng ký"


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // nếu username la nguyenvana@gmail.com
        // Xuất ra hello với giá trị của tham số nhận
        String message = "";
        if(username.toLowerCase().equals("nguyenvana@gmail.com")){
            message = "Hello " + username;
        }
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);

        List<String>list= Arrays.asList("CyberSoft","Java21","JSP");

        req.setAttribute("msg",message);
        req.setAttribute("user",users);
        req.setAttribute("list",list);
        // Tìm hiểu JSTL hển thị giá trị của list ra jsp
        req.getRequestDispatcher("login.jsp").forward(req,resp);
//        PrintWriter writer = resp.getWriter();
//        writer.println(message);
    }
}
