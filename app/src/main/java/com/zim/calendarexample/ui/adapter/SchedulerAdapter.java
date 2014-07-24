package com.zim.calendarexample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zim.calendarexample.R;
import com.zim.calendarexample.ui.view.ScheduleItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by martin.zangl on 7/23/14.
 */
public class SchedulerAdapter extends BindableAdapter<String> {

    private List<String> data = Collections.emptyList();

    public SchedulerAdapter(Context context) {
        super(context);
    }

    public final void swapData(List<String> newData){
        if(newData != null && newData.size() > 0){
            data = newData;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.item_view_schedule, container, false);
    }

    @Override
    public void bindView(String item, int position, View view) {
        final ScheduleItem scheduleItem = (ScheduleItem) view;
        scheduleItem.setHour(item);
    }
}
