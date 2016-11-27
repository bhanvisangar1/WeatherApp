package com.weather.bhanvisangar.weather;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.weather.Util.CurrentLocationObservable;
import com.weather.Util.PermissionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class LaunchActivity extends AppCompatActivity {

    private final int LOCATION_REQUEST = 1;

    @BindView(R.id.location_textview)
    TextView locationTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        if (PermissionHelper.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getUserLocation();
        } else {
            PermissionHelper.requestPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION, LOCATION_REQUEST);
        }
    }

    private void getUserLocation() {
        CurrentLocationObservable.create(this).subscribe(locationSubscriber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            getUserLocation();
        }

    }

    Subscriber<Location> locationSubscriber = new Subscriber<Location>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            locationTextview.setText("Location not available");
        }

        @Override
        public void onNext(Location location) {
            locationTextview.setText("Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude());
        }
    };

}
