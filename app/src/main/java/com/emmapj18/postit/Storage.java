package com.emmapj18.postit;

import android.support.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Storage {
    FirebaseStorage mStore;
    StorageReference mRef;


    public Storage(@Nullable String reference) {
        mStore = FirebaseStorage.getInstance();

        if (reference == null) mRef = mStore.getReference();
        else mRef = mStore.getReference(reference);
    }

    public void uploadFile() {

    }
}
