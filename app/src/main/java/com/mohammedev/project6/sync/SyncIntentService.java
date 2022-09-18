package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.NotificationUtils;
import com.mohammedev.project6.utils.ScreenBroadcastReceiver;

public class SyncIntentService extends IntentService {

    public SyncIntentService() {
        super(SyncIntentService.class.getName());
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
        Notification notification = NotificationUtils.getSyncNotification(this , null);
        startForeground(NotificationUtils.ALERT_NOTIFICATION_ID , notification);
    }
}
