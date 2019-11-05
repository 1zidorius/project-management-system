package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Task {
    private final String defaultTaskStatus = "New";
    private final String defaultTaskPriority = "3";

    private SimpleStringProperty subject;
    private SimpleStringProperty status, priority;
    private LocalDate createdOn, updatedOn;
    private SimpleStringProperty addedBy;
    private SimpleStringProperty project;
    private User assignee;
    private ArrayList<Task> subTasks = new ArrayList<>();

    public Task(String project, String subject, String addedBy, String priority, User assignee) {
        this.subject = new SimpleStringProperty(subject);
        this.status = new SimpleStringProperty(defaultTaskStatus);
        if (priority != null) {
            this.priority = new SimpleStringProperty(priority);
        } else {
            this.priority = new SimpleStringProperty(defaultTaskPriority);
        }
        this.project = new SimpleStringProperty(project);
        this.addedBy = new SimpleStringProperty(addedBy);
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

    public String getAddedBy() {
        return addedBy.get();
    }

    public SimpleStringProperty addedByProperty() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy.set(addedBy);
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
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
}
