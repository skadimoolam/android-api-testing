package dev.adi.testapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SensorListActivity extends AppCompatActivity {

    SensorManager sensorManager;
    ListView lvSensorList;
    ArrayAdapter laSensors;
    List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        lvSensorList = (ListView) findViewById(R.id.lv_sensor_list);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        final List<String> sensorsNameList = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            sensorsNameList.add(sensor.getName());
        }

        laSensors = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensorsNameList);
        lvSensorList.setAdapter(laSensors);

        lvSensorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(SensorListActivity.this, SensorCapabilityActivity.class);
                i.putExtra("sensor-type", sensorList.get(pos).getType());
                startActivity(i);
            }
        });
    }
}
