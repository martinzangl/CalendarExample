package com.zim.calendarexample.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zim.calendarexample.R;

/**
 * Created by martin.zangl on 7/23/14.
 */
public class ScheduleItem extends LinearLayout {

    private TextView textViewHour;
    private TextView textViewEvent;

    public ScheduleItem(Context context) {
        super(context);
    }

    public ScheduleItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScheduleItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textViewEvent = (TextView) findViewById(R.id.text_event);
        textViewHour = (TextView) findViewById(R.id.text_hour);
    }

    public void setHour(String hour) {
        textViewHour.setText(hour);
    }

    public void setEventName(String eventName) {
        textViewEvent.setText(eventName);
    }
}
