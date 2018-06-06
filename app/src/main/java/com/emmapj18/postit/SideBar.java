package com.emmapj18.postit;

import android.app.Activity;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class SideBar implements Drawer.OnDrawerItemClickListener {

    private Drawer mDrawner;

    SideBar(Activity activity) {
        DimenHolder dimenHolder = new DimenHolder();
        dimenHolder.setDp(150);

        mDrawner = new DrawerBuilder()
                .withActivity(activity)
                .inflateMenu(R.menu.menu_layout)
                .withHeader(R.layout.menu_header)
                .withHeaderDivider(false)
                .withHeaderHeight(dimenHolder)
                .withFullscreen(false)
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(-1)
                .build();
    }

    public Drawer getMenu() {
        return mDrawner;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        return false;
    }


}
