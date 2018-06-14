package com.emmapj18.postit;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.emmapj18.postit.Listeners.MenuListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuListener{

    public static int PERMISSIONS_CODE = 777;
    FirebaseAuth mAuth;
    public static FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mAuth = FirebaseAuth.getInstance();
        mManager = getSupportFragmentManager();

        setFragment(mManager, R.id.frameLayoutMenu, new MenuFragment());
        setFragment(mManager, R.id.frameLayoutBody, new FeedFragment());

        checkPermission();
    }

    public static void setFragment(FragmentManager manager, int frameLayoutId, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(frameLayoutId, fragment);
        transaction.commit();
    }

    @Override
    public void onHomeButtonPressed() {
        setFragment(mManager, R.id.frameLayoutBody, new FeedFragment());
    }

    @Override
    public void onPostButtonPressed() {
        setFragment(mManager, R.id.frameLayoutBody, new PostFragment());
    }

    @Override
    public void onProfileButtonPressed() {

    }

    public void checkPermission() {
        String[] permissions = readPermission();
        List<String> notGranted = new ArrayList<>();
        for (String permission: permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permission);
            }
        }
        if (notGranted.size() > 0) requestPermissions(notGranted.toArray(new String[0]), PERMISSIONS_CODE);
    }

    public String[] readPermission()
    {
        List<String> list = new ArrayList<>();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    Log.d("PERMISSION", "Permission : " + p);
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list.toArray(new String[0]);
    }
}
