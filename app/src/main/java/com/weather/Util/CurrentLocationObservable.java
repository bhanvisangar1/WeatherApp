package com.weather.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.weather.bhanvisangar.weather.Manifest;

import rx.Observable;
import rx.Subscriber;

public class CurrentLocationObservable implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        Observable.OnSubscribe<Location> {

    Context context;
    GoogleApiClient googleApiClient;
    Subscriber<? super Location> subscriber;


    public static Observable<Location> create(Context context) {
        return Observable.create(new CurrentLocationObservable(context));
    }

    private CurrentLocationObservable(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(CurrentLocationObservable.this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void call(Subscriber<? super Location> subscriber) {
        this.subscriber = subscriber;
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = getUserLocation();

        if (location != null) {
            subscriber.onNext(location);
            subscriber.onCompleted();
        } else {
            sendError();
        }
        googleApiClient.disconnect();
    }

    private void sendError() {
        subscriber.onError(new Exception("Location not found."));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        sendError();
        googleApiClient.disconnect();
    }

    private Location getUserLocation() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } else {
            return null;
        }
    }

}