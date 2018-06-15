package com.emmapj18.postit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.emmapj18.postit.Helpers.CommonHelper;
import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.Helpers.ImageHelper;
import com.emmapj18.postit.Helpers.LocationHelper;
import com.emmapj18.postit.Models.Feed;
import com.google.android.gms.location.LocationListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

public class PostFragment extends Fragment implements View.OnClickListener, LocationListener {

    private ImageView mImageView;
    private EditText mEditText;
    private ImageHelper imageHelper;

    private String imageUrl;
    private String location;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageView = view.findViewById(R.id.imageViewImagePost);
        mImageView.setOnClickListener(this);
        view.findViewById(R.id.buttonSubmit).setOnClickListener(this);
        view.findViewById(R.id.buttonCancel).setOnClickListener(this);
        mEditText = view.findViewById(R.id.editTextDescriptionPost);
        imageHelper = new ImageHelper(this.getActivity(), this);
        LocationHelper locationHelper = new LocationHelper(this.getContext(), this);
        locationHelper.getLastLocation();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageHelper.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extra = data.getExtras();

            if (extra != null) {
                Bitmap bitmap = (Bitmap)extra.get("data");

                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                    imageUrl = FirebaseHelper.uploadImage(bitmap);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewImagePost:
                imageHelper.startCamera();
                break;
            case R.id.buttonSubmit:
                    uploadData();
                break;
            case R.id.buttonCancel:
                    finish();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.location = location.getLatitude() + "," + location.getLongitude();
        }
    }

    public void finish() {
        MainActivity.setFragment(MainActivity.mManager, R.id.frameLayoutBody, new FeedFragment());
    }

    public void uploadData(){
        Feed feed = new Feed();
        FirebaseUser user = mAuth.getCurrentUser();

        try {
            feed.dateAdded = CommonHelper.convertDateToString(new Date(Calendar.getInstance().getTime().getTime()));
            feed.imageUrl = imageUrl;
            feed.description = mEditText.getText().toString();
            feed.location = location;
            feed.user = user.getEmail();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        FirebaseHelper.saveFeed(feed);
        finish();
    }
}
