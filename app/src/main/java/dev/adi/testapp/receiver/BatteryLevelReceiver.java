package dev.adi.testapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import dev.adi.testapp.AsonActivity;
import dev.adi.testapp.BatteryBluetoothActivity;

public class BatteryLevelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver Online", Toast.LENGTH_SHORT).show();

        Intent nextIntent = new Intent(context.getApplicationContext(), BatteryBluetoothActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(nextIntent);
    }
}
