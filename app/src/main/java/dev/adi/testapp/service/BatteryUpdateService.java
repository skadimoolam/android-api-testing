package dev.adi.testapp.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class BatteryUpdateService extends Service {

    private static final int CHECK_BATTERY_INTERVAL = 5000;
    private Handler handler;

    private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent batteryIntent) {
            int batteryState = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

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

    @Override
    public void onCreate() {
        registerReceiver(batInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(batInfoReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
