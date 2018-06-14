package com.emmapj18.postit.Listeners;

import android.location.Location;

public interface MyLocationListener {
    void onRequest();

    void onLocationChanged(Location location);

    void onLocationAcquired(Location location);

    void onTimeout();

    void onLocationFailed(String message, int messageId);
}
