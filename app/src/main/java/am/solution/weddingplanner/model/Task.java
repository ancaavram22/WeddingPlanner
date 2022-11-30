package am.solution.weddingplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String taskUser;
    private String taskTitle;
    private String taskDescription;
    private String taskDate;
    private String taskTime;
    private String taskEvent;



    public Task(String taskUser, String taskTitle, String taskDescription, String taskDate, String taskTime, String taskEvent) {
        this.taskUser = taskUser;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskEvent = taskEvent;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTaskUser() { return taskUser; }

    public void setTaskUser(String username) {
        this.taskUser = username;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String title) { this.taskDescription = title; }

    public String getTaskDescription() {return taskDescription; }

    public void setTaskDescription(String description) { this.taskDescription = description; }

    public String getTaskDate() { return taskDate; }

    public void setTaskDate(String date) { this.taskDate = date; }

    public String getTaskTime() {return taskTime;}

    public void setTaskTime(String time) {this.taskTime = time;}

    public String getTaskEvent() {return taskEvent;}

    public void setTaskEvent(String event) {this.taskEvent = event;}

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskUser='" + taskUser + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskDate='" + taskDate + '\'' +
                ", taskTime='" + taskTime + '\'' +
                ", taskEvent='" + taskEvent + '\'' +
                '}';
    }
}
