package com.sonu.diary.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.util.DateUtils;

import java.util.Map;

public class DiaryEntryAdapter extends BaseAdapter{

    private Activity context;

    public DiaryEntryAdapter(Activity context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return (null == CacheManager.getDiaryCache().getDiaryEntries() || CacheManager.getDiaryCache().getDiaryEntries().isEmpty())?0:CacheManager.getDiaryCache().getDiaryEntries().size();
    }

    @Override
    public DiaryEntry getItem(int i) {
        return (null == CacheManager.getDiaryCache().getDiaryEntries() || CacheManager.getDiaryCache().getDiaryEntries().isEmpty())?null:CacheManager.getDiaryCache().getDiaryEntries().get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiaryEntry de = CacheManager.getDiaryCache().getDiaryEntry(position);

        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(null == de){
            assert vi != null;
            return vi.inflate(R.layout.list_item_diary_entry, null);
        }
 
        if( null == convertView) {
            assert vi != null;
            convertView = vi.inflate(R.layout.list_item_diary_entry, null);
            populateViewFields(convertView, de);
        } else {
            populateViewFields(convertView, de);
        }
        return convertView;
    }

    private void populateViewFields(View convertView, DiaryEntry de){
        TextView txtDescription =  convertView.findViewById(R.id.description);
        TextView txtTitle = convertView.findViewById(R.id.deTitle);
        TextView txtCreationTime =  convertView.findViewById(R.id.creationTime);
        txtDescription.setText(de.getEntryDescription());
        if(null != de.getEntryTitle() && de.getEntryTitle().contains("eventName")){
            Gson gson = new Gson();
            Map<String, String> map = gson.<Map<String, String>>fromJson(de.getEntryTitle(), Map.class);
            txtTitle.setText(String.format("%s|%s", map.get("eventName"), map.get("category")));
        }else {
            txtTitle.setText(de.getEntryTitle());
        }
        String strTime = "[" +
                DateUtils.getStringDateFromTimestampInFormat(de.getEntryActionTime(), "dd/MMM/YY hh:mm") +
                "]:";
        txtCreationTime.setText(strTime);
        TextView expense = convertView.findViewById(R.id.deExpense);
        if(de.isExpenseAdded() || (null != de.getEntryExpenditure()) && de.getEntryExpenditure() != 0){
            String sb = de.getEntryExpenditureSource() +
                    ":₹" +
                    String.valueOf(de.getEntryExpenditure());
            expense.setText(sb);
        } else {
            expense.setText("-");
        }
        Long id = de.getEntryId();
        convertView.setTag(id);
    }
}
