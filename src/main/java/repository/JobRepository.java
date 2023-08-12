package repository;

import config.MysqlConfig;
import model.JobModel;
import model.RoleModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {

    public List<JobModel> findAll() {
        Connection connection = null;
        List<JobModel> jobModelList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from jobs";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));

                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedStartDate = outputDateFormat.format(resultSet.getDate("start_date"));
                String formattedEndDate = outputDateFormat.format(resultSet.getDate("end_date"));

                jobModel.setStart_date(formattedStartDate);
                jobModel.setEnd_date(formattedEndDate);
                jobModelList.add(jobModel);
            }
        } catch (Exception e) {
            System.out.println("Error getJob: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close getJob: " + e.getMessage());
                }
            }
        }
        return jobModelList;
    }

    public boolean insertJob(String name, Date startDay, Date endDay) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "insert into jobs(name, start_date, end_date) values(?,?,?)";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, name);
            stateMent.setDate(2, startDay);
            stateMent.setDate(3, endDay);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

            /**
             * Hiển thị danh sách role từ database ra màn hình
             * Thế role id cứng thành role động người dùng nhập vào ở giao diện
             */
        } catch (Exception e) {
            System.out.println("Error insertJob: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close insertJob: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

    public boolean deleteById(int id){
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "DELETE FROM jobs j WHERE j.id =?";
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

    public JobModel findById(int id) {
        Connection connection = null;
        JobModel jobModel = new JobModel();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from jobs where id = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setInt(1, id);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));

                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = outputDateFormat.format(resultSet.getDate("start_date"));
                String formattedEndDate = outputDateFormat.format(resultSet.getDate("end_date"));

                jobModel.setStart_date(formattedStartDate);
                jobModel.setEnd_date(formattedEndDate);
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
        return jobModel;
    }

    public boolean updateJob(int id, String name, Date startDay, Date endDay) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "UPDATE jobs j SET j.name = ?, j.start_date = ?, j.end_date = ? WHERE j.id = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, name);
            stateMent.setDate(2, startDay);
            stateMent.setDate(3, endDay);
            stateMent.setInt(4, id);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updateJob: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close updateJob: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }
}
