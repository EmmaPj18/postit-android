package com.emmapj18.postit.Helpers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.emmapj18.postit.Listeners.CameraListener;
import com.emmapj18.postit.Models.Feed;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CameraHelper {
    private static final int REQUEST_PICTURE_FROM_GALLERY = 23;
    private static final int REQUEST_PICTURE_FROM_CAMERA = 24;
    private static final int REQUEST_CROP_PICTURE = 25;
    private static final String TAG = "ImageInputHelper";
    private static final String PATTERN_DATE = "dd_MM_yyyy_hh_mm_ss";

    private File tempFileFromSource = null;
    private Uri tempUriFromSource = null;

    private File tempFileFromCrop = null;
    private Uri tempUriFromCrop = null;

    private Activity activity;
    private Fragment fragment;

    private CameraListener listener;

    /*public CameraHelper(Activity activity) {
        this.activity = activity;
    }*/

    public CameraHelper(Fragment fragment, Activity activity) {
        this.fragment = fragment;
        this.activity = activity;
    }

    public void setCameraListener(CameraListener listener) {
        this.listener = listener;
    }

    private void checkListener() {
        if (listener == null) {
            throw new RuntimeException("CameraListener must be set before calling openGalleryIntent(), openCameraIntent() or requestCropImage().");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_PICTURE_FROM_GALLERY) && (resultCode == Activity.RESULT_OK)) {
            Log.d(TAG, "Image selected from gallery");
            listener.onImageSelectedFromGallery(data.getData(), tempFileFromSource);
        } else if ((requestCode == REQUEST_PICTURE_FROM_CAMERA) && (resultCode == Activity.RESULT_OK)) {
            Log.d(TAG, "Image selected from camera");
            listener.onImageTakenFromCamera(tempUriFromSource, tempFileFromSource);
        } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == Activity.RESULT_OK)) {
            Log.d(TAG, "Image returned from crop");
            listener.onImageCropped(tempUriFromCrop, tempFileFromCrop);
        }
    }

    public void selectImageFromGallery() {
        checkListener();

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose_" + new SimpleDateFormat(PATTERN_DATE, Locale.getDefault()).format(Calendar.getInstance().getTime()), ".png", activity.getExternalCacheDir());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);

        if (fragment == null)
            activity.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
        else
            fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
    }

    public void takePhotoWithCamera() {
        checkListener();

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("camera_" + new SimpleDateFormat(PATTERN_DATE, Locale.getDefault()).format(Calendar.getInstance().getTime()), ".png", activity.getExternalCacheDir());
                tempUriFromSource = Uri.fromFile(tempFileFromSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);
        if (fragment == null) {
            activity.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
        } else {
            fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
        }
    }

    public void requestCropImage(Uri uri, int outputX, int outputY, int aspectX, int aspectY) {
        checkListener();

        if (tempFileFromCrop == null) {
            try {
                tempFileFromCrop = File.createTempFile("crop_" + new SimpleDateFormat(PATTERN_DATE, Locale.getDefault()).format(Calendar.getInstance().getTime()), ".png", activity.getExternalCacheDir());
                tempUriFromCrop = Uri.fromFile(tempFileFromCrop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", tempUriFromCrop);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);

        if (fragment == null) {
            activity.startActivityForResult(intent, REQUEST_CROP_PICTURE);
        } else {
            fragment.startActivityForResult(intent, REQUEST_CROP_PICTURE);
        }
    }


}
