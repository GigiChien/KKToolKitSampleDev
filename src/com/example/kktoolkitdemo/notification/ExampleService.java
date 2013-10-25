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
 * ExampleService.java: This is an example of using KKService.
 */
package com.example.kktoolkitdemo.notification;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.kkbox.toolkit.KKService;

public class ExampleService extends KKService {
    Handler mHandler;
    @Override
    public IBinder onBind(Intent i) {
        return super.onBind(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("GGG", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
	public void initServiceComponent() {
	}

	@Override
	public void finalize() {
        Log.v("GGG", "finalize");
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("GGG", "onDestroy");
    }
}
