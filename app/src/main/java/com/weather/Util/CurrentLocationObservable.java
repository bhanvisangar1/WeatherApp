package com.weather.Util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
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


}