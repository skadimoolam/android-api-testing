package dev.adi.testapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class GpsActivity2 extends AppCompatActivity implements LocationListener {

    private TextView tvGpsLat, tvGpsLong;
    private LocationManager locationManager;
    private Location previousLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        tvGpsLat = (TextView) findViewById(R.id.tv_lat);
        tvGpsLong = (TextView) findViewById(R.id.tv_long);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

//        Toast.makeText(this, String.valueOf(lastKnownLocation.getSpeed()), Toast.LENGTH_SHORT).show();

        tvGpsLat.setText("Lat:");
        tvGpsLong.setText("Long:");
        getSupportActionBar().setTitle("GPS example");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Went through this", Toast.LENGTH_SHORT).show();
//            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Lat: " + location.getLatitude() + "Lng: " + location.getLongitude();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        Toast.makeText(this, "speed: " + location.getSpeed(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "speed: " + location.getTime(), Toast.LENGTH_LONG).show();


        tvGpsLat.setText(String.valueOf(location.getLatitude()));
        tvGpsLong.setText(String.valueOf(location.getLongitude()));

        if (previousLocation == null) {
            previousLocation = location;
//            location.
        } else {
//            String speedMsg = "speed:" + calcSpeed(previousLocation, location);
//            Toast.makeText(this, speedMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Intent settings_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settings_intent);
        Toast.makeText(this, "Gps is off", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Gps is on", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public double calcSpeed(Location prev, Location now) {
        double lat1 = toRad(prev.getLatitude());
        double long1 = toRad(prev.getLongitude());
        double lat2 = toRad(now.getLatitude());
        double long2 = toRad(now.getLongitude());
        double radius_of_earth = 6378100;

        double rho1 = radius_of_earth * Math.cos(lat1);
        double z1 = radius_of_earth * Math.sin(lat1);
        double x1 = rho1 * Math.cos(long1);
        double y1 = rho1 * Math.sin(long1);

        double rho2 = radius_of_earth * Math.cos(lat2);
        double z2 = radius_of_earth * Math.sin(lat2);
        double x2 = rho2 * Math.cos(long2);
        double y2 = rho2 * Math.sin(long2);

        double dot_product = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot_product / (radius_of_earth * radius_of_earth);
        double theta = Math.acos(cos_theta);

        return radius_of_earth * theta;
    }

    public double toRad(double digit) {
        return digit * Math.PI / 180;
    }
}
