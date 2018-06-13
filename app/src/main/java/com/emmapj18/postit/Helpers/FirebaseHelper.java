package com.emmapj18.postit.Helpers;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.emmapj18.postit.Models.Feed;
import com.emmapj18.postit.Listeners.FeedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private static final String FEEDS_NODE = "feeds";

    public static void getFeeds(final FeedListener listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FEEDS_NODE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Feed> feeds = new ArrayList<>();

                for (DataSnapshot nodeDS: dataSnapshot.getChildren()){
                    Feed feed = nodeDS.getValue(Feed.class);
                    feeds.add(feed);
                }

                listener.onRetrieveFeeds(feeds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void saveFeed(Feed feed) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FEEDS_NODE);

        String id = reference.push().getKey();
        if (id != null) reference.child(id).setValue(feed);
        else Log.e("NULLEXCEPTION", "CANNOT CREATE A NEW ID BECAUSE IS NULL");
    }

    public static void setImage(String imageUrl, final Context context, final ImageView imageView) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageReference = storage.getReference().child(imageUrl);

        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
}
