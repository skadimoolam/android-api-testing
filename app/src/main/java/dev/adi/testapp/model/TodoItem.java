package dev.adi.testapp.model;

import android.content.ContentValues;

import java.util.UUID;

import dev.adi.testapp.database.TodoItemsTable;

public class TodoItem {

    private String todoId, todoName, todoGroup;
    private int todoPosition, todoCompleted, todoDeleted;

    public TodoItem() {}

    public TodoItem(String todoId, String todoName, String todoGroup, int todoPosition, int todoCompleted, int todoDeleted) {
        this.todoId = todoId;

        if (todoId == null) {
            this.todoId = UUID.randomUUID().toString();
        }

        this.todoName = todoName;
        this.todoGroup = todoGroup;
        this.todoPosition = todoPosition;
        this.todoCompleted = todoCompleted;
        this.todoDeleted = todoDeleted;
    }

    public int getTodoDeleted() {
        return todoDeleted;
    }

    public void setTodoDeleted(int todoDeleted) {
        this.todoDeleted = todoDeleted;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getTodoGroup() {
        return todoGroup;
    }

    public void setTodoGroup(String todoGroup) {
        this.todoGroup = todoGroup;
    }

    public int getTodoPosition() {
        return todoPosition;
    }

    public void setTodoPosition(int todoPosition) {
        this.todoPosition = todoPosition;
    }

    public int getTodoCompleted() {
        return todoCompleted;
    }

    public void setTodoCompleted(int todoCompleted) {
        this.todoCompleted = todoCompleted;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(TodoItemsTable.COLUMN_ID, todoId);
        values.put(TodoItemsTable.COLUMN_NAME, todoName);
        values.put(TodoItemsTable.COLUMN_GROUP, todoGroup);
        values.put(TodoItemsTable.COLUMN_POSITION, todoPosition);
        values.put(TodoItemsTable.COLUMN_COMPLETED, todoCompleted);
        values.put(TodoItemsTable.COLUMN_DELETED, todoDeleted);

        return values;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                ", id=" + todoId +
                ", name=" + todoName +
                ", group=" + todoGroup +
                ", pos=" + todoPosition +
                ", comp=" + todoCompleted +
                ", del=" + todoDeleted + "}";
    }
}
