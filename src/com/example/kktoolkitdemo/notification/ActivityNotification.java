/* Copyright (C) 2013 KKBOX Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * ​http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * ActivityNotification.java: This activity demonstrates the usage of KKDialogs (com.kkbox.toolkit.notification).
 */
package com.example.kktoolkitdemo.notification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.kktoolkitdemo.R;
import com.kkbox.toolkit.dialog.KKDialogFactory;
import com.kkbox.toolkit.dialog.KKDialogPostExecutionListener;
import com.kkbox.toolkit.ui.KKServiceActivity;

public class ActivityNotification extends KKServiceActivity {

    private final OnClickListener btnNotifyOne = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mHandler.sendEmptyMessageDelayed(MSG_ALERT_DLG, 3000);
            Intent intent = new Intent(ActivityNotification.this, EmptyActivity.class);
            startActivity(intent);

        }
    };

    private final OnClickListener btnNotifyTwo = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mHandler.sendEmptyMessageDelayed(MSG_NOTIFY_TWO_DLG, 3000);
            Intent intent = new Intent(ActivityNotification.this, EmptyActivity.class);
            startActivity(intent);
        }
    };

    private final OnClickListener btnNotifyThree = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mHandler.sendEmptyMessageDelayed(MSG_CHOICE_DLG, 3000);
            Intent intent = new Intent(ActivityNotification.this, EmptyActivity.class);
            startActivity(intent);
        }
    };

    private final OnClickListener btnNotifyFour = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mHandler.sendEmptyMessageDelayed(MSG_PROCESSING_DLG, 3000);
            Intent intent = new Intent(ActivityNotification.this, EmptyActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_example);
        getKKActionBar().setTitle("Notification Example");

        Button btnNotify1 = (Button) findViewById(R.id.btnNotify1);
        btnNotify1.setOnClickListener(btnNotifyOne);
        Button btnNotify2 = (Button) findViewById(R.id.btnNotify2);
        btnNotify2.setOnClickListener(btnNotifyTwo);
        Button btnNotify3 = (Button) findViewById(R.id.btnNotify3);
        btnNotify3.setOnClickListener(btnNotifyThree);
        Button btnNotify4 = (Button) findViewById(R.id.btnNotify4);
        btnNotify4.setOnClickListener(btnNotifyFour);

    }

    final static int MSG_ALERT_DLG = 1;
    final static int MSG_NOTIFY_TWO_DLG = 2;
    final static int MSG_CHOICE_DLG = 3;
    final static int MSG_PROCESSING_DLG = 4;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_ALERT_DLG:
                    ExampleService.getDialogNotificationManager().addDialog(
                            KKDialogFactory.createAlertDialog(0, "KKBOX Reminder", "This is a test message", "Confirm",
                                    new KKDialogPostExecutionListener() {

                                    }));
                    break;
                case MSG_NOTIFY_TWO_DLG:
                    ExampleService.getDialogNotificationManager().addDialog(
                            KKDialogFactory.createYesOrNoDialog(2, "KKBOX Reminder", "This is a test message", "Yes", "No", new KKDialogPostExecutionListener() {

                            }));
                    break;
                case MSG_CHOICE_DLG:
                    ExampleService.getDialogNotificationManager().addDialog(
                            KKDialogFactory
                                    .createThreeChoiceDialog(1, "KKBOX Reminder", "This is a test message", "Retry", "Ignore", "Abort",
                                            new KKDialogPostExecutionListener() {

                                            }));
                    break;
                case MSG_PROCESSING_DLG:
                    ExampleService.getDialogNotificationManager().addDialog(KKDialogFactory.createProgressingDialog(3, "Processing", new KKDialogPostExecutionListener() {
                        @Override
                        public void onCancel() {
                            super.onCancel();
                            ExampleService.getDialogNotificationManager().cancelDialog(3);
                        }
                    }));
                    break;
            }
        }
    };
}
