package com.emmapj18.postit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    public String description;
    public String imageUrl;

    Feed() {}
    Feed(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static List<Feed> getList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feeds");
        final List<Feed> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    list.add(data.getValue(Feed.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE_ERROR", databaseError.getMessage(), databaseError.toException());
            }
        });

        return list;
    }

    public static void createNewFeed(String description, String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feeds");

        String feedId = databaseReference.push().getKey();

        if (feedId != null) {
            Feed feed = new Feed(description, imageUrl);
            databaseReference.child(feedId).setValue(feed);
        }


    }
}
