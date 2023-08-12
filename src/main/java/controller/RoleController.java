package controller;

import model.RoleModel;
import service.RoleService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "roleController", urlPatterns = {"/role","/role/add","/role/delete","/role/edit"})
public class RoleController extends HttpServlet {
    UserService userService = new UserService();
    RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                getAllRole(req, resp);
                break;
            case "/role/add":
                addRole(req, resp);
                break;
            case "/role/delete":
                deleteRole(req, resp);
                break;
            case "/role/edit":
                editRole(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                break;
            case "/role/add":
                addRole(req, resp);
                break;
            case "/role/edit":
                editRole(req, resp);
                break;
            default:
                break;
        }
    }

    private void getAllRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleModel> listRoles = userService.getAllRoles();

        req.setAttribute("listRoles", listRoles);
        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
    }

    private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getMethod();
        if(method.equalsIgnoreCase("post")){
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            boolean isSuccess = roleService.inserRole(name, description);
            if(isSuccess){
                resp.sendRedirect(req.getContextPath() + "/role");
                return;
            } else {
                System.out.println("Thêm thất bại");
            }
        }
        req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
    }

    private void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = roleService.deleteRole(id);
    }

    private void editRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getMethod();
        int id = Integer.parseInt(req.getParameter("id"));
        if(method.equalsIgnoreCase("post")){

            String name = req.getParameter("name");
            String description = req.getParameter("description");
            boolean isSuccess = roleService.updateRole(id, name, description);
            if(isSuccess){
                resp.sendRedirect(req.getContextPath() + "/role");
                return;
            } else {
                System.out.println("Sửa thất bại");
            }
        }

        RoleModel role = roleService.findById(id);

        req.setAttribute("isEdit", true);
        req.setAttribute("role", role);
        req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
    }
}
