package dev.adi.testapp.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import dev.adi.testapp.R;
import dev.adi.testapp.fragment.WeatherFragment;

public class WeatherReportActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private ViewPager weatherSlider;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        weatherSlider = (ViewPager) findViewById(R.id.vp_weather_slider);

        fragmentList = new ArrayList<>();
        fragmentList.add(WeatherFragment.newInstance("Gobi-Home"));
        fragmentList.add(WeatherFragment.newInstance("Gobi-Farm"));
        fragmentList.add(WeatherFragment.newInstance("CBE-Iob"));
        fragmentList.add(WeatherFragment.newInstance("CBE-Sitra"));
        fragmentList.add(WeatherFragment.newInstance("CBE-Hopes"));

        weatherSlider.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragmentList));
        weatherSlider.setOffscreenPageLimit(fragmentList.size());
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> pageFragments;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> pageList) {
            super(fm);
            pageFragments = pageList;
        }

        @Override
        public Fragment getItem(int i) {
            return pageFragments.get(i);
        }

        @Override
        public int getCount() {
            return pageFragments.size();
        }
    }
}
