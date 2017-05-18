package dev.adi.testapp.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Callback;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.afollestad.json.Ason;

import java.util.Date;

import dev.adi.testapp.R;
import dev.adi.testapp.utils.Util;

public class WeatherFragment extends Fragment {

    private String spTag = "WeatherReports.perf";
    private String prefKey, perfLocation;
    private String TAG = this.getClass().getSimpleName();
    private TextView tvTempMain, tvTempApparent, tvSummery, tvLocation;
    private ImageView ivImgMain;
    private RelativeLayout rlActivityRoot;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_report, container, false);

        rlActivityRoot = (RelativeLayout) view.findViewById(R.id.rl_activity_root);
        tvTempMain = (TextView) view.findViewById(R.id.tv_temp_main);
        tvTempApparent = (TextView) view.findViewById(R.id.tv_temp_apparent);
        tvSummery = (TextView) view.findViewById(R.id.tv_summary);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        ivImgMain = (ImageView) view.findViewById(R.id.iv_img_main);
        ImageView ivRefresh = (ImageView) view.findViewById(R.id.iv_refresh);

        perfLocation = getArguments().getString("location", "Gobi-Home");
        prefKey = "weather-report-" + perfLocation;
        sharedPreferences = getActivity().getSharedPreferences(spTag, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        checkOldData();
        return view;
    }

    private void checkOldData() {
        if (sharedPreferences.contains(prefKey)) {
            Ason ason = new Ason(sharedPreferences.getString(prefKey, ""));
            long timeDiff = (new Date().getTime() / 1000) - ason.getInt("currently.time");

            setData(ason);
            if (timeDiff > 3600) {
                loadData();
            }
        } else {
            loadData();
        }
    }

    private void loadData() {
        if (Util.hasInternet(getActivity())) {
            String latLong;

            switch (perfLocation) {
                case "Gobi-Home":
                    latLong = "11.450061,77.441718"; break;
                case "Gobi-Farm":
                    latLong = "11.427646,77.461279"; break;
                case "CBE-Iob":
                    latLong = "11.038128,76.867349"; break;
                case "CBE-Sitra":
                    latLong = "11.049419,77.055014"; break;
                case "CBE-Hopes":
                    latLong = "11.027214,77.024342"; break;
                default:
                    latLong = "11.450061,77.441718"; break;
            }

            Bridge
                .get("https://api.darksky.net/forecast/11662b1452a254a7b4538392e01aed65/"+ latLong +"?units=si&exclude=flags,hourly,daily")
                .throwIfNotSuccess()
                .request(new Callback() {
                    @Override
                    public void response(@NonNull Request request, @Nullable Response response, @Nullable BridgeException e) {
                        if (response != null) {
                            Ason ason = new Ason(response.asString());
                            setData(ason);

                            sharedPreferencesEditor.putString(prefKey, ason.toString());
                            sharedPreferencesEditor.commit();
                            sharedPreferencesEditor.apply();
                        } else {
                            Log.i(TAG, response.toString());
                        }
                    }
                });
        } else {
            Util.toast(getActivity(), "Please check your Internet");
        }
    }

    private void setData(Ason ason) {
        tvTempMain.setText(String.valueOf(ason.get("currently.temperature")));
        tvTempApparent.setText(String.valueOf(ason.get("currently.apparentTemperature")));
        tvSummery.setText(ason.getString("currently.summary"));
        tvLocation.setText(perfLocation);

        int imageResId;
        String colorHex;
        switch (ason.getString("currently.icon")) {
            case "clear-day":
                imageResId = R.drawable.ic_wea_sun; colorHex = "#e5b622"; break;
            case "clear-night":
                imageResId = R.drawable.ic_wea_moon; colorHex = "#27a37a"; break;
            case "rain":
                imageResId = R.drawable.ic_wea_rain; colorHex = "#649168"; break;
            case "snow":
                imageResId = R.drawable.ic_wea_snow; colorHex = "#e5e2e2"; break;
            case "sleet":
                imageResId = R.drawable.ic_wea_sleet; colorHex = "#28b4a2"; break;
            case "wind":
                imageResId = R.drawable.ic_wea_wind; colorHex = "#d3a8a8"; break;
            case "fog":
                imageResId = R.drawable.ic_wea_fog; colorHex = "#6789b0"; break;
            case "cloudy":
                imageResId = R.drawable.ic_wea_cloudy; colorHex = "#7a5e75"; break;
            case "partly-cloudy-night":
                imageResId = R.drawable.ic_wea_cloud_moon; colorHex = "#007e94"; break;
            case "partly-cloudy-sun":
                imageResId = R.drawable.ic_wea_cloud_sun; colorHex = "#aaf1d3"; break;
            default:
                imageResId = R.drawable.ic_wea_cloudy; colorHex = "#efc65d"; break;
        }

        ivImgMain.setImageResource(imageResId);
        rlActivityRoot.setBackgroundColor(Color.parseColor(colorHex));
//        animateIcon(imageResId);
    }

    private void animateIcon(final int imageResId) {
        ivImgMain
            .animate()
            .translationXBy(-200)
            .setDuration(500)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ivImgMain.setImageResource(imageResId);
                    ivImgMain
                        .animate()
                        .x(0)
                        .setDuration(500);
                }
            });
    }

    public static WeatherFragment newInstance(String location) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        fragment.setArguments(args);
        return fragment;
    }
}
