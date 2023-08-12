package repository;

import config.MysqlConfig;
import model.JobModel;
import model.StatusModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository {

    public List<StatusModel> findAll() {
        Connection connection = null;
        List<StatusModel> statusModelList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from status";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));
                statusModelList.add(statusModel);
            }
        } catch (Exception e) {
            System.out.println("Error getStatus: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close getStatus: " + e.getMessage());
                }
            }
        }
        return statusModelList;
    }
}
