package com.example.kktoolkitdemo.listview;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.kkbox.toolkit.internal.ui.KKListViewOnRefreshListener;
import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.ui.KKListView;

/**
 * Created by gigichien on 13/10/22.
 */
public class KKListViewActivity extends KKActivity {
    private KKListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout root = new FrameLayout(this);
        this.setContentView(root);
        mListView = new KKListView(this);
        root.addView(mListView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setPullToRefresh(pullToRefreshListener);
        mListView.setLoadMore(loadMoreListener);
        // Get a cursor with all people
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                CONTACT_PROJECTION, null, null, null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                // Use a template that displays a text view
                android.R.layout.simple_list_item_1,
                // Give the cursor to the list adatper
                c,
                // Map the NAME column in the people database to...
                new String[] {ContactsContract.Contacts.DISPLAY_NAME},
                // The "text1" view defined in the XML template
                new int[] {android.R.id.text1});
        mListView.setAdapter(adapter);

}
    private static final String[] CONTACT_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };
    private KKListView.OnRefreshListener pullToRefreshListener = new KKListView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mListView.loadCompleted();
//            peopleItems.clear();
//            saveListViewPosition();
//            getPeopleListItems(0);
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
}
