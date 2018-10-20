package com.sonu.diary.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.caches.DiaryCache;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.util.DateUtils;

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
        TextView txtDescription = (TextView) convertView.findViewById(R.id.description);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.deTitle);
        TextView txtCreationTime = (TextView) convertView.findViewById(R.id.creationTime);
        txtDescription.setText(de.getEntryDescription());
        txtTitle.setText(de.getEntryTitle());
        txtCreationTime.setText("[" + DateUtils.getStringTimeFromTimestamp(de.getEntryActionTime()) + "]:");
        TextView expense = (TextView)convertView.findViewById(R.id.deExpense);
        if(de.isExpenseAdded()){
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
