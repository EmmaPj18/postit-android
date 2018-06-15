package com.emmapj18.postit;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emmapj18.postit.Helpers.PermissionHelper;
import com.emmapj18.postit.Listeners.MenuListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MenuListener{

    FirebaseAuth mAuth;
    public static FragmentManager mManager;
    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mAuth = FirebaseAuth.getInstance();
        mManager = getSupportFragmentManager();

        setFragment(mManager, R.id.frameLayoutMenu, new MenuFragment());
        setFragment(mManager, R.id.frameLayoutBody, new FeedFragment());

        permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onPermissionResult(requestCode, permissions, grantResults);
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
}
