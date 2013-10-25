package com.example.kktoolkitdemo.messageview;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.ui.KKMessageView;

/**
 * Created by gigichien on 13/10/21.
 */
public class KKMessageViewActivity extends KKActivity {
    private KKMessageView mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMsg = new KKMessageView(this);
        this.setContentView(mMsg);
        mMsg.setSingleTextView("This is SingleTextView");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "setSingle");
        menu.add(0, 1, 1, "setMutiple");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
            case 0:
                mMsg.setSingleTextView("Loading...");
                break;
            case 1:
                mMsg.setMultiTextView("KKBOX Message", "Loading...");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
