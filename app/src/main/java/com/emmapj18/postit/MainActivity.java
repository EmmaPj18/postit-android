package com.emmapj18.postit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.Listeners.FeedListener;
import com.emmapj18.postit.Listeners.MenuListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MenuListener{

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
        setFragment(R.id.frameLayoutBody, new FeedFragment());
    }

    @Override
    public void onPostButtonPressed() {
        setFragment(R.id.frameLayoutBody, new PostFragment());
    }

    @Override
    public void onProfileButtonPressed() {

    }
}
