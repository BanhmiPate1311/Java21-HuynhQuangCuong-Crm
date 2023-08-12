package service;

import dto.TaskDto;
import model.RoleModel;
import model.StatusModel;
import model.TaskModel;
import repository.StatusRepository;
import repository.TaskRepository;

import java.sql.Date;
import java.util.List;

public class TaskService {

    private TaskRepository taskRepository = new TaskRepository();
    private StatusRepository statusRepository = new StatusRepository();

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll();
    }

    public  boolean insertTask(String name, Date startDay, Date endDay, int userId, int jobId) {
        return taskRepository.insertTask(name, startDay, endDay, userId, jobId);
    }

    public List<StatusModel> getAllStatus() {
        return statusRepository.findAll();
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteById(id);
    }

    public TaskModel findById(int id) {
        return taskRepository.findById(id);
    }

    public boolean updateTask(int id, String name, Date startDay, Date endDay, int userId, int jobId, int statusId) {
        return taskRepository.updateTask(id, name, startDay, endDay, userId, jobId, statusId);
    }

}
