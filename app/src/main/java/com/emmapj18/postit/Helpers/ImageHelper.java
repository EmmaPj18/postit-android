package com.emmapj18.postit.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


public class ImageHelper {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Fragment fragment;
    private Activity activity;

    public ImageHelper(Activity activity,@Nullable Fragment fragment) {
        if (fragment != null)
            this.fragment = fragment;

        this.activity = activity;
    }

    public void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            if (fragment != null)
                fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            else
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
