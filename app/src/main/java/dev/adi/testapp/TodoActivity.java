package dev.adi.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import dev.adi.testapp.adapter.TodoListAdapter;
import dev.adi.testapp.database.TodoDataSource;
import dev.adi.testapp.dialog.TodoItemDialog;
import dev.adi.testapp.model.TodoItem;

public class TodoActivity extends AppCompatActivity implements TodoItemDialog.TodoItemDialogListener {

    private TodoDataSource todoData;
    private CoordinatorLayout container;
    private ListView lvItems;
    private List<TodoItem> todoItems;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            reloadData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        getSupportActionBar().setTitle("Todos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(TodoDataSource.TODO_SERVICE_MESSAGE));

        todoData = new TodoDataSource(this);
        todoData.open();

        Toast.makeText(this, String.valueOf(todoData.getTodosCount()), Toast.LENGTH_SHORT).show();

        container = (CoordinatorLayout) findViewById(R.id.activity_todo);
        lvItems = (ListView) findViewById(R.id.lv_todo);

        reloadData();
    }

    public void showTodoDialog(View view) {
        new TodoItemDialog().show(getSupportFragmentManager(), "TodoItemDialog");
    }

    @Override
    public void onDataReturn(String todoName, String todoGroup) {
        TodoItem item = new TodoItem(UUID.randomUUID().toString(), todoName, todoGroup, 1, 0, 0);

        try {
            todoData.createTodo(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Snackbar.make(container, "Created new Todo", Snackbar.LENGTH_SHORT).show();
    }

    public void reloadData() {
        todoItems = todoData.getAllTodos();
        lvItems.setAdapter(new TodoListAdapter(this, todoItems, todoData));
    }

    @Override
    protected void onPause() {
        super.onPause();
        todoData.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoData.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
