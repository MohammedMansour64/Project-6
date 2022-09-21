package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.NotificationUtils;
import com.mohammedev.project6.utils.ScreenOnOffReceiver;

public class ScreenOnOffService extends IntentService {

    private static final String TAG = "SyncIntentService";

    private ScreenOnOffReceiver mScreenReceiver;

    public ScreenOnOffService() {
        super(ScreenOnOffService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    private void registerScreenStatusReceiver(){
        mScreenReceiver = new ScreenOnOffReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenReceiver , filter);
    }

    private void unregisterScreenStatusReceiver() {
        try {
            if (mScreenReceiver != null) {
                unregisterReceiver(mScreenReceiver);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "unregisterScreenStatusReceiver: ", e.getCause());
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                ScreenOnOffReceiver.startTimer(getApplicationContext());
            }
        });


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenStatusReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterScreenStatusReceiver();
    }
}
