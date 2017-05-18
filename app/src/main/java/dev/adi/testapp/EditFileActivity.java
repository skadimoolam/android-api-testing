package dev.adi.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditFileActivity extends AppCompatActivity {

    public static final String FILE_NAME = "layout_main.json";
    private EditText etFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        etFileContents = (EditText) findViewById(R.id.etFileContents);
        etFileContents.setText(readFile());
    }

    public String readFile() {
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));
            String line;
            StringBuffer text = new StringBuffer();
            while ((line = bReader.readLine()) != null) {
                text.append(line + "\n");
            }

            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void writeFile() {
        try {
            FileOutputStream fos = openFileOutput(EditFileActivity.FILE_NAME, Context.MODE_PRIVATE);
            fos.write(etFileContents.getText().toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        writeFile();
    }
}
