package com.dao;

import com.db.MongoMain;
import com.models.Task;
import com.models.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.dao.UserDao.fetchUsers;
import static com.dao.UserDao.getUserByUuid;


public class TaskDao extends MongoMain {
    private static MongoCollection<Document> collection = mongoDatabase.getCollection("tasks");

    public static void main(String[] args) {
        populateTasks();
    }

    private static void populateTasks() {
        ObservableList<User> users = fetchUsers();
        User user = users.get(0);
        User user1 = users.get(1);
        Task task = new Task("Android", "Fix visual bug", user, "3", null);
        Task task1 = new Task("Windows", "Fix bug where user cannot log out", user, "2", user);
        Task task2 = new Task("Windows", "Add new feature", user, "3", user1);
        addTask(task);
        addTask(task1);
        addTask(task2);
    }

    public static void addTask(Task task) {
        Document document = new Document("project", task.getProject());
        document.append("uuid", task.getUuid());
        document.append("subject", task.getSubject());
        document.append("addedBy", task.getAddedBy().getUuid());
        document.append("priority", task.getPriority());
        if (task.getAssignee() != null) {
            document.append("assignee", task.getAssignee().getUuid());
        }
        collection.insertOne(document);
    }

    public static void updateTask(Task task) {
        Document taskObj = collection.find(Filters.eq("uuid", task.getUuid())).first();
        if (taskObj != null) {
            Bson filter = new Document("uuid", task.getUuid());
            Bson newValue = new Document("project", task.getProject())
                    .append("subject", task.getSubject())
                    .append("priority", task.getPriority())
                    .append("assignee", task.getAssignee());
            Bson updateOperationDocument = new Document("$set", newValue);
            collection.updateMany(filter, updateOperationDocument);
        }
    }

    public static ObservableList<Task> fetchTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        FindIterable<Document> find = collection.find();
        for (Document taskObj : find) {
            Task task = getTask(taskObj);
            tasks.add(task);
        }
        return tasks;
    }

    private static Task getTask(Document taskObj) {
        String uuid = taskObj.getString("uuid");
        String project = taskObj.getString("project");
        String subject = taskObj.getString("subject");
        String addedByUuid = taskObj.getString("addedBy");
        String priority = taskObj.getString("priority");
        String assigneeUuid = taskObj.getString("assignee");
        User addedBy = getUserByUuid(addedByUuid);
        User assignee = getUserByUuid(assigneeUuid);
        return new Task(project, subject, addedBy, priority, assignee, uuid);
    }

    public static Task getTaskByUuid(String uuid) {
        Document found = (Document) collection.find(new Document("uuid", uuid)).first();
        if (found != null) {
            return getTask(found);
        }
        return null;
    }

    public static void deleteTask(Task task) {
        Document taskObj = collection.find(Filters.eq("uuid", task.getUuid())).first();
        collection.deleteOne(taskObj);
    }
}
