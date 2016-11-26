package com.weather.bhanvisangar.weather;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.weather.Util.CurrentLocationObservable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.location_textview)
    TextView locationTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        CurrentLocationObservable.create(this).subscribe(new Subscriber<Location>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                locationTextview.setText("Location not available");

            }

            @Override
            public void onNext(Location location) {
                if(location!= null) {
                    locationTextview.setText("Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude());
                }
                else {
                    locationTextview.setText("Location is null");
                }

            }
        });

    }

}
