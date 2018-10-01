package com.sonu.diary.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sonu.diary.R;
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
        return (null == DiaryCache.getDiaryEntries() || DiaryCache.getDiaryEntries().isEmpty())?0:DiaryCache.getDiaryEntries().size();
    }

    @Override
    public DiaryEntry getItem(int i) {
        return (null == DiaryCache.getDiaryEntries() || DiaryCache.getDiaryEntries().isEmpty())?null:DiaryCache.getDiaryEntries().get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiaryEntry de = DiaryCache.getDiaryEntries().get(position);

        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if( null == convertView) {
            assert vi != null;
            convertView = vi.inflate(R.layout.list_item_diary_entry, null);
            TextView txtDescription = (TextView) convertView.findViewById(R.id.description);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.deTitle);
            TextView txtCreationTime = (TextView) convertView.findViewById(R.id.creationTime);
            txtDescription.setText(de.getEntryDescription());
            txtTitle.setText(de.getEntryTitle());
            txtCreationTime.setText(DateUtils.getStringTimeFromTimestamp(de.getEntryActionTime()));
        } else {
            TextView txtDescription = (TextView) convertView.findViewById(R.id.description);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.deTitle);
            TextView txtCreationTime = (TextView) convertView.findViewById(R.id.creationTime);
            txtDescription.setText(de.getEntryDescription());
            txtTitle.setText(de.getEntryTitle());
            txtCreationTime.setText(DateUtils.getStringTimeFromTimestamp(de.getEntryActionTime()));
        }
        return convertView;
    }
}
