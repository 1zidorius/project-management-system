package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {
    private final int id;
    private TaskStatus status;
    private String subject;
    private String description;
    private final Date createdOn;
    private Date updatedOn;
    private User assignee;
    private final User addedBy;
    private Project project;
    private TaskPriority taskPriority;
    private ArrayList<Task> subTasks = new ArrayList<>();

    public static class TaskBuilder {
        private int id;
        private static int taskCounter = 1;
        private TaskStatus status;
        private final String subject;
        private String description;
        private Date createdOn;
        private Date updatedOn;
        private User assignee;
        private final User addedBy; // need to somehow add default value as a constructor
        private final Project project;
        private TaskPriority taskPriority;
        private ArrayList<Task> subTasks = new ArrayList<>();

        public TaskBuilder(String subject, User addedBy, Project project) {
            this.id = taskCounter;
            this.subject = subject;
            this.addedBy = addedBy;
            this.status = TaskStatus.NEW;
            this.taskPriority = TaskPriority.MEDIUM;
            this.createdOn = new Date();
            this.project = project;
            taskCounter++;
        }

        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder assignee(User assignee) {
            this.assignee = assignee;
            return this;
        }

        public TaskBuilder priority(TaskPriority taskPriority) {
            this.taskPriority = taskPriority;
            return this;
        }


        public TaskBuilder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public TaskBuilder subTasks(Task subTask) {
            this.subTasks.add(subTask);
            return this;
        }

        public Task build() {
            Task task = new Task(this);
//            validateTaskObject(task);
            return task;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", status=" + status +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", assignee=" + assignee +
                ", addedBy=" + addedBy +
                ", project=" + project +
                ", priority=" + taskPriority +
                ", subTasks=" + subTasks +
                '}';
    }

    private Task(TaskBuilder builder) {
        this.id = builder.id;
        this.status = builder.status;
        this.subject = builder.subject;
        this.description = builder.description;
        this.createdOn = builder.createdOn;
        this.updatedOn = builder.updatedOn;
        this.assignee = builder.assignee;
        this.addedBy = builder.addedBy;
        this.project = builder.project;
        this.taskPriority = builder.taskPriority;
        this.subTasks = builder.subTasks;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public User getAssignee() {
        return assignee;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public Project getProject() {
        return project;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
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

    public void setStatus(TaskStatus status) {
        this.updatedOn = new Date();
        this.status = status;
    }

    public void setSubject(String subject) {
        this.updatedOn = new Date();
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.updatedOn = new Date();
        this.description = description;
    }

    public void setAssignee(User assignee) {
        this.updatedOn = new Date();
        this.assignee = assignee;
    }

    public void setProject(Project project) {
        this.updatedOn = new Date();
        this.project = project;
    }

    void setTaskPriority(TaskPriority taskPriority) {
        this.updatedOn = new Date();
        this.taskPriority = taskPriority;
    }

    void setSubTask(Task subTask) {
        this.subTasks.add(subTask);
    }

}

