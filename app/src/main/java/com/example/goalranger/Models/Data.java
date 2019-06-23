package com.example.goalranger.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {
    @SerializedName("task")
    Task TaskObject;
    @SerializedName("data")
    private Data data;
    @SerializedName("success")
    private boolean success;
    @SerializedName("user")
    private User UserObject;
    @SerializedName("token")
    private String token;
    @SerializedName("goal")
    private Goal goalObject;
    @SerializedName("goals")
    private ArrayList<Goal> goalArrayList;
    @SerializedName("message")
    private String message;

    public Data() {

    }

    public Data(String name, String email, String phone) {
    }

    public Data(String token, User userObject) {
        this.UserObject = userObject;
        this.token = token;
    }

    public Data(Data data, ArrayList<Goal> goalArrayList) {
        this.data = data;
        goalArrayList = goalArrayList;
    }

    public Goal getGoalObject() {
        return goalObject;
    }

    public void setGoalObject(Goal goalObject) {
        this.goalObject = goalObject;
    }

    public ArrayList<Goal> getGoalArrayList() {
        return goalArrayList;
    }

    public void setGoalArrayList(ArrayList<Goal> goalArrayList) {
        this.goalArrayList = goalArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Task getTask() {
        return TaskObject;
    }

    public void setTask(Task taskObject) {
        this.TaskObject = taskObject;
    }

    public User getUserObject() {
        return UserObject;
    }

    public void setUserObject(User userObject) {
        UserObject = userObject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    // Setter Methods

    public User getUser() {
        return UserObject;
    }

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }

}
