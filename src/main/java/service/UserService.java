package service;

import dto.TaskDto;
import dto.UserDetailDto;
import dto.UserDto;
import model.RoleModel;
import model.UserModel;
import repository.RoleRepository;
import repository.TaskRepository;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository = new UserRepository();
    private RoleRepository roleRepository = new RoleRepository();
    private TaskRepository taskRepository = new TaskRepository();

    public List<UserDto> getAllUser() {
        return userRepository.findAll();
    }

    public boolean insertUser(String fullname, String email, String password, int role) {
        return userRepository.insertUser(fullname, email, password, role);
    }

    public List<RoleModel> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }

    public UserModel findById(int id) {
        return userRepository.findById(id);
    }

    public boolean updateUser(int id, String fullname, String email, String password, int role) {
        return userRepository.updateUser(id, fullname, email, password, role);
    }

    public UserDetailDto findByUserId(int userId) {
        return taskRepository.findByUserId(userId);
    }
}
