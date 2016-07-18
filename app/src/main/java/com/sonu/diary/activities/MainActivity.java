package com.sonu.diary.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.sonu.diary.R;
import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean floatingMenuShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFloatingMenu();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        //Database stuff
        DatabaseManager.init(this.getApplicationContext());
        DatabaseManager dbManager = DatabaseManager.getInstance();
        DatabaseHelper dbHelper = dbManager.getHelper();
        dbHelper.createTableForAllTheBeans();
        dbHelper.truncateAllTables();
        dbHelper.createDiaryForCurrentYear();
    }

    private void handleFloatingMenu() {
        FloatingActionButton fab1 = (FloatingActionButton)findViewById(R.id.fab_1);
        FloatingActionButton fab2 = (FloatingActionButton)findViewById(R.id.fab_2);
        FloatingActionButton fab3 = (FloatingActionButton)findViewById(R.id.fab_3);
        Animation showFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        Animation hideFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        Animation showFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        Animation hideFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        Animation showFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        Animation hideFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        assert fab1 != null;
        assert fab2 != null;
        assert fab3 != null;

        if(!floatingMenuShown) {
            showFloatingMenu(fab1, fab1.getWidth() * 1.7, fab1.getHeight() * 0.25, showFab1);
            showFloatingMenu(fab2, fab1.getWidth() * 1.5, fab1.getHeight() * 1.5, showFab2);
            showFloatingMenu(fab3, fab1.getWidth() * 0.25, fab1.getHeight() * 1.7, showFab3);
            floatingMenuShown = true;
        }else {
            hideFloatingMenu(fab1, fab1.getWidth() * 1.7, fab1.getHeight() * 0.25, hideFab1);
            hideFloatingMenu(fab2, fab1.getWidth() * 1.5, fab1.getHeight() * 1.5, hideFab2);
            hideFloatingMenu(fab3, fab1.getWidth() * 0.25, fab1.getHeight() * 1.7, hideFab3);
            floatingMenuShown = false;
        }
    }

    private void showFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (rightMargin);
        layoutParams.bottomMargin += (int) (leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(true);
    }

    private void hideFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (rightMargin);
        layoutParams.bottomMargin -= (int) (leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
