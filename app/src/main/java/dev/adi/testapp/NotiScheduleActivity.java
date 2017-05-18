package dev.adi.testapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import dev.adi.testapp.receiver.NotificationPublisher;
import dev.adi.testapp.utils.NotificationID;

public class NotiScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_schedule);

        findViewById(R.id.btn_5sec).setOnClickListener(this);
        findViewById(R.id.btn_10sec).setOnClickListener(this);
        findViewById(R.id.btn_15sec).setOnClickListener(this);
        findViewById(R.id.btn_20sec).setOnClickListener(this);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    private void scheduleNotification(Notification notification, int delay) {
        int id = NotificationID.getId();
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_5sec:
                scheduleNotification(getNotification("5 second delay"), 5000);
                break;

            case R.id.btn_10sec:
                scheduleNotification(getNotification("10 second delay"), 10000);
                break;

            case R.id.btn_15sec:
                scheduleNotification(getNotification("15 second delay"), 15000);
                break;

            case R.id.btn_20sec:
                scheduleNotification(getNotification("20 second delay"), 20000);
                break;

            default:
                Toast.makeText(this, "Dont know what happened", Toast.LENGTH_SHORT).show();
        }
    }
}
