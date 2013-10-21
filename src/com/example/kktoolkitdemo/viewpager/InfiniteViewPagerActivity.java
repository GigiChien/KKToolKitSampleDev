package com.example.kktoolkitdemo.viewpager;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.kkbox.toolkit.listview.adapter.InfiniteViewPagerAdapter;
import com.kkbox.toolkit.ui.InfiniteViewPager;
import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.ui.OnInfiniteViewPagerPageChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gigichien on 13/10/21.
 */
public class InfiniteViewPagerActivity extends KKActivity {
    private InfiniteViewPager mViewPager;

    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mViewPager = (InfiniteViewPager) findViewById(R.id.view_pager);


        ArrayList<Integer> mColorList = new ArrayList<Integer>();
        mColorList.add(1);
        mColorList.add(2);
        mColorList.add(3);

        mAdapter = new ViewPagerAdapter(mColorList, true);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new OnInfiniteViewPagerPageChangeListener(mViewPager) {
            @Override
            public void onLoopPageSelected(int position) {

            }

            @Override
            public void onPageScrollLeft() {

            }

            @Override
            public void onPageScrollRight() {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private class ViewPagerAdapter extends InfiniteViewPagerAdapter{

        public ViewPagerAdapter(ArrayList<Integer> content, boolean loopEnabled) {
            super((ArrayList)content, loopEnabled);
            Log.v("GGG", "views size - "+content.size());
        }

        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public boolean isLoopEnabled() {
            return super.isLoopEnabled();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.v("GGG", "instantiateItem - "+position);
            TextView v = new TextView(getApplicationContext());
            v.setGravity(Gravity.CENTER);
            Integer index = (Integer)getItem(position);
            switch(index.intValue()){
                case 1:
                    v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    v.setText("Page 1");
                    break;
                case 2:
                    v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                    v.setText("Page 2");
                    break;
                case 3:
                    v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    v.setText("Page 3");
                    break;

            }

            ((ViewPager)container).addView(v);
            return v;
        }
    }
}
