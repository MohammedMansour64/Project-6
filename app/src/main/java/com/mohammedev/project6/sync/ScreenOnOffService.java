package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.CountUpTimer;
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

        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(mScreenReceiver , screenFilter);
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


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH){

                    if (checkScreenOffOnOverAPI20()){
                        CountUpTimer countUpTimer = CountUpTimer.getInstance();
                        countUpTimer.startTimer(getApplicationContext());
                    }

                }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH){
                    if (checkScreenOffOnUnderAPI20()){
                        CountUpTimer countUpTimer = CountUpTimer.getInstance();
                        countUpTimer.startTimer(getApplicationContext());
                    }
                }

            }
        });


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = NotificationUtils.getSyncNotification(getApplicationContext() , null);
        startForeground(NotificationUtils.ALERT_NOTIFICATION_ID , notification);
        registerScreenStatusReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterScreenStatusReceiver();
    }

    public boolean checkScreenOffOnOverAPI20(){
        DisplayManager dm = (DisplayManager) getApplicationContext().getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                return true;
            }
        }
        return false;
    }

    public boolean checkScreenOffOnUnderAPI20(){
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        return powerManager.isScreenOn();
    }
}
