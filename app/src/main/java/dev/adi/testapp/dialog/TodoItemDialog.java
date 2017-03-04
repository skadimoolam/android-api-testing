package dev.adi.testapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import dev.adi.testapp.R;

public class TodoItemDialog extends DialogFragment {

    private TodoItemDialogListener host;

    public interface TodoItemDialogListener {
        void onDataReturn(String todoName, String todoGroup);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_input_todo, null);
        final EditText etTodoName = (EditText) v.findViewById(R.id.et_todo_name);
        final EditText etTodoGroup = (EditText) v.findViewById(R.id.et_todo_group);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Todo");
        builder.setView(v);
        builder.setCancelable(true);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (etTodoName.getText().length() == 0) {
                    etTodoName.setError("Don't leave this empty!");
                } else if (etTodoGroup.getText().length() == 0) {
                    etTodoGroup.setError("Don't leave this empty!");
                } else {
                    host.onDataReturn(etTodoName.getText().toString(), etTodoGroup.getText().toString());
                }
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        host = (TodoItemDialogListener) context;
    }
}
