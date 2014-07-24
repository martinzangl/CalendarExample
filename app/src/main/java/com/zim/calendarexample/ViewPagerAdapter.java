package com.zim.calendarexample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by martin.zangl on 7/24/14.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> mViews = new ArrayList<View>();
    private final Context mContext;

    private int lastFirstItem;
    private int lastTop;


    private final AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            lastFirstItem = firstVisibleItem;
            lastTop = view.getChildAt(0) == null ? 0 : view.getChildAt(0).getTop();
        }
    };

    private final static String[] HOURS = {"08", "09", "10",
            "11", "12", "13", "14",
            "15", "16", "17", "18",
            "19", "20"};

    private SchedulerAdapter mAdapter;

    public ViewPagerAdapter(Context context) {
        mContext = context;
        mAdapter = new SchedulerAdapter(context);
        mAdapter.swapData(Arrays.asList(HOURS));
    }

    public View getView(int position) {
        return mViews.get(position);
    }

    public void updateList(int newPosition) {
        View newView = getView(newPosition);
        if (newView == null) return;

        ListView newList = (ListView) ((ViewGroup) newView).getChildAt(0);

        newList.setSelectionFromTop(lastFirstItem, lastTop);

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mViews.size() > position) {
            View view = getView(position);
            if (view != null) {
                updateList(position);
                return view;
            }
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_view, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);


        while (mViews.size() <= position) {
            mViews.add(null);
        }

        mViews.set(position, view);

        updateList(position);
        listView.setOnScrollListener(onScrollListener);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        mViews.set(position, null);

        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
