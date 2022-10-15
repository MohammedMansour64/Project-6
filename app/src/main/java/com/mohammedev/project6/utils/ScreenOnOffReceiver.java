package com.mohammedev.project6.utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.sync.SyncUtils;
import com.mohammedev.project6.viewmodel.AlertViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    CountUpTimer countUpTimer;

    public ScreenOnOffReceiver(Application application) {
        countUpTimer = CountUpTimer.getInstance(application);
    }



    private static final String TAG = "ScreenOnOffReceiver";

    public static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";


    @Override
    public void onReceive(Context context, Intent intent) {
        SyncUtils.scheduleSync(context);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countUpTimer.pauseTimer();
            Log.d(TAG, "onReceive: screenState: " + "false");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            countUpTimer.startTimer();
            Log.d(TAG, "onReceive: screenState: " + "true");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            countUpTimer.startTimer();
            Log.d(TAG, "onReceive: screenState: " + "true");
        }
    }
}
