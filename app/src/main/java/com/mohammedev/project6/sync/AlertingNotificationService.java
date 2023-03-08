package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.NotificationUtils;
import com.mohammedev.project6.utils.ScreenOnOffReceiver;

public class AlertingNotificationService extends IntentService {

    private static final String TAG = "AlertingNotificationService";
    public AlertingNotificationService() {
        super(AlertingNotificationService.class.getName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {

            }
        });


        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
//        Notification notification2 = NotificationUtils.getAlertingNotification(getApplicationContext());
//        startForeground(NotificationUtils.ALERT2_NOTIFICATION_ID , notification2);

        Notification notification = NotificationUtils.getSyncNotification(getApplicationContext() , null);
        startForeground(NotificationUtils.ALERT_NOTIFICATION_ID , notification);
    }
}
