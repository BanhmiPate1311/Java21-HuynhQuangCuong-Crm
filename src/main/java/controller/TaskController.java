package controller;

import dto.UserDto;
import model.JobModel;
import model.StatusModel;
import model.TaskModel;
import service.JobService;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "taskController", urlPatterns = {"/task", "/task/add", "/task/delete","/task/edit"})
public class TaskController extends HttpServlet {

    TaskService taskService = new TaskService();

    UserService userService = new UserService();

    JobService jobService = new JobService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        Lấy đường dẫn servlet người dùng gọi trên browser
        String path = req.getServletPath();
        switch (path) {
            case "/task":
                getAllTasks(req, resp);
                break;
            case "/task/add":
                addTask(req, resp);
                break;
            case "/task/delete":
                deleteTask(req, resp);
                break;
            case "/task/edit":
                editTask(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/task":
                break;
            case "/task/add":
                addTask(req, resp);
                break;
            case "/task/edit":
                editTask(req, resp);
                break;
            default:
                break;
        }
    }

    public void getAllTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<UserDto> userList = userService.getAllUser();
//        List<JobModel> jobList = jobService.getAllJobs();
//        List<StatusModel> statusList = taskService.getAllStatus();
        req.setAttribute("taskList", taskService.getAllTasks());
//        req.setAttribute("userList", userList);
//        req.setAttribute("jobList", jobList);
//        req.setAttribute("statusList", statusList);
        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }

    public void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String errorMessage = null;
        String method = req.getMethod();
        List<UserDto> userList = userService.getAllUser();
        List<JobModel> jobList = jobService.getAllJobs();
        if(method.equalsIgnoreCase("post")){
            String name = req.getParameter("name");
            String startDay = req.getParameter("start_day");
            String endDay = req.getParameter("end_day");
            int userId = Integer.parseInt(req.getParameter("user_id"));
            int jobId = Integer.parseInt(req.getParameter("job_id"));


            // Định dạng ngày tháng đầu vào
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date startDate = new Date(inputDateFormat.parse(startDay).getTime());
                Date endDate = new Date(inputDateFormat.parse(endDay).getTime());

                if (startDate.after(endDate)) {
                    errorMessage = "Ngày bắt đầu không được lớn hơn ngày kết thúc.";
                } else {
                    boolean isSuccess = taskService.insertTask(name, startDate, endDate, userId, jobId);
                    if (isSuccess) {
                        resp.sendRedirect(req.getContextPath() + "/task");
                        return;
                    } else {
                        System.out.println("Thêm thất bại");
                    }
                }
            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
                errorMessage = "Error parsing date: " + e.getMessage();
            }
        }
        req.setAttribute("userList", userList);
        req.setAttribute("jobList", jobList);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
    }

    public void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println("id: " + id);
        boolean isSuccess = taskService.deleteTask(id);
    }

    public void editTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String errorMessage = null;
        int id = Integer.parseInt(req.getParameter("id"));
        String method = req.getMethod();

        if(method.equalsIgnoreCase("post")){
            String name = req.getParameter("name");
            String startDay = req.getParameter("start_day");
            String endDay = req.getParameter("end_day");
            int userId = Integer.parseInt(req.getParameter("user_id"));
            int jobId = Integer.parseInt(req.getParameter("job_id"));
            int statusId = Integer.parseInt(req.getParameter("status_id"));

            // Định dạng ngày tháng đầu vào
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date startDate = new Date(inputDateFormat.parse(startDay).getTime());
                Date endDate = new Date(inputDateFormat.parse(endDay).getTime());

                if (startDate.after(endDate)) {
                    errorMessage = "Ngày bắt đầu không được lớn hơn ngày kết thúc.";
                    req.setAttribute("errorMessage", errorMessage);

                } else {
                    boolean isSuccess = taskService.updateTask(id, name, startDate, endDate, userId, jobId, statusId);
                    if (isSuccess) {
                        resp.sendRedirect(req.getContextPath() + "/task");
                        return;
                    } else {
                        System.out.println("Thêm thất bại");
                    }
                }
            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }
        List<UserDto> userList = userService.getAllUser();
        List<JobModel> jobList = jobService.getAllJobs();
        List<StatusModel> statusList = taskService.getAllStatus();
        TaskModel task = taskService.findById(id);
        System.out.println("taskstaday: " + task.getStartDay());
        req.setAttribute("task", task);
        req.setAttribute("isEdit", true);
        req.setAttribute("userList", userList);
        req.setAttribute("jobList", jobList);
        req.setAttribute("statusList", statusList);

        req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
    }
}
