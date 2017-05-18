package dev.adi.testapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dev.adi.testapp.R;
import dev.adi.testapp.database.TodoDataSource;
import dev.adi.testapp.model.TodoItem;

public class TodoListAdapter extends ArrayAdapter<TodoItem> {

    private Context context;
    private List<TodoItem> todoItems;
    private LayoutInflater inflater;
    private TodoDataSource todoData;

    public TodoListAdapter(Context context, List<TodoItem> todoItems, TodoDataSource todoData) {
        super(context, R.layout.item_todo, todoItems);
        this.todoItems = todoItems;
        this.todoData = todoData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
        }

        final TextView tvName = (TextView) convertView.findViewById(R.id.tv_todo_name);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_todo_group);
        final CheckBox cbCompleted = (CheckBox) convertView.findViewById(R.id.cb_todo_complete);
        ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.btn_todo_delete);

        final TodoItem item = todoItems.get(position);
        tvName.setText(item.getTodoName());
        tvGroup.setText(item.getTodoGroup());

//        Log.i("TodoListAdapter", item.toString());

        if (item.getTodoCompleted() == 1) {
            cbCompleted.setChecked(true);
            tvName.setPaintFlags(tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvGroup.setVisibility(View.GONE);
        } else {
            cbCompleted.setChecked(false);
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View editView = inflater.inflate(R.layout.dialog_input_todo, null);
                final EditText etTodoName = (EditText) editView.findViewById(R.id.et_todo_name);
                final EditText etTodoGroup = (EditText) editView.findViewById(R.id.et_todo_group);
                etTodoName.setText(item.getTodoName());
                etTodoGroup.setText(item.getTodoGroup());

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Todo!");
                builder.setView(editView);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.setTodoName(etTodoName.getText().toString());
                        item.setTodoGroup(etTodoGroup.getText().toString());
                        todoData.updateTodo(item);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.setCancelable(true);
                builder.create().show();

                return true;
            }
        });

//        cbCompleted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (cbCompleted.isChecked()) {
//                    item.setTodoCompleted(1);
//                    todoData.updateTodo(item);
//                } else {
//                    item.setTodoCompleted(0);
//                    todoData.updateTodo(item);
//                }
//            }
//        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Todo!");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), tvName.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                        todoData.deleteTodo(item.getTodoId());
                    }
                });
                builder.setNegativeButton("No", null);
                builder.setCancelable(true);
                builder.create().show();
            }
        });

        return convertView;
    }
}
