package com.example.kktoolkitdemo.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.example.kktoolkitdemo.notification.ExampleService;
import com.kkbox.toolkit.ui.KKServiceActivity;

/**
 * Created by gigichien on 13/10/24.
 */
public class ServiceActivity extends KKServiceActivity{
    TextView mTextView;
    ExampleService mService;
    @Override
    protected void onServiceStarted() {
        super.onServiceStarted();
        mTextView.setText("onServiceStarted");
        Log.v("GGG", "onServiceStarted");

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout mRoot = new FrameLayout(this);
        mTextView = new TextView(this);
        mRoot.addView(mTextView);
        setContentView(mRoot);
        mService = new ExampleService();
        mService.onStartCommand(null,0, 0);
    }
}
