package com.emmapj18.postit.Helpers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ShareHelper {


    public static void sharePhoto(Uri uri, Fragment fragment) {
        SharePhoto photo = new SharePhoto.Builder()
                .setImageUrl(uri)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareDialog.show(fragment, content);
    }
    public static void share(Bitmap bitmap, Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_STREAM, bitmap);
        intent.setType("image/*");
        fragment.startActivity(Intent.createChooser(intent, "Share Image:"));
    }
}
