package com.emmapj18.postit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    Drawer sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sideBar = new SideBar(this).getMenu();
        sideBar.openDrawer();

        mAuth = FirebaseAuth.getInstance();
    }
}
