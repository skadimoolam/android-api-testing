package dev.adi.testapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BatteryBluetoothActivity extends AppCompatActivity {

    static final String TAG = BatteryBluetoothActivity.class.getSimpleName();
    private static final String PREF_NAME = "dev.adi.testapp.prefs";
    private static final String SELECTED_DEVICE_ADDRESS = "selected_device_address";
    private static final String SELECTED_DEVICE_NAME = "selected_device_name";

    SharedPreferences.Editor prefEditor;
    SharedPreferences preferences;

    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> pairedDevicesList = new ArrayList<>();

    BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryState = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (batteryState) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    Toast.makeText(context, "Battery Full", Toast.LENGTH_SHORT).show();
                    break;

                case BatteryManager.BATTERY_STATUS_CHARGING:
                    Toast.makeText(context, "Battery Charging", Toast.LENGTH_SHORT).show();
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    Toast.makeText(context, "Battery Discharging", Toast.LENGTH_SHORT).show();
                    break;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    Toast.makeText(context, "Battery Not Charging", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(context, "Battery Default message", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    ListView lvPairedDevices;
    List<String> pairedDevicesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_bluetooth);
        registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        prefEditor = preferences.edit();
        lvPairedDevices = (ListView) findViewById(R.id.lv_paired_devices);

        setupBluetooth();
        setupList();
    }

    private void setupList() {
        if (pairedDevicesNames.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pairedDevicesNames);
            lvPairedDevices.setAdapter(adapter);
            lvPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    showToast(pairedDevicesList.get(position).getAddress());
                    connectAndSend(pairedDevicesList.get(position));

                    prefEditor.putString(SELECTED_DEVICE_ADDRESS, pairedDevicesList.get(position).getAddress());
                    prefEditor.putString(SELECTED_DEVICE_NAME, pairedDevicesList.get(position).getName());
                    prefEditor.apply();
                }
            });
        } else {
            lvPairedDevices.setVisibility(View.GONE);
        }
    }

    private void setupBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) {
                showToast("Bluetooth: Enabled");
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device: pairedDevices) {
                        pairedDevicesList.add(device);
                        pairedDevicesNames.add(device.getName() + " - " + device.getAddress());
                    }
                } else {
                    showToast("No paired devices");
                }
            } else {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
            }
        } else {
            showToast("Your phone does not support bluetooth");
        }
    }

    public void connectAndSend(BluetoothDevice device) {
        UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        BluetoothSocket socket = null;

        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
        } catch (Exception e) {
            showToast(e.toString());
            e.printStackTrace();
        }

        if (socket != null) {
            try {
                OutputStream outStream = socket.getOutputStream();
                outStream.write("123".getBytes());
                showToast("Wrote to output");
                socket.close();
            } catch (Exception e) {
                showToast(e.toString());
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryInfoReceiver);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
