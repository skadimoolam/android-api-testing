package dev.adi.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import dev.adi.testapp.weather.WeatherReportActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llButtonHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llButtonHost = (LinearLayout) findViewById(R.id.button_host);
        addNewButton("Weather Report", WeatherReportActivity.class);
        addNewButton("Scheduled Notification", NotiScheduleActivity.class);
        addNewButton("Schedule in Calender", CalenderScheduleActivity.class);
        addNewButton("QR Scanner", QRActivity.class);
        addNewButton("CalDroid Activity", CaldroidActivity.class);
        addNewButton("Sensor List", SensorListActivity.class);
        addNewButton("Html TextView", HtmlTextViewActivity.class);
        addNewButton("Image Gallery", ImageGalleryActivity.class);
        addNewButton("Todo List", TodoActivity.class);
        addNewButton("Battery Bluetooth", BatteryBluetoothActivity.class);
        addNewButton("Usb Tether", UsbTetherActivity.class);
        addNewButton("Pref Layout", PrefsActivity.class);
        addNewButton("Contacts Lookup", ContactsActivity.class);
        addNewButton("Toobar Search", SearchBarActivity.class);
        addNewButton("Toobar Search 2", SearchBar2Activity.class);
        addNewButton("GPS Coords", GpsActivity.class);
        addNewButton("Data Binding Test", DataBindingActivity.class);
        addNewButton("Proteus Activity", ProteusActivity.class);
        addNewButton("Http Activity", HttpActivity.class);
        addNewButton("Ason Activity", AsonActivity.class);
        addNewButton("Bridge HTTP Helper", BridgeActivity.class);
        addNewButton("Chrome Custom Tabs", CCTabsActivity.class);
    }

    public void addNewButton(String buttonText, final Object resultActivity) {
        Button button = new Button(this);
        button.setText(buttonText);
        button.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, (Class<?>) resultActivity));
            }
        });

        llButtonHost.addView(button);
    }
}
