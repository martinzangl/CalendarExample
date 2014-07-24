package com.zim.calendarexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

        private int position;

        public static PlaceholderFragment newInstance() {
            return new PlaceholderFragment();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);

            pager.setOffscreenPageLimit(0);
            SampleAdapter2 adapter2 = new SampleAdapter2(getActivity());
            SampleAdapter adapter = new SampleAdapter(getActivity(), getChildFragmentManager());
            pager.setAdapter(adapter2);
            pager.setCurrentItem(5);



            return rootView;
        }
    }

    public interface OnScrollListener {
        void onScrollUpdated(int child, int top);
    }





    private static class SampleAdapter2 extends PagerAdapter{
        private static final String TAG = SampleAdapter2.class.getName();
        private Context context;
        private int index;
        private int top;

        private List<OnScrollListener> listeners = new ArrayList<OnScrollListener>();

        private AbsListView.OnScrollListener scroll = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int t = (view.getChildAt(0) != null) ? view.getChildAt(0).getTop() : -1;
                Log.d(TAG, String.format("onScroll  top=%s, index=%s", t, firstVisibleItem));
                for(OnScrollListener listener : listeners){
                    if(view.isShown())
                    listener.onScrollUpdated(firstVisibleItem, t);
                }
            }
        };


        private final static String[] HOURS = {"08", "09", "10",
                "11", "12", "13", "14",
                "15", "16", "17", "18",
                "19", "20"};

        private SchedulerAdapter mAdapter;

        public SampleAdapter2(Context context) {
            this.context = context;
            mAdapter = new SchedulerAdapter(context);
            mAdapter.swapData(Arrays.asList(HOURS));

        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
            Log.d(TAG, String.format("startUpdate position=%s, top=%s, index=%s", container.getTag(), top, index));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.schedule_view, container, false);
            ScheduleDayListView listView = (ScheduleDayListView) view.findViewById(android.R.id.list);
            listView.setAdapter(mAdapter);
            listeners.add(listView);
            listView.setOnScrollListener(scroll);
            container.addView(view);

            container.setTag(new Integer(position));

            Log.d(TAG, String.format("instantiateItem position=%s, top=%s, index=%s", position, top, index));
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            final ViewGroup view = (ViewGroup) object;
            final ScheduleDayListView listView = (ScheduleDayListView) view.getChildAt(0);
            listeners.remove(listView);
            container.removeView((View) object);
            Log.d(TAG, String.format("destroyItem position=%s, top=%s, index=%s", position, top, index));
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

    }


    private static class SampleAdapter extends FragmentStatePagerAdapter {

        private Context context;

        public SampleAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Fragment getItem(int i) {
            return PlaceChildFragment.newInstance(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PlaceChildFragment.getTitle(context, position);
        }

    }

    public static class PlaceChildFragment extends Fragment {

        private static final String TAG = PlaceChildFragment.class.getName();

        private int position;

        private SchedulerAdapter mAdapter;

        static int index = 0;

        static int top;


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
            Log.d(TAG, String.format("top=%s, index=%s", top, index));
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
            ListView listView = (ListView) view.findViewById(android.R.id.list);

            mAdapter = new SchedulerAdapter(getActivity());
            mAdapter.swapData(Arrays.asList(HOURS));
            listView.setAdapter(mAdapter);

        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, String.format("onResume=%s", position));
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, String.format("onPause=%s", position));
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d(TAG, String.format("onDestroyView=%s", position));
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, String.format("onDestroy=%s", position));
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d(TAG, String.format("onDetach=%s", position));
        }

        public static CharSequence getTitle(Context context, int position) {
            return context.getString(R.string.position, position);
        }
    }
}
