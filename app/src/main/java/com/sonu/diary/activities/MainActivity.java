package com.sonu.diary.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sonu.diary.R;
import com.sonu.diary.adapters.DiaryEntryAdapter;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.remote.EntryDataReceived;

import com.sonu.diary.remote.SyncService;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.DateUtils;
import com.sonu.diary.util.SecurityUtil;
import com.sonu.diary.util.cartesian.CartesianCoordinate;
import com.sonu.diary.util.cartesian.CircularPlottingSystem;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class MainActivity extends AbstractActivity implements Serializable, EntryDataReceived {

    private boolean floatingMenuShown = false;
    private ListView lstEntries;
    private DiaryEntryAdapter adapter;
    private TextView txtExpenseForDate;
    private TextView txtExpenseForMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager.init(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(null == CacheManager.getDiaryCache().getOwner()){
            Intent intent = new Intent(this, UserDetailsActivity.class);
            handleFloatingMenu();
            startActivity(intent);
        }

        initializeDbOperations();
        initializeUI();

        adapter = new DiaryEntryAdapter(MainActivity.this);
        lstEntries.setAdapter(adapter);
        Context context = this;

        lstEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent intent = new Intent(context, GeneralEntry.class);
                long entryId = (Long)view.getTag();
                intent.putExtra("entryId",entryId);
                startActivity(intent);
            }
        });
    }

    private void initializeUI() {

        FloatingActionButton fab = findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFloatingMenu();
            }
        });

        lstEntries = findViewById(R.id.lstEntries);

        TextView pageDate = findViewById(R.id.txtViewPageDate);
        pageDate.setText(DateUtils.getStringDateFromTimestampInFormat(DateUtils.getCurrentTimestamp(), DateUtils.DEFAULT_DATE_FORMAT));

        txtExpenseForDate = findViewById(R.id.txtExpenseForDate);
        txtExpenseForMonth = findViewById(R.id.txtExpenseForMonth);
        String sb = "Expense for Today: ₹" + CacheManager.getDiaryCache().getTotalExpenseForPage();
        txtExpenseForDate.setText(sb);
    }

    protected void onPostResume() {
        super.onPostResume();
        adapter.notifyDataSetChanged();

        String sb = "Expense for Today: ₹" + CacheManager.getDiaryCache().getTotalExpenseForPage();
        txtExpenseForDate.setText(sb);

        sb = "Expense for Month: ₹" + CacheManager.getDiaryCache().getTotalExpenseForCurrentMonth();
        txtExpenseForMonth.setText(sb);

        SyncService.getEntriesFromServer(this);
        SyncService.syncDiaryEntries(this);
    }

    private void initializeDbOperations() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        DatabaseHelper dbHelper = dbManager.getHelper();
        dbHelper.createTableForAllTheBeans();
        dbHelper.createDiaryForCurrentYear();
        CacheManager.getDiaryCache().initializeCaches(this);
    }

    private void handleFloatingMenu() {
        FloatingActionButton fab1 = findViewById(R.id.fab_1);
        FloatingActionButton fab2 = findViewById(R.id.fab_2);
        FloatingActionButton fab3 = findViewById(R.id.fab_3);
        FloatingActionButton fab4 = findViewById(R.id.fab_4);
        FloatingActionButton fab5 = findViewById(R.id.fab_5);
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void showGeneralEntry(View view){
        Intent intent = new Intent(this, GeneralEntry.class);
        handleFloatingMenu();
        startActivity(intent);
    }

    public void showRoutineEntry(View view) {
        SyncService.syncPendingData(this);
        Intent intent = new Intent(this, TrendsViewController.class);
        handleFloatingMenu();
        startActivity(intent);
        SyncService.testEnc();
    }

    public void lblDateTouched(View view) {
        super.showDatePicker(view, DateUtils.getCurrentTimestamp());
    }

    @Override
    public void performActionAfterDateTimeUpdate(Timestamp ts) {
        CacheManager.getDiaryCache().setCurrentPageId(DateUtils.getNumericDateForPageId(ts));
        adapter.notifyDataSetChanged();
        String sb = "Expense for selected Date is : ₹" + CacheManager.getDiaryCache().getTotalExpenseForPage();
        txtExpenseForDate.setText(sb);
    }

    public void imgFilterTouched(View view) {
        Intent intent = new Intent(this, FilterViewController.class);
        handleFloatingMenu();
        startActivity(intent);
    }

    public void showLoginView(View view) {

    }

    @Override
    public void dataReceived(List<DiaryEntry> entries){
        adapter.notifyDataSetChanged();
    }
}
