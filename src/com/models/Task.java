package com.models;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Task {
    private final String defaultTaskStatus = "New";
    private final String defaultTaskPriority = "3";
    private String uuid;
    private SimpleStringProperty subject;
    private SimpleStringProperty status, priority;
    private LocalDate createdOn, updatedOn;
    private User addedBy;
    private SimpleStringProperty project;
    private User assignee;
    private ArrayList<Task> subTasks = new ArrayList<>();

    public Task(String project, String subject, User addedBy, String priority, User assignee) {
        this.uuid = UUID.randomUUID().toString();
        this.subject = new SimpleStringProperty(subject);
        this.status = new SimpleStringProperty(defaultTaskStatus);
        if (priority != null) {
            this.priority = new SimpleStringProperty(priority);
        } else {
            this.priority = new SimpleStringProperty(defaultTaskPriority);
        }
        this.project = new SimpleStringProperty(project);
        this.addedBy = addedBy;
        this.assignee = assignee;
        this.createdOn = LocalDate.now();
    }

    public Task(String project, String subject, User addedBy, String priority, User assignee, String uuid) {
        this.uuid = uuid;
        this.subject = new SimpleStringProperty(subject);
        this.status = new SimpleStringProperty(defaultTaskStatus);
        this.priority = new SimpleStringProperty(priority);
        this.project = new SimpleStringProperty(project);
        this.addedBy = addedBy;
        this.assignee = assignee;
        this.createdOn = LocalDate.now();
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getPriority() {
        return priority.get();
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getProject() {
        return project.get();
    }

    public SimpleStringProperty projectProperty() {
        return project;
    }

    public void setProject(String project) {
        this.project.set(project);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> all = new ArrayList<>();
        for (Task t : subTasks) {
            all.addAll(t.getAllTasks());
        }
        return all;
    }

    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Task{" +
                "defaultTaskStatus='" + defaultTaskStatus + '\'' +
                ", defaultTaskPriority='" + defaultTaskPriority + '\'' +
                ", uuid=" + uuid +
                ", project=" + project +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", addedBy=" + addedBy +
                ", assignee=" + assignee +
                '}';
    }
}
