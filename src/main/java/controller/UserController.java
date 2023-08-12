package controller;

import dto.TaskDto;
import dto.UserDetailDto;
import dto.UserDto;
import model.RoleModel;
import model.UserModel;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(name = "userController", urlPatterns = {"/user", "/user/add", "/user/delete", "/user/edit","/user/details"})
public class UserController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Lấy đường dẫn servlet người dùng gọi trên browser
        String path = req.getServletPath();
        switch (path) {
            case "/user":
                getAllUser(req, resp);
                break;
            case "/user/add":
                addUser(req, resp);
                break;
            case "/user/delete":
                deleteUser(req, resp);
                break;
            case "/user/edit":
                editUser(req, resp);
                break;
            case "/user/details":
                userDetail(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user":
                break;
            case "/user/add":
                addUser(req, resp);
                break;
            case "/user/edit":
                editUser(req, resp);
                break;
            default:
                break;
        }
    }

    private void getAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> list = userService.getAllUser();
//        List<RoleModel> listRoles = userService.getAllRoles();
        req.setAttribute("listUsers", list);
//        req.setAttribute("listRoles", listRoles);
        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String method = req.getMethod();
        List<RoleModel> listRoles = userService.getAllRoles();
        String errorMessage = null;
        if (method.equalsIgnoreCase("post")) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("role"));
            // Kiểm tra password có rỗng hay không
            if (password.isEmpty()) {
                errorMessage = "Password cannot be empty.";
            } else {
                boolean isSuccess = userService.insertUser(fullname, email, password, roleId);
                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/user");
                    return;
                } else {
                    System.out.println("Thêm thất bại");
                }
            }
        }
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("listRoles", listRoles);
        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = userService.deleteUser(id);
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String method = req.getMethod();

        if (method.equalsIgnoreCase("post")) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            int roleId = Integer.parseInt(req.getParameter("role"));
            if (password.isEmpty()) {
                String message = "Password cannot be empty.";
            } else {
                boolean isSuccess = userService.updateUser(id, fullname, email, password, roleId);
                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/user");
                    return;
                } else {
                    System.out.println("Cập nhật thất bại");
                }
            }
        }

        UserModel userModel = userService.findById(id);
        List<RoleModel> listRoles = userService.getAllRoles();
        req.setAttribute("isEdit", true);
        req.setAttribute("user", userModel);
        req.setAttribute("listRoles", listRoles);

        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
    }

    private void userDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        UserDetailDto userDetail = userService.findByUserId(id);
        req.setAttribute("userDetail", userDetail);
        req.getRequestDispatcher("/user-details.jsp").forward(req, resp);
    }
}
