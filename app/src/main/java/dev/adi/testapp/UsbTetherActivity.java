package dev.adi.testapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Method;

public class UsbTetherActivity extends AppCompatActivity {

    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout parentView = new LinearLayout(this);
        Button btnTetherOn = new Button(this);
        btnTetherOn.setText("Enable USB Tether");
        parentView.addView(btnTetherOn);

        setContentView(parentView);
        getSupportActionBar().setTitle("Usb Thether");
        getSupportActionBar().setHomeButtonEnabled(true);

        btnTetherOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object obj = getSystemService(Context.CONNECTIVITY_SERVICE);
                for (Method method : obj.getClass().getDeclaredMethods()) {
                    if (method.getName().equals("setUsbTethering")) {
                        try {
                            method.invoke(obj, Boolean.valueOf(true));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        Toast.makeText(this, networkInfo.toString(), Toast.LENGTH_SHORT).show();
    }
}
