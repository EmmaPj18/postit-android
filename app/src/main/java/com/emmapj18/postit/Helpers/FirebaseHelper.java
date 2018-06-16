package com.emmapj18.postit.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FirebaseHelper {
    private static final String FEEDS_NODE = "feeds";
    private static final String IMAGE_URL = "imagenes/";

    private static Uri shareUri;
    private static Bitmap bitmap;

    public static void getFeeds(final FeedListener listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FEEDS_NODE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Feed> feeds = new ArrayList<>();

                for (DataSnapshot nodeDS : dataSnapshot.getChildren()) {
                    Feed feed = nodeDS.getValue(Feed.class);
                    feeds.add(feed);
                }

                listener.onRetrieveFeeds(feeds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    public static void saveFeed(Feed feed) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FEEDS_NODE);

        String id = reference.push().getKey();
        if (id != null) {
            reference.child(id).child("dateAdded").setValue(feed.dateAdded);
            reference.child(id).child("description").setValue(feed.description);
            reference.child(id).child("imageUrl").setValue(feed.imageUrl);
            reference.child(id).child("location").setValue(feed.location);
            reference.child(id).child("user").setValue(feed.user);
        }
        else Log.e("NULLEXCEPTION", "CANNOT CREATE A NEW ID BECAUSE IS NULL");
    }

    public static void setImage(String imageUrl, final Context context, final ImageView imageView) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageReference = storage.getReference().child(imageUrl);

        imageReference.getDownloadUrl()
                .addOnSuccessListener((Uri uri) ->
                    Glide.with(context).load(uri).into(imageView)
                ).addOnFailureListener((Exception e) -> e.printStackTrace());
    }

    public static Bitmap downloadImage(String imageUrl) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageReference = storage.getReference().child(imageUrl);

        imageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener((byte[] bytes) ->
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length))
                .addOnFailureListener((@NonNull Exception e) -> e.printStackTrace());

        return bitmap;

    }

    public static String uploadImage(Bitmap bitmap) {
        String imageUrl = IMAGE_URL + "PostIt_" + CommonHelper.convertDateToString(Calendar.getInstance().getTime()) + ".png";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageReference = storage.getReference()
            .child(imageUrl);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask task = imageReference.putBytes(data);
        task.addOnFailureListener((@NonNull Exception e) ->
                e.printStackTrace()
        ).addOnSuccessListener((UploadTask.TaskSnapshot taskSnapshot) -> {
                Log.d("TAG", taskSnapshot.toString());
        });

        return imageUrl;
    }
}
