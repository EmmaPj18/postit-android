package com.emmapj18.postit.Listeners;

import android.net.Uri;

import java.io.File;

public interface CameraListener {
    void onImageSelectedFromGallery(Uri uri, File imageFile);
    void onImageTakenFromCamera(Uri uri, File imageFile);
    void onImageCropped(Uri uri, File imageFile);
}