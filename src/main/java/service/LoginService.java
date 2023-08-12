package service;

import model.UserModel;
import repository.UserRepository;

import java.util.List;

public class LoginService {

    private UserRepository userRepository = new UserRepository();
    public UserModel checkLogin(String email, String password) {
        UserModel user = userRepository.findByEmailAndPassword(email, password);
        return user;
    }

    /**
     * Tạo ra một link /user load giao diện user-table.jsp
     * UserController lấy được danh sách user
     */

}
