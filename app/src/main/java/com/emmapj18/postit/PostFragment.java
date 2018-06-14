package com.emmapj18.postit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.emmapj18.postit.Helpers.CameraHelper;
import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.Helpers.LocationHelper;
import com.emmapj18.postit.Listeners.CameraListener;
import com.emmapj18.postit.Listeners.MyLocationListener;
import com.emmapj18.postit.Models.Feed;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostFragment extends Fragment implements CameraListener, MyLocationListener, View.OnClickListener {

    private ImageView mImageView;
    private EditText mEditText;
    private CameraHelper cameraHelper;
    private LocationHelper locationHelper;
    private Context context;

    private String imageUrl;
    private String location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = view.findViewById(R.id.imageViewImagePost);
        view.findViewById(R.id.buttonSubmit).setOnClickListener(this);
        view.findViewById(R.id.buttonCancel).setOnClickListener(this);
        mEditText = view.findViewById(R.id.editTextDescriptionPost);

        mImageView.setOnClickListener(this);
        cameraHelper = new CameraHelper(this, this.getActivity());
        locationHelper = new LocationHelper(view.getContext());
        context = view.getContext();

        locationHelper.fetchLocation(LocationHelper.TIMEOUT_TIME, LocationHelper.Accuracy.FINE, this);
        cameraHelper.setCameraListener(this);
        Location location = locationHelper.getCachedLocation();
        location.getLatitude();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewImagePost:
                new AlertDialog.Builder(this.getContext())
                        .setIcon(R.drawable.ic_menu_camera)
                        .setTitle("Camera or Gallery")
                        .setMessage("Select photo from camera or gallery?")
                        .setPositiveButton("Yes", (DialogInterface dialog, int which) -> cameraHelper.takePhotoWithCamera())
                        .setNegativeButton("No", (DialogInterface dialog, int which) -> cameraHelper.selectImageFromGallery())
                        .show();
                break;
            case R.id.buttonSubmit:
                    uploadData();
                break;
            case R.id.buttonCancel:
                    finish();
                break;
        }
    }

    public void finish() {
        locationHelper.stopFetch();
        MainActivity.setFragment(MainActivity.mManager, R.id.frameLayoutBody, new FeedFragment());
    }

    public void uploadData(){
        Feed feed = new Feed();

        feed.dateAdded = new SimpleDateFormat(Feed.DATE_PATTERN, Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        feed.imageUrl = imageUrl;
        feed.description = mEditText.getText().toString();
        feed.location = location;

        FirebaseHelper.saveFeed(feed);
        finish();
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        cameraHelper.requestCropImage(uri, 800, 450, 16, 9);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        cameraHelper.requestCropImage(uri, 800, 450, 16, 9);
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            mImageView.setImageBitmap(bitmap);
            imageUrl = FirebaseHelper.uploadImage(uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationFailed(String message, int messageId) {
        locationHelper.stopFetch();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location.getLongitude() + "," + location.getLatitude();
    }

    @Override
    public void onLocationAcquired(Location location) {
        this.location = location.getLongitude() + "," + location.getLatitude();
    }

    @Override
    public void onRequest() {

    }

    @Override
    public void onTimeout() {

    }
}
