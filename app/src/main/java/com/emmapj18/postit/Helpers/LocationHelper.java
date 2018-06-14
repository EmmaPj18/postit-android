package com.emmapj18.postit.Helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.emmapj18.postit.Listeners.MyLocationListener;

import java.util.List;

public class LocationHelper implements LocationListener {
    public static final int MESSAGE_PROVIDER_DISABLED = 0;
    public static final int MESSAGE_TIMEOUT = 1;
    public static final int MESSAGE_FORCED_CANCEL = 2;
    public static final int TIMEOUT_TIME = 0;

    private final Context context;
    private final LocationManager locationManager;
    private MyLocationListener callBack = null;
    private Accuracy accuracy = Accuracy.FINE;
    private final Handler timeOutHandler = new Handler();
    private float accuracyFloat = 30.0f;
    private boolean hasAcquired = false;

    private final Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            timeOutHandler.removeCallbacks(timeOutRunnable);
            locationManager.removeUpdates(LocationHelper.this);

            if (callBack != null) {
                callBack.onLocationFailed("Timeout", MESSAGE_TIMEOUT);
                callBack.onTimeout();
            }
        }
    };

    public LocationHelper(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void cancelRequest() {
        timeOutHandler.removeCallbacks(timeOutRunnable);
        locationManager.removeUpdates(this);

        if (callBack != null) {
            callBack.onLocationFailed("Cancel", MESSAGE_FORCED_CANCEL);
        }
    }

    public void setAccuracy(float accuracy) {
        accuracyFloat = accuracy;
    }

    public float getAccuracy() {
        return accuracyFloat;
    }

    public Location getCachedLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location location = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
            try {
                Location getLocation = locationManager.getLastKnownLocation(providers.get(i));

                if (location == null || (getLocation != null && getLocation.getAccuracy() < location.getAccuracy())) {
                    location = getLocation;
                }
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
        }
        return location;
    }

    public void stopFetch() {
        timeOutHandler.removeCallbacks(timeOutRunnable);
        locationManager.removeUpdates(this);
    }

    public void fetchLocation(long timeOut, Accuracy accuracy, MyLocationListener callBack) {
        this.callBack = callBack;
        callBack.onRequest();
        this.accuracy = accuracy;
        Location userLocation;
        userLocation = getCachedLocation();
        if (userLocation == null) {

            if (timeOut > 0) {
                timeOutHandler.postDelayed(timeOutRunnable, timeOut);
            }

            try {
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            try {
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        List<String> providers = locationManager.getProviders(true);
        boolean allOn = false;

        for (int i = providers.size() - 1; i >= 0; i--) {
            allOn |= Settings.Secure.isLocationProviderEnabled(context.getContentResolver(), providers.get(i));
        }

        if (!allOn) {
            timeOutHandler.removeCallbacks(timeOutRunnable);
            locationManager.removeUpdates(this);

            if (callBack != null) {
                callBack.onLocationFailed("All providers disabled", MESSAGE_PROVIDER_DISABLED);
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (callBack != null) {
                callBack.onLocationChanged(location);

                if (accuracy == Accuracy.FINE && (!location.hasAccuracy() || location.getAccuracy() > accuracyFloat))
                    return;
                if (!hasAcquired) {
                    timeOutHandler.removeCallbacks(timeOutRunnable);
                    locationManager.removeUpdates(this);
                    callBack.onLocationAcquired(location);
                    hasAcquired = true;
                }
            }
        }
    }

    public enum Accuracy {
        FINE,
        COARSE
    }
}
