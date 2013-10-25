package com.example.kktoolkitdemo.eventqueue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kktoolkitdemo.R;
import com.kkbox.toolkit.ui.KKActivity;
import com.kkbox.toolkit.utils.KKEventQueue;
import com.kkbox.toolkit.utils.KKEventQueueListener;

import java.util.ArrayList;


/**
 * Created by gigichien on 13/10/18.
 */
public class EventQueueActivity extends KKActivity {
    private KKEventQueue eventQueue;
    TextView txtEventStatus, txtLockID;
    int mID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_eventqueue);

        setEventQueueUsage();
        txtEventStatus.setText("Status : Not started");
    }

    ArrayList<Integer> mLockId;

    private void setEventQueueUsage(){
        eventQueue = new KKEventQueue();
        eventQueue.setListener(eventQueuelistener);

        Button btnAddEvent, btnClearEvent, btnStartEvent, btnLockEvent, btnUnlockEvent, btnUnlockAllEvent;
        btnAddEvent = (Button) findViewById(R.id.add__event);
        btnStartEvent = (Button) findViewById(R.id.start_event);
        btnClearEvent = (Button) findViewById(R.id.clear_event);
        btnLockEvent = (Button) findViewById(R.id.lock_event);
        btnUnlockEvent = (Button) findViewById(R.id.unlock_event);
        btnUnlockAllEvent = (Button) findViewById(R.id.unlock_all_event);

        txtLockID = (TextView) findViewById(R.id.lock_id);
        txtEventStatus = (TextView) findViewById(R.id.job_status);

        mLockId = new ArrayList<Integer>();

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventQueue.addNewThreadEvent(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {

                            }
                        }
                );
            }
        });

        btnStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventQueue.isRunning())
                    txtEventStatus.setText("Status : Running");
                eventQueue.start();
            }
        });

        btnLockEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final int id = mID++;
               mLockId.add(id);
               printLockID();
                eventQueue.addCallerThreadEventWithLock(new Runnable() {
                    @Override
                    public void run() {
                        txtEventStatus.setText("Status : Locked,  ID = " + id);
                    }
                }, id);
            }
        });

        btnUnlockEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mLockId.isEmpty())
                {
                    eventQueue.unlockEvent(mLockId.get(0));
                    mLockId.remove(0);
                    printLockID();

                    if (eventQueue.isRunning())
                    {
                        txtEventStatus.setText("Status : Running");
                    }
                }
            }
        });

        btnUnlockAllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.unlockAllEvents();
                mLockId.clear();
                printLockID();
                if (eventQueue.isRunning())
                {
                    txtEventStatus.setText("Status : Running");
                }
            }
        });

        btnClearEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.clearPendingEvents();
                if (eventQueue.isRunning()) {
                    txtEventStatus.setText("Status : Finished");
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

    private void printLockID(){
        String s = "";
        for(Integer id : mLockId){
            s += "Lock ID : "+id + "\n";
        }
        txtLockID.setText(s);
    }

}
