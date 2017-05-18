package dev.adi.testapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CaldroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caldroid);

        CaldroidFragment calender = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

        calender.setArguments(args);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.caldroid_holder, calender)
            .commit();


        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        CaldroidListener calListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), format.format(date), Toast.LENGTH_SHORT).show();
            }
        };

        calender.setCaldroidListener(calListener);

        setCustomResourceForDates(calender);
    }

    private void setCustomResourceForDates(CaldroidFragment caldroidFragment) {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        cal.set(Calendar.YEAR, Calendar.MONTH + 1, 20);
        Date customData = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(Color.BLUE);
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setBackgroundDrawableForDate(blue, customData);
            caldroidFragment.setTextColorForDate(android.R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(android.R.color.white, greenDate);
            caldroidFragment.setTextColorForDate(android.R.color.white, customData);
        }
    }
}
