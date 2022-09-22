package com.mohammedev.project6.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.mohammedev.project6.data.entity.Alert;
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

    private static final String TAG = "ScreenOnOffReceiver";

    /**
     * TODO: CHANGE THIS METHOD PLACE, ITS NOT A GOOD PRACTICE FOR IT TO BE HERE.
     * This method is used for inserting or updating an alert. First, it searches the db for all the Objects then,
     * checks if there is an object that has today's date, On which he updates the alert with a new notification.
     * else, he will create a new alert because he didn't register an alert for today. and the new object has 1 alert and today's date
     * @param alerts the list where he searches for similar dates
     */
//    public static void setForTodayDateAlert(LiveData<List<Alert>> alerts, Context context) {
//        Date c = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + c);
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String todayDate = df.format(c);
//
//        AlertViewModel alertViewModel = ViewModelProviders.of((FragmentActivity) context).get(AlertViewModel.class);
//
//        for (int i = 0; i < alerts.getValue().size(); i++) {
//
//            if (alerts.getValue().get(i).getDayDate().contains(todayDate)) {
//
//
//                setForTodayDateAlert(alertViewModel.getAllAlerts(), context);
//
//                alerts.getValue().get(i).setDayAlertCounter(alerts.getValue().get(i).getDayAlertCounter() + 1);
//                alertViewModel.updateAlert(alerts.getValue().get(i));
//
//            } else {
//
//                Alert alert = new Alert(1, todayDate);
//                alertViewModel.insertAlert(alert);
//
//            }
//        }
//
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            CountUpTimer.pauseTimer();
            Log.d(TAG, "onReceive: screenState: " + "false");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            CountUpTimer.startTimer();
            Log.d(TAG, "onReceive: screenState: " + "true");
        }
    }
}
