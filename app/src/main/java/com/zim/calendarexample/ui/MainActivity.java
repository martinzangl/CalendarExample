package com.zim.calendarexample.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zim.calendarexample.R;
import com.zim.calendarexample.ui.adapter.SchedulerAdapter;
import com.zim.calendarexample.ui.adapter.ViewPagerAdapter;

import java.util.Arrays;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {
        private static final String TAG = PlaceholderFragment.class.getName();

        public static PlaceholderFragment newInstance() {
            return new PlaceholderFragment();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);

            final ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());

            pager.setAdapter(adapter);


            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    adapter.updateList(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            return rootView;
        }
    }

    public interface OnScrollListener {
        void onScrollUpdated(int child, int top);
    }

    public static class PlaceChildFragment extends Fragment {

        private static final String TAG = PlaceChildFragment.class.getName();

        private int position;

        private ListView listView;

        private static Parcelable state;
        private static int child;
        private static int top;

        private SchedulerAdapter mAdapter;


        private final static String[] HOURS = {"08", "09", "10",
                "11", "12", "13", "14",
                "15", "16", "17", "18",
                "19", "20"};

        public static PlaceChildFragment newInstance() {
            return new PlaceChildFragment();
        }

        public static PlaceChildFragment newInstance(int position) {

            PlaceChildFragment fragment = new PlaceChildFragment();
            fragment.position = position;
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_child, container, false);

            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            listView = (ListView) view.findViewById(android.R.id.list);

            mAdapter = new SchedulerAdapter(getActivity());
            mAdapter.swapData(Arrays.asList(HOURS));
            listView.setAdapter(mAdapter);

        }

        public static CharSequence getTitle(Context context, int position) {
            return context.getString(R.string.position, position);
        }

        @Override
        public void onResume() {
            super.onResume();
            if(state != null){
                listView.onRestoreInstanceState(state);
                listView.setSelectionFromTop(child, top);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            state = listView.onSaveInstanceState();
            top = listView.getChildAt(0).getTop();
            child = listView.getFirstVisiblePosition();
        }
    }
}
