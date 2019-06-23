package com.example.goalranger.Models;

import com.google.gson.annotations.SerializedName;

public class Goal {
    private float owner_id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("begin_date")
    private String begin_date;
    @SerializedName("due_date")
    private String due_date;
    @SerializedName("level")
    private String level;
    private float goal_status;
    private String updated_at;
    private String created_at;
    @SerializedName("id")
    private int id;

    public Goal(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Goal(int id) {
        this.id = id;
    }

    public Goal() {
    }

    public Goal(String title, String description, String begin_date, String due_date, String level) {
        this.title = title;
        this.description = description;
        this.begin_date = begin_date;
        this.due_date = due_date;
        this.level = level;
    }

// Getter Methods

    public float getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(float owner_id) {
        this.owner_id = owner_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    // Setter Methods

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getGoal_status() {
        return goal_status;
    }

    public void setGoal_status(float goal_status) {
        this.goal_status = goal_status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
