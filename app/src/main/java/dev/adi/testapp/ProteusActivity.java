package dev.adi.testapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.flipkart.android.proteus.builder.DataAndViewParsingLayoutBuilder;
import com.flipkart.android.proteus.builder.LayoutBuilderFactory;
import com.flipkart.android.proteus.toolbox.Styles;
import com.flipkart.android.proteus.view.ProteusView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ProteusActivity extends AppCompatActivity {

    private ViewGroup container;
    private JsonObject layoutMain;
    private JsonObject dataMain;
    private DataAndViewParsingLayoutBuilder layoutBuilder;
    private Styles styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proteus);

        container = (ViewGroup) findViewById(R.id.content_proteus);
        styles = new Styles();
        dataMain = new JsonObject();

        reload();
    }

    public static String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public String readFile() {
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(openFileInput(EditFileActivity.FILE_NAME)));
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
            fos.write(AssetJSONFile("layout_main.json", this).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reloadLayout(View view) {
        reload();
    }

    public void reload() {
        container.removeAllViews();
        Map<String, JsonObject> layouts = new HashMap<>();
        layouts.put("layout_main.json", layoutMain);
        layoutBuilder = new LayoutBuilderFactory().getDataAndViewParsingLayoutBuilder(layouts);

        if (readFile().length() > 0) {
            layoutMain = (JsonObject) new JsonParser().parse(readFile());
        } else {
            writeFile();
        }

        ProteusView view = layoutBuilder.build(container, layoutMain, dataMain, 0, styles);
        container.addView((View) view);

    }

    public void editLayout(View view) {
        startActivity(new Intent(this, EditFileActivity.class));
    }
}
