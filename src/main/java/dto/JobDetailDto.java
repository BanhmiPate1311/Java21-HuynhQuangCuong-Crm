package dto;

import java.util.List;

public class JobDetailDto {
    private String leaderName;
    private String leaderAvatar;
    private List<TaskDto> taskDtoList;

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderAvatar() {
        return leaderAvatar;
    }

    public void setLeaderAvatar(String leaderAvatar) {
        this.leaderAvatar = leaderAvatar;
    }

    public List<TaskDto> getTaskDtoList() {
        return taskDtoList;
    }

    public void setTaskDtoList(List<TaskDto> taskDtoList) {
        this.taskDtoList = taskDtoList;
    }
}
