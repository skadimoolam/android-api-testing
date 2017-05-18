package dev.adi.testapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SensorCapabilityActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    int sensorType;
    Sensor sensor;

    TextView tvSensorDetail, tvSensorValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_capability);

        sensorType = getIntent().getIntExtra("sensor-type", 0);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);

        tvSensorDetail = (TextView) findViewById(R.id.tv_sensor_detail);
        tvSensorDetail = (TextView) findViewById(R.id.tv_sensor_values);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sensor.getName() + "\n");
        stringBuilder.append(sensor.getMaximumRange() + "\n");
        stringBuilder.append(sensor.getPower() + "\n");
        stringBuilder.append(sensor.getResolution() + "\n");

        tvSensorDetail.setText(stringBuilder.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        StringBuilder stringBldr = new StringBuilder();
        stringBldr.append(sensorEvent.accuracy + "\n");
        stringBldr.append(sensorEvent.timestamp + "\n");
        stringBldr.append(sensorEvent.values.toString() + "\n");

//        tvSensorValues.setText(Arrays.toString(sensorEvent.values));

//        try {
//            stringBuilder.append(sensorEvent.values[1] + "\n");
//            stringBuilder.append(sensorEvent.values[2] + "\n");
//            stringBuilder.append(sensorEvent.values[3] + "\n");
//            stringBuilder.append(sensorEvent.values[4] + "\n");
//            stringBuilder.append(sensorEvent.values[5] + "\n");
//            stringBuilder.append(sensorEvent.values[6] + "\n");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        tvSensorValues.setText(stringBldr.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
