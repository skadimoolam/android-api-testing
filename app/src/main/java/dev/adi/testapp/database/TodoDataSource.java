package dev.adi.testapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dev.adi.testapp.adapter.TodoListAdapter;
import dev.adi.testapp.model.TodoItem;

public class TodoDataSource {

    private Context context;
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    public static final String TODO_SERVICE_MESSAGE = "TodoDataSource";
    public static final String TODO_SERVICE_PAYLOAD = "TodoDataSourcePayload";

    public TodoDataSource(Context context) {
        this.context = context;

        dbHelper = new DBHelper(this.context);
        db = dbHelper.getWritableDatabase();
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long getTodosCount() {
        return DatabaseUtils.queryNumEntries(db, TodoItemsTable.TABLE_NAME);
    }

    public TodoItem createTodo(TodoItem item) {
        ContentValues values = item.toValues();
        db.insert(TodoItemsTable.TABLE_NAME, null, values);
        sendDataChangedMessage();
        return item;
    }

    public List<TodoItem> getAllTodos() {
        List<TodoItem> dataItems = new ArrayList<>();
        Cursor cursor = db.query(TodoItemsTable.TABLE_NAME, TodoItemsTable.ALL_COLUMNS, null, null, null, null, TodoItemsTable.COLUMN_POSITION + "," + TodoItemsTable.COLUMN_GROUP);

        while (cursor.moveToNext()) {
            TodoItem item = new TodoItem();
            item.setTodoId(cursor.getString(cursor.getColumnIndex(TodoItemsTable.COLUMN_ID)));
            item.setTodoName(cursor.getString(cursor.getColumnIndex(TodoItemsTable.COLUMN_NAME)));
            item.setTodoGroup(cursor.getString(cursor.getColumnIndex(TodoItemsTable.COLUMN_GROUP)));
            item.setTodoPosition(cursor.getInt(cursor.getColumnIndex(TodoItemsTable.COLUMN_POSITION)));
            item.setTodoCompleted(cursor.getInt(cursor.getColumnIndex(TodoItemsTable.COLUMN_COMPLETED)));
            item.setTodoDeleted(cursor.getInt(cursor.getColumnIndex(TodoItemsTable.COLUMN_DELETED)));

            dataItems.add(item);
        }

        return dataItems;
    }

    public void deleteTodo(String id) {
        String[] queryParam = { id };
        db.delete(TodoItemsTable.TABLE_NAME, TodoItemsTable.COLUMN_ID + " = ?", queryParam);
        sendDataChangedMessage();
    }

    public void completeTodo(TodoItem item) {
        String[] queryParam = { item.getTodoId() };

        db.update(TodoItemsTable.TABLE_NAME, item.toValues(), TodoItemsTable.COLUMN_ID + " = ?", queryParam);

        sendDataChangedMessage();
    }

    public void updateTodo(TodoItem item) {
        String[] queryParam = { item.getTodoId() };

        db.update(TodoItemsTable.TABLE_NAME, item.toValues(), TodoItemsTable.COLUMN_ID + " = ?", queryParam);

        sendDataChangedMessage();
    }

    private void sendDataChangedMessage() {
//        for (TodoItem item: getAllTodos()) {
//            Log.w("TodoDataSource", item.toString());
//        }
        Intent messageIntent = new Intent(TODO_SERVICE_MESSAGE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(messageIntent);
    }
}