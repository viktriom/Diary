package com.sonu.diary.handers.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sonu on 18/01/18.
 */

public class ConsolidatedViewAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<>();

    public ConsolidatedViewAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
