package com.mohammedev.project6.utils;

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

    private Context mContext;

    public ScreenOnOffReceiver(Context context) {
        this.mContext = context;
    }



    private static final String TAG = "ScreenOnOffReceiver";

    public static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    private static final CountUpTimer countUpTimer = CountUpTimer.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        SyncUtils.scheduleSync(context);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countUpTimer.pauseTimer();
            Log.d(TAG, "onReceive: screenState: " + "false");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            countUpTimer.startTimer(mContext);
            Log.d(TAG, "onReceive: screenState: " + "true");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            countUpTimer.startTimer(mContext);
            Log.d(TAG, "onReceive: screenState: " + "true");
        }
    }
}
