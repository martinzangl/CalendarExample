package com.zim.calendarexample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by martin.zangl on 7/24/14.
 */
public class ScheduleDayListView extends ListView implements MainActivity.OnScrollListener{

    public ScheduleDayListView(Context context) {
        super(context);
    }

    public ScheduleDayListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScheduleDayListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onScrollUpdated(int child, int top) {
        setSelectionFromTop(child, top);
    }
}
