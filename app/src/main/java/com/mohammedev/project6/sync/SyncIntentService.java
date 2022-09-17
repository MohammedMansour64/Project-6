package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.ScreenBroadcastReceiver;

public class SyncIntentService extends IntentService {

    public SyncIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {
                ScreenBroadcastReceiver.startTimer(getApplicationContext());
            }
        });


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
