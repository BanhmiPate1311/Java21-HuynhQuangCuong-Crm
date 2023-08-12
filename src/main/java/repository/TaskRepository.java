package repository;

import config.MysqlConfig;
import dto.TaskDto;
import dto.UserDetailDto;
import model.JobModel;
import model.TaskModel;
import model.UserModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public List<TaskDto> findAll() {
        Connection connection = null;
        List<TaskDto> taskDtoList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "SELECT t.*, u.fullname as user_name, j.name as job_name ,s.name as status_name FROM tasks t JOIN users u ON t.user_id = u.id JOIN jobs j ON t.job_id = j.id JOIN status s ON t.status_id = s.id";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                TaskDto taskDto = new TaskDto();
                taskDto.setId(resultSet.getInt("id"));
                taskDto.setName(resultSet.getString("name"));
                taskDto.setStartDay(resultSet.getDate("start_date"));
                taskDto.setEndDay(resultSet.getDate("end_date"));
                taskDto.setUserName(resultSet.getString("user_name"));
                taskDto.setJobName(resultSet.getString("job_name"));
                taskDto.setStatusDesc(resultSet.getString("status_name"));
                taskDtoList.add(taskDto);
            }
        } catch (Exception e) {
            System.out.println("Error getTasks: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close getTasks: " + e.getMessage());
                }
            }
        }
        return taskDtoList;
    }

    public boolean insertTask(String name, Date startDay, Date endDay, int userId, int jobId) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "insert into tasks(name, start_date, end_date, user_id,job_id,status_id) values(?,?,?,?,?,?)";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, name);
            stateMent.setDate(2, startDay);
            stateMent.setDate(3, endDay);
            stateMent.setInt(4, userId);
            stateMent.setInt(5, jobId);
            stateMent.setInt(6, 1);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

            /**
             * Hiển thị danh sách role từ database ra ma hình
             * Thế role id cứng thành role động người dùng nhập vào ở giao diện
             */
        } catch (Exception e) {
            System.out.println("Error insertTask: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close insertTask: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

    public boolean deleteById(int id) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "DELETE FROM tasks t WHERE t.id =?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            stateMent.setInt(1, id);
            isSuccess = stateMent.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error deleteById: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close deleteById: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

    public TaskModel findById(int id) {
        Connection connection = null;
        TaskModel taskModel = new TaskModel();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "SELECT * FROM tasks t WHERE t.id =?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            stateMent.setInt(1, id);
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));

                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = outputDateFormat.format(resultSet.getDate("start_date"));
                String formattedEndDate = outputDateFormat.format(resultSet.getDate("end_date"));

                taskModel.setStartDay(formattedStartDate);
                taskModel.setEndDay(formattedEndDate);
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setJobId(resultSet.getInt("job_id"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
            }
        } catch (Exception e) {
            System.out.println("Error findById: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close findById: " + e.getMessage());
                }
            }
        }
        return taskModel;
    }

    public boolean updateTask(int id, String name, Date startDay, Date endDay, int userId, int jobId, int statusId) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "UPDATE tasks t SET t.name = ?, t.start_date = ?, t.end_date = ?, t.user_id = ?, t.job_id = ?, t.status_id = ? WHERE t.id = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, name);
            stateMent.setDate(2, startDay);
            stateMent.setDate(3, endDay);
            stateMent.setInt(4, userId);
            stateMent.setInt(5, jobId);
            stateMent.setInt(6, statusId);
            stateMent.setInt(7, id);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

            /**
             * Hiển thị danh sách role từ database ra ma hình
             * Thế role id cứng thành role động người dùng nhập vào ở giao diện
             */
        } catch (Exception e) {
            System.out.println("Error updateTask: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close updateTask: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

    public UserDetailDto findByUserId(int userId) {
        Connection connection = null;
        UserDetailDto userDetailDto = new UserDetailDto();
        List<TaskDto> taskDtoList = new ArrayList<>();

        try {
            connection = MysqlConfig.getConnection();
            String userSql = "SELECT fullname as user_name, email as email, avatar as avatar FROM users WHERE id = ?";
            PreparedStatement userStatement = connection.prepareStatement(userSql);
            userStatement.setInt(1, userId);
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                userDetailDto.setUserName(userResult.getString("user_name"));
                userDetailDto.setUserEmail(userResult.getString("email"));
                userDetailDto.setUserAvatar(userResult.getString("avatar"));
            }

            String taskSql = "SELECT t.*, j.name as job_name, s.id as status_id FROM tasks t INNER JOIN jobs j ON t.job_id = j.id INNER JOIN status s ON t.status_id = s.id WHERE t.user_id = ?";
            PreparedStatement taskStatement = connection.prepareStatement(taskSql);
            taskStatement.setInt(1, userId);
            ResultSet taskResult = taskStatement.executeQuery();

            while (taskResult.next()) {
                TaskDto taskDto = new TaskDto();
                taskDto.setId(taskResult.getInt("id"));
                taskDto.setName(taskResult.getString("name"));
                taskDto.setStartDay(taskResult.getDate("start_date"));
                taskDto.setEndDay(taskResult.getDate("end_date"));
                taskDto.setJobName(taskResult.getString("job_name"));
                taskDto.setStatusId(taskResult.getInt("status_id"));
                taskDtoList.add(taskDto);
            }

            userDetailDto.setTaskDtoList(taskDtoList);

        } catch (Exception e) {
            System.out.println("Error findByUserId: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close findByUserId: " + e.getMessage());
                }
            }
        }

        return userDetailDto;
    }
}
