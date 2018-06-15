package com.emmapj18.postit.Helpers;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class LocationHelper {

    private static final long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private static final long FASTEST_INTERVAL = 2000; /* 2 sec */


    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationListener listener;

    public LocationHelper(Context context, LocationListener listener) {
        this.context = context;
        this.listener = listener;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        startLocationUpdates();

    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);



        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            listener.onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    public void getLastLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener((Location location) -> {
                        if (location != null) {
                            listener.onLocationChanged(location);
                        }
                    })
                    .addOnFailureListener((@NonNull Exception e) -> {
                        Log.d("LOCATION", "Error trying to get last GPS location", e);
                        e.printStackTrace();
                    });
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }
}
