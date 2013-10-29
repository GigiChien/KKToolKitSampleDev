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
    private int mID = 1;
    private int pendingNumber = 0;
    private ArrayList<Integer> lockIDs;

    private TextView labelEventStatus;
    private TextView labelLockID;
    private TextView labelPending;
    private Button btnAddEvent;
    private Button btnClearEvent;
    private Button btnStartEvent;
    private Button btnLockEvent;
    private Button btnUnlockEvent;
    private Button btnUnlockAllEvent;

    private final KKEventQueueListener eventQueueListener = new KKEventQueueListener() {
        @Override
        public void onQueueCompleted() {
            printStatus(eventStatus.FINISHED);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_eventqueue);

        initUI();
        setEventQueueUsage();

    }

    private void initUI() {

        labelLockID = (TextView) findViewById(R.id.lock_id);
        labelEventStatus = (TextView) findViewById(R.id.job_status);
        labelPending = (TextView) findViewById(R.id.label_pending);

        btnAddEvent = (Button) findViewById(R.id.add__event);
        btnStartEvent = (Button) findViewById(R.id.start_event);
        btnClearEvent = (Button) findViewById(R.id.clear_event);
        btnLockEvent = (Button) findViewById(R.id.lock_event);
        btnUnlockEvent = (Button) findViewById(R.id.unlock_event);
        btnUnlockAllEvent = (Button) findViewById(R.id.unlock_all_event);

        resetParameter();
        printStatus(eventStatus.NOT_START);
        printPendingNumber();
    }

    private void resetParameter() {
        mID = 1;
        pendingNumber = 0;
        if(lockIDs != null) {
            lockIDs.clear();
        }
    }

    private void setEventQueueUsage(){

        lockIDs = new ArrayList<Integer>();

        eventQueue = new KKEventQueue();
        eventQueue.setListener(eventQueueListener);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingNumber++;
                printPendingNumber();
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
                                printStatus(eventStatus.RUNNING);
                                if (pendingNumber > 0) {
                                    pendingNumber--;
                                }
                                printPendingNumber();
                            }
                        }
                );
            }
        });

        btnStartEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventQueue.start();
                printStatus(eventStatus.RUNNING);
            }
        });

        btnLockEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id = mID++;
                lockIDs.add(id);
                pendingNumber++;
                printLockIDs();
                printPendingNumber();
                eventQueue.addCallerThreadEventWithLock(new Runnable() {
                    @Override
                    public void run() {
                        printStatus(eventStatus.RUNNING);
                        if(pendingNumber > 0) {
                            pendingNumber--;
                        }
                        printPendingNumber();
                        printStatus(eventStatus.LOCKED);
                    }
                }, id);
            }
        });

        btnUnlockEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lockIDs.isEmpty())
                {
                    eventQueue.unlockEvent(lockIDs.get(0));
                    lockIDs.remove(0);
                    printLockIDs();
//                    printStatus(eventStatus.RUNNING);
                }
            }
        });

        btnUnlockAllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.unlockAllEvents();
                lockIDs.clear();
                printLockIDs();
//                printStatus(eventStatus.RUNNING);
            }
        });

        btnClearEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventQueue.clearPendingEvents();
                resetParameter();
                printPendingNumber();
                printLockIDs();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(eventQueue != null) {
            eventQueue.clearPendingEvents();
        }
        super.onDestroy();
    }

    private void printLockIDs(){
        String s = "";
        for(Integer id : lockIDs){
            s += "Lock ID : "+id + "\n";
        }
        labelLockID.setText(s);
    }

    private void printPendingNumber(){
        if(labelPending != null) {
            labelPending.setText("Number of pending : " + pendingNumber);
        }
    }
    enum eventStatus {NOT_START, RUNNING, LOCKED, FINISHED};
    private void printStatus(eventStatus status){
        switch(status){
            case NOT_START:
                labelEventStatus.setText("Status : Not started");
                break;
            case RUNNING:
                if (eventQueue.isRunning()) {
                    labelEventStatus.setText("Status : Running");
                }
                break;
            case LOCKED:
                if (!lockIDs.isEmpty()) {
                    labelEventStatus.setText("Status : Locked,  ID = " + lockIDs.get(0));
                }
                break;
            case FINISHED:
                labelEventStatus.setText("Status : Finished");
                break;
        }

    }
}
