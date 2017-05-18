package com.sonu.diary.handers.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.bean.DiaryEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sonu on 17/12/16.
 */

public class DashboardUIHandler extends ListActivity{


    private ArrayList<String> listItems = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    public void displayAllEntriesForToday(Date date, AppCompatActivity dashboardUI) throws SQLException {
        List<DiaryEntry> diaryEntries = DatabaseManager.getInstance().getHelper().getDao(DiaryEntry.class).queryForAll();
        TableLayout tableLayout = (TableLayout) dashboardUI.findViewById(R.id.dashboard);
        for(DiaryEntry entry : diaryEntries) {
            TableRow tableRow = new TableRow(dashboardUI);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);
            TextView title = new TextView(dashboardUI);
            TextView desc = new TextView(dashboardUI);
            TextView exp = new TextView(dashboardUI);
            title.setText(entry.getEntryTitle());
            desc.setText(entry.getEntryDescription());
            exp.setText(entry.getLocation());
            tableRow.addView(title);
            tableRow.addView(desc);
            tableRow.addView(exp);
            tableLayout.addView(tableRow);

            listItems.add(entry.toString());
        }
        //adapter.notifyDataSetChanged();
        System.out.println("Testing diary entry.");
    }

    public void onCreate(Bundle iCicle){
        super.onCreate(iCicle);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
    }
}
