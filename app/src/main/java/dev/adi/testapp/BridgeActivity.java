package dev.adi.testapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Response;
import com.afollestad.bridge.ResponseConvertCallback;
import com.afollestad.json.Ason;

public class BridgeActivity extends AppCompatActivity {

    private LinearLayout parentLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ason);
        getSupportActionBar().setTitle("Bridge HTTP Test");
        getSupportActionBar().setHomeButtonEnabled(true);

        parentLayout = (LinearLayout) findViewById(R.id.parent_layout);
        progressDialog = ProgressDialog.show(this, "Loading...", "Please Wait!", false);

        Bridge.get("https://jsonip.com/")
            .asString(new ResponseConvertCallback<String>() {
                @Override
                public void onResponse(@Nullable Response response, @Nullable String object, @Nullable BridgeException e) {
                    Ason ason = new Ason(object);

                    addTextView("MyIP", true);
                    addTextView(ason.get("ip").toString(), false);
                    addTextView(" ", true);
                    addTextView("Comment", true);
                    addTextView(ason.get("reject-fascism").toString(), false);
                    progressDialog.dismiss();
                }
            });
    }

    public void addTextView(String text, boolean isHeading) {
        TextView textView = new TextView(this);
        textView.setText(text);
        if (isHeading) {
            textView.setTextAppearance(this, android.R.style.TextAppearance_Large);
        }

        parentLayout.addView(textView);
    }
}
