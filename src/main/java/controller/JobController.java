package controller;

import model.JobModel;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "jobController", urlPatterns = {"/groupwork","/groupwork/add","/groupwork/delete","/groupwork/edit","/groupwork/details"})
public class JobController extends HttpServlet {

    JobService jobService = new JobService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        Lấy đường dẫn servlet người dùng gọi trên browser
        String path = req.getServletPath();
        switch (path) {
            case "/groupwork":
                getAllJobs(req, resp);
                break;
            case "/groupwork/add":
                addJob(req, resp);
                break;
            case "/groupwork/delete":
                deleteJob(req, resp);
                break;
            case "/groupwork/edit":
                editJob(req, resp);
                break;
            case "/groupwork/details":
                jobDetail(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/groupwork":
                break;
            case "/groupwork/add":
                addJob(req, resp);
                break;
            case "/groupwork/edit":
                editJob(req, resp);
                break;
            default:
                break;
        }
    }

    public void getAllJobs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listJobs", jobService.getAllJobs());
        req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
    }

    public void addJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getMethod();
        String errorMessage = null;
        if(method.equalsIgnoreCase("post")){
            String name = req.getParameter("name");
            String startDay = req.getParameter("start_day");
            String endDay = req.getParameter("end_day");

            // Định dạng ngày tháng đầu vào
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date startDate = new Date(inputDateFormat.parse(startDay).getTime());
                Date endDate = new Date(inputDateFormat.parse(endDay).getTime());

                if (startDate.after(endDate)) {
                    errorMessage = "Ngày bắt đầu không được lớn hơn ngày kết thúc.";
                } else {
                    boolean isSuccess = jobService.insertJob(name, startDate, endDate);
                    if (isSuccess) {
                        resp.sendRedirect(req.getContextPath() + "/groupwork");
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
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("/groupwork-add.jsp").forward(req, resp);
    }

    public void deleteJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = jobService.deleteJob(id);

    }

    private void editJob(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String method = req.getMethod();

        if(method.equalsIgnoreCase("post")){
            String name = req.getParameter("name");
            String startDay = req.getParameter("start_day");
            String endDay = req.getParameter("end_day");

            // Định dạng ngày tháng đầu vào
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date startDate = new Date(inputDateFormat.parse(startDay).getTime());
                Date endDate = new Date(inputDateFormat.parse(endDay).getTime());

                if (startDate.after(endDate)) {
                    req.setAttribute("errorMessage", "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                } else {
                    boolean isSuccess = jobService.updateJob(id, name, startDate, endDate);
                    if (isSuccess) {
                        resp.sendRedirect(req.getContextPath() + "/groupwork");
                        return;
                    } else {
                        System.out.println("Cập nhật thất bại");
                    }
                }

            } catch (ParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
                req.setAttribute("errorMessage", "Error parsing date: " + e.getMessage());
            }
        }

        JobModel job = jobService.findById(id);
        req.setAttribute("isEdit", true);
        req.setAttribute("job", job);
        req.getRequestDispatcher("/groupwork-add.jsp").forward(req, resp);
    }

    public void jobDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(req.getParameter("id"));
        JobModel job = jobService.findById(id);
        req.setAttribute("job", job);
        req.getRequestDispatcher("/groupwork-details.jsp").forward(req, resp);
    }
}
