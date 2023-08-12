package repository;

import config.MysqlConfig;
import dto.UserDto;
import model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public UserModel findByEmailAndPassword(String email, String password) {
        // Tạo kết nối đến database
        Connection connection = null;
        UserModel userModel = new UserModel();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from users u where u.email = ? and u.password = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, email);
            stateMent.setString(2, password);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {

                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));

            }
        } catch (Exception e) {
            System.out.println("Error findByEmailAndPassword: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close findByEmailAndPassword: " + e.getMessage());
                }
            }
        }
        return userModel;
    }

    public List<UserDto> findAll() {
        // Tạo kết nối đến database
        Connection connection = null;
        List<UserDto> userDtoList = new ArrayList<>();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "SELECT u.*, r.description FROM users u JOIN roles r ON u.role_id = r.id";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                UserDto userDto = new UserDto();
                userDto.setId(resultSet.getInt("id"));
                userDto.setEmail(resultSet.getString("email"));
                userDto.setPassword(resultSet.getString("password"));
                userDto.setFullname(resultSet.getString("fullname"));
                userDto.setAvatar(resultSet.getString("avatar"));
                userDto.setRole_desc(resultSet.getString("description"));
                userDtoList.add(userDto);
            }
        } catch (Exception e) {
            System.out.println("Error findAll: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close findAll: " + e.getMessage());
                }
            }
        }
        return userDtoList;
    }

    public boolean insertUser(String fullname, String email, String password, int roleId) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "insert into users(fullname, email, password, role_id) values(?,?,?,?)";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, fullname);
            stateMent.setString(2, email);
            stateMent.setString(3, password);
            stateMent.setInt(4, roleId);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

            /**
             * Hiển thị danh sách role từ database ra ma hình
             * Thế role id cứng thành role động người dùng nhập vào ở giao diện
             */
        } catch (Exception e) {
            System.out.println("Error insertUser: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close insertUser: " + e.getMessage());
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
            String sql = "DELETE FROM users u WHERE u.id =?";
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

    public UserModel findById(int id) {
        // Tạo kết nối đến database
        Connection connection = null;
        UserModel userModel = new UserModel();
        try {
            connection = MysqlConfig.getConnection();
            String sql = "select * from users u where u.id = ? ";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setInt(1, id);
            // Thực thi câu query
            ResultSet resultSet = stateMent.executeQuery();
            while (resultSet.next()) {
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setFullname(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));

            }
        } catch (Exception e) {
            System.out.println("Error findByEmailAndPassword: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close findByEmailAndPassword: " + e.getMessage());
                }
            }
        }
        return userModel;
    }

    public boolean updateUser(int id, String fullname, String email, String password, int roleId) {
        Connection connection = null;
        boolean isSuccess = false;
        try {
            connection = MysqlConfig.getConnection();
            String sql = "UPDATE users u SET u.fullname = ?, u.email = ?, u.password = ?, u.role_id = ? WHERE u.id = ?";
            PreparedStatement stateMent = connection.prepareStatement(sql);
            stateMent.setString(1, fullname);
            stateMent.setString(2, email);
            stateMent.setString(3, password);
            stateMent.setInt(4, roleId);
            stateMent.setInt(5, id);
            // Thực thi câu query
            isSuccess = stateMent.executeUpdate() > 0;

            /**
             * Hiển thị danh sách role từ database ra ma hình
             * Thế role id cứng thành role động người dùng nhập vào ở giao diện
             */
        } catch (Exception e) {
            System.out.println("Error updateUser: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error close updateUser: " + e.getMessage());
                }
            }
        }
        return isSuccess;
    }

}
