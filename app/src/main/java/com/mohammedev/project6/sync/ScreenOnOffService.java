package com.mohammedev.project6.sync;

import android.app.IntentService;
import android.app.Notification;
import android.content.BroadcastReceiver;
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
    public ScreenOnOffService() {
        super(ScreenOnOffService.class.getName());
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
        Notification notification = NotificationUtils.getSyncNotification(getApplicationContext() , null);
        startForeground(NotificationUtils.ALERT_NOTIFICATION_ID , notification);

        CountUpTimer countUpTimer = CountUpTimer.getInstance(getApplication());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH){

            if (checkScreenOffOnOverAPI20()){

                countUpTimer.startTimer();
            }

        }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH){
            if (checkScreenOffOnUnderAPI20()){
                countUpTimer.startTimer();
            }
        }
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
