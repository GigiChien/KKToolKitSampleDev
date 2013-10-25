package com.example.kktoolkitdemo.eventqueue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.utils.KKEventQueue;
import com.kkbox.toolkit.utils.KKEventQueueListener;



/**
 * Created by gigichien on 13/10/18.
 */
public class EventQueueActivity extends KKActivity {
    private KKEventQueue eventQueue;
    TextView txtNumPending, txtEventStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_eventqueue);

        setEventQueueUsage();
        mHandler.sendEmptyMessage(UPDATE_PENDING_NUMBER);
        txtEventStatus.setText("Status : Finished");
    }

    int mPending = 0;
    private void setEventQueueUsage(){
        eventQueue = new KKEventQueue();
        eventQueue.setListener(eventQueuelistener);

        Button btnAddEvent, btnClearEvent, btnStartEvent;
        btnAddEvent = (Button) findViewById(R.id.add__event);
        btnStartEvent = (Button) findViewById(R.id.start_event);
        btnClearEvent = (Button) findViewById(R.id.clear_event);

        txtNumPending = (TextView) findViewById(R.id.pending_num);
        txtEventStatus = (TextView) findViewById(R.id.job_status);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPending ++;
                txtNumPending.setText("Pending : "+ mPending);
                eventQueue.addNewThreadEvent(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mPending--;
                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {
                                txtNumPending.setText("Pending : " + mPending);
                            }
                        }
                );
            }
        });

        btnStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.start();
                txtEventStatus.setText("Status : Running");
            }
        });

        btnClearEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.clearPendingEvents();
                if(!eventQueue.isRunning()){
                    mPending = 0;
                    txtEventStatus.setText("Status : Finished");
                    mHandler.sendEmptyMessage(UPDATE_PENDING_NUMBER);
                } else {
                    mPending = 1;
                }
            }
        });
    }
    private final KKEventQueueListener eventQueuelistener = new KKEventQueueListener() {
        @Override
        public void onQueueCompleted() {
            txtEventStatus.setText("Status : Finished");
        }
    };
    @Override
    protected void onDestroy() {
        if(eventQueue != null)
        {
            eventQueue.clearPendingEvents();
        }
        super.onDestroy();
    }

    private static final int UPDATE_PENDING_NUMBER = 2;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_PENDING_NUMBER:
                    txtNumPending.setText("Pending : "+ mPending);
                    break;

            }
        }
    };
}
