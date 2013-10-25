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
    ExampleService mService;
    TextView mTextView;
    @Override
    protected void onServiceStarted() {
        super.onServiceStarted();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout mRoot = new FrameLayout(this);
        setContentView(mRoot);
        mService = new ExampleService();
        mService.onStartCommand(null,0, 0);
    }
}
