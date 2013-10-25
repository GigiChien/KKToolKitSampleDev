package com.example.kktoolkitdemo.listview;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.kktoolkitdemo.api.ExampleForecastAPI;
import com.example.kktoolkitdemo.api.ExampleWeatherAPI;
import com.kkbox.toolkit.api.KKAPIListener;
import com.kkbox.toolkit.api.KKAPIRequest;
import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.ui.KKListView;

import java.util.ArrayList;

/**
 * Created by gigichien on 13/10/22.
 */
public class KKListViewActivity extends KKActivity {
    private KKListView mListView;
    private ExampleForecastAPI mAPI;
    private KKAPIRequest mRequest;
    String mDay[] = {"Temperature Day 1 : ",
            "Temperature Day 2 : ",
            "Temperature Day 3 : ",
            "Temperature Day 4 : ",
            "Temperature Day 5 : ",
            "Temperature Day 6 : ",
            "Temperature Day 7 : "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout root = new FrameLayout(this);
        this.setContentView(root);
        mListView = new KKListView(this);
        root.addView(mListView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setPullToRefresh(pullToRefreshListener);
        mListView.setLoadMore(loadMoreListener);
        ArrayAdapter adapter = new ArrayAdapter(KKListViewActivity.this, android.R.layout.simple_list_item_1, mDay);
        mListView.setAdapter(adapter);
        loadForecastData();
   }

    private KKListView.OnRefreshListener pullToRefreshListener = new KKListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadForecastData();
        }
    };

    private KKListView.OnLoadMoreListener loadMoreListener = new KKListView.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            mListView.loadMoreFinished();
//            saveListViewPosition();
//            getPeopleListItems(peopleItems.size());
        }
    };

    private KKAPIListener exampleAPIListener = new KKAPIListener() {
        @Override
        public void onAPIComplete() {
            ArrayList<Float> mData = mAPI.getForecastData();
            int size = mData.size();
            String mTemp[];
            mTemp = new String[size];
            for (int i = 0; i < size; i++) {
                mTemp[i] = mDay[i] + mData.get(i).toString().substring(0, 2);
            }
            ArrayAdapter adapter = new ArrayAdapter(KKListViewActivity.this, android.R.layout.simple_list_item_1, mTemp);
            mListView.setAdapter(adapter);

            mListView.loadCompleted();
            mListView.loadMoreFinished();
        }

        @Override
        public void onAPIError(int errorCode) {

        }
    };

    private void loadForecastData(){
        if(mAPI == null)
        {
            mAPI = new ExampleForecastAPI();
            mAPI.setAPIListener(exampleAPIListener);
        }

        String inputURL = "http://api.openweathermap.org/data/2.5/forecast/daily?id=2643743";
        mRequest = new KKAPIRequest(inputURL, null);
        mAPI.start(mRequest);
    }

}
