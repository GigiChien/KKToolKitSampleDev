package com.example.kktoolkitdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kktoolkitdemo.actionbar.ActionBarActivity;
import com.example.kktoolkitdemo.eventqueue.EventQueueActivity;

public class MainActivity extends ListActivity {
    private String[] mStrings = {
            "KKActionBar",
            "KKMessageView",
            "ViewPager",
            "ResizableView",
            "EventQueue"};
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
            case 4:
                intent = new Intent(MainActivity.this, EventQueueActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
