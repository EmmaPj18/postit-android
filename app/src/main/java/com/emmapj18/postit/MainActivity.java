package com.emmapj18.postit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuFragmentListener{

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mAuth = FirebaseAuth.getInstance();

        setFragment(R.id.frameLayoutMenu, new MenuFragment());
        setFragment(R.id.frameLayoutBody, new FeedFragment());
    }

    public void setFragment(int frameLayoutId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayoutId, fragment);
        transaction.commit();
    }

    @Override
    public void onHomeButtonPressed() {

    }

    @Override
    public void onPostButtonPressed() {

    }

    @Override
    public void onProfileButtonPressed() {

    }
}
