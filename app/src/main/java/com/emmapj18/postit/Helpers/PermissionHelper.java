package com.emmapj18.postit.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.util.ArrayList;

public class PermissionHelper {
    private static final int ALL_PERMISSIONS_RESULT = 101;
    private static final String EMAIL_PERMISSION = "email";
    private static final String PUBLISH_ACTIONS_PERMISION = "publish_actions";


    private Context context;
    private Activity activity;

    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest = new ArrayList<>();

    public PermissionHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        addPermissions();
        findUnAskedPermissions(permissions);
    }

    private void addPermissions() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissions.add(Manifest.permission.CAMERA);
    }

    public void requestPermissions() {
        if (permissionsToRequest.size() > 0)
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
    }

    public void onPermissionResult(int requestCode, String[] permissions, int[] grantResult) {
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            if (grantResult.length > 0) {
                int i = 0;
                for (int grant: grantResult) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Log.d("PERMISSIONS", permissions[i] + " is Denied");
                    }
                    i++;
                }
            }
        }
    }

    private void findUnAskedPermissions(ArrayList<String> wanted) {
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                permissionsToRequest.add(perm);
            }
        }
    }

    private boolean hasPermission(String permission) {
        return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }
}
