package com.sonu.diary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.adapters.DiaryEntryAdapter;
import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.handers.ui.DashboardUIHandler;
import com.sonu.diary.util.DateUtils;
import com.sonu.diary.util.cartesian.CartesianCoordinate;
import com.sonu.diary.util.cartesian.CircularPlottingSystem;

import java.sql.SQLException;
import java.util.Date;


public class MainActivity extends AbstractActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean floatingMenuShown = false;
    private ListView lstEntries;
    private DiaryEntryAdapter adapter;
    private TextView pageDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDbOperations();
        initializeUI();

        adapter = new DiaryEntryAdapter(MainActivity.this);

        lstEntries.setAdapter(adapter);

        lstEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });


    }

    private void initializeUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
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

        lstEntries = (ListView) findViewById(R.id.lstEntries);

        pageDate = (TextView)findViewById(R.id.txtViewPageDate);
        pageDate.setText(DateUtils.getStringDateFromTimestampInFormat(DateUtils.getCurrentTimestamp(), DateUtils.DEFAULT_DATE_FORMAT));

    }


    private void initializeDbOperations() {
        DatabaseManager.init(this.getApplicationContext());
        DatabaseManager dbManager = DatabaseManager.getInstance();
        DatabaseHelper dbHelper = dbManager.getHelper();
        dbHelper.dropAllTables();
        dbHelper.createTableForAllTheBeans();
        dbHelper.truncateAllTables();
        dbHelper.createDiaryForCurrentYear();
    }

    private void handleFloatingMenu() {
        FloatingActionButton fab1 = (FloatingActionButton)findViewById(R.id.fab_1);
        FloatingActionButton fab2 = (FloatingActionButton)findViewById(R.id.fab_2);
        FloatingActionButton fab3 = (FloatingActionButton)findViewById(R.id.fab_3);
        FloatingActionButton fab4 = (FloatingActionButton)findViewById(R.id.fab_4);
        FloatingActionButton fab5 = (FloatingActionButton)findViewById(R.id.fab_5);
        Animation showFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        Animation hideFab1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        Animation showFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        Animation hideFab2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        Animation showFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        Animation hideFab3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
        Animation showFab4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        Animation hideFab4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);
        Animation showFab5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_show);
        Animation hideFab5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab5_hide);

        assert fab1 != null;
        assert fab2 != null;
        assert fab3 != null;
        assert fab4 != null;
        assert fab5 != null;

        CircularPlottingSystem ps = new CircularPlottingSystem(new CartesianCoordinate(0d,0d),3,4,90,180);

        if(!floatingMenuShown) {
            showFloatingMenu(fab1, fab1.getWidth() * ps.getAbsNthPoint(0).getY(), fab1.getHeight() * ps.getAbsNthPoint(0).getX(), showFab1);
            showFloatingMenu(fab2, fab2.getWidth() * ps.getAbsNthPoint(1).getY(), fab2.getHeight() * ps.getAbsNthPoint(1).getX(), showFab2);
            showFloatingMenu(fab3, fab3.getWidth() * ps.getAbsNthPoint(2).getY(), fab3.getHeight() * ps.getAbsNthPoint(2).getX(), showFab3);
            showFloatingMenu(fab4, fab4.getWidth() * ps.getAbsNthPoint(3).getY(), fab4.getHeight() * ps.getAbsNthPoint(3).getX(), showFab4);
            showFloatingMenu(fab5, fab5.getWidth() * ps.getAbsNthPoint(4).getY(), fab5.getHeight() * ps.getAbsNthPoint(4).getX(), showFab5);
        }else {
            hideFloatingMenu(fab1, fab1.getWidth() * ps.getAbsNthPoint(0).getY(), fab1.getHeight() * ps.getAbsNthPoint(0).getX(), hideFab1);
            hideFloatingMenu(fab2, fab2.getWidth() * ps.getAbsNthPoint(1).getY(), fab2.getHeight() * ps.getAbsNthPoint(1).getX(), hideFab2);
            hideFloatingMenu(fab3, fab3.getWidth() * ps.getAbsNthPoint(2).getY(), fab3.getHeight() * ps.getAbsNthPoint(2).getX(), hideFab3);
            hideFloatingMenu(fab4, fab4.getWidth() * ps.getAbsNthPoint(3).getY(), fab4.getHeight() * ps.getAbsNthPoint(3).getX(), hideFab4);
            hideFloatingMenu(fab5, fab5.getWidth() * ps.getAbsNthPoint(4).getY(), fab5.getHeight() * ps.getAbsNthPoint(4).getX(), hideFab5);
        }

    }

    private void showFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin += (int) (rightMargin);
        layoutParams.bottomMargin += (int) (leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(true);
        floatingMenuShown = true;
    }

    private void hideFloatingMenu(FloatingActionButton fab, double rightMargin, double leftMargin, Animation animation) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (rightMargin);
        layoutParams.bottomMargin -= (int) (leftMargin);
        fab.setLayoutParams(layoutParams);
        fab.startAnimation(animation);
        fab.setClickable(false);
        floatingMenuShown = false;
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

    @SuppressWarnings("Stateme  ntWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_routine) {
            //showRoutineEntry(item.getActionView());
            DashboardUIHandler dashboardUIHandler = new DashboardUIHandler();
            try {
                dashboardUIHandler.displayAllEntriesForToday(new Date(DateUtils.getCurrentTimestamp().getTime()), this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showGeneralEntry(View view){
        Intent intent = new Intent(this, GeneralEntry.class);
        handleFloatingMenu();
        startActivity(intent);
    }

    public void showRoutineEntry(View view) {
        //Intent intent = new Intent(this, RoutineActivity.class);
        //startActivity(intent);
        DashboardUIHandler dashboardUIHandler = new DashboardUIHandler();
        try {
            dashboardUIHandler.displayAllEntriesForToday(new Date(DateUtils.getCurrentTimestamp().getTime()), this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
