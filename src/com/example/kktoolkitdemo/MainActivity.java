package com.example.kktoolkitdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kktoolkitdemo.actionbar.ActionBarActivity;
import com.example.kktoolkitdemo.eventqueue.EventQueueActivity;
import com.example.kktoolkitdemo.image.ActivityImage;
import com.example.kktoolkitdemo.listview.KKDragAndDropListViewActivity;
import com.example.kktoolkitdemo.listview.KKListViewActivity;
import com.example.kktoolkitdemo.messageview.KKMessageViewActivity;
import com.example.kktoolkitdemo.resizableview.ResizableViewActivity;
import com.example.kktoolkitdemo.tabfragment.ActivityTabFragment;
import com.example.kktoolkitdemo.viewpager.InfiniteViewPagerActivity;

public class MainActivity extends ListActivity {
    private String[] mStrings = {
            "KKActionBar",
            "KKMessageView",
            "ViewPager",
            "ResizableView",
            "EventQueue",
            "KKListView",
            "KKDragAndDropListView",
            "KKTabFragment",
            "KKImageManager"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStrings));
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        switch(position){
            case 0:
                intent = new Intent(MainActivity.this, ActionBarActivity.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, KKMessageViewActivity.class);
                break;
            case 2:
                intent = new Intent(MainActivity.this, InfiniteViewPagerActivity.class);
                break;
            case 3:
                intent = new Intent(MainActivity.this, ResizableViewActivity.class);
                break;
            case 4:
                intent = new Intent(MainActivity.this, EventQueueActivity.class);
                break;
            case 5:
                intent = new Intent(MainActivity.this, KKListViewActivity.class);
                break;
            case 6:
                intent = new Intent(MainActivity.this, KKDragAndDropListViewActivity.class);
                break;
            case 7:
                intent = new Intent(MainActivity.this, ActivityTabFragment.class);
                break;
            case 8:
                intent = new Intent(MainActivity.this, ActivityImage.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
