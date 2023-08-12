package repository;

import config.MysqlConfig;
import model.RoleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    public List<RoleModel> findAll() {
        Connection connection = null;
        List<RoleModel> roleModelList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from roles";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
                roleModelList.add(roleModel);
            }
        } catch (Exception e) {
            System.out.println("Error getRole: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close getRole: " + e.getMessage());
                }
            }
        }
        return roleModelList;
    }

    public boolean insertRole(String name, String description) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "insert into roles(name, description) values(?,?)";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, name);
            stateMent.setString(2, description);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error insertRole: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close insertRole: " + e.getMessage());
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
            String sql = "DELETE FROM roles r WHERE r.id =?";
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

    public RoleModel findById(int id){
        Connection connection = null;
        RoleModel roleModel = new RoleModel();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "SELECT * FROM roles r WHERE r.id =?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            stateMent.setInt(1, id);
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
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
        return roleModel;
    }

    public boolean updateRole (int id, String name, String description){
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "UPDATE roles r SET r.name = ?, r.description = ? WHERE r.id = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            stateMent.setString(1, name);
            stateMent.setString(2, description);
            stateMent.setInt(3, id);
            isSuccess = stateMent.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updateRole: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close updateRole: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

}
