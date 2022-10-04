package com.mohammedev.project6.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
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
import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer {

    //TODO: for tomorrow, fix the time achieved thingy, its buggy

    private static final String TAG = "CountUpTimer";

    private static final int ONE_SECOND_MILLIE_SECONDS = 100;
    private static final int TWENTY_FIVE_MINUTES_IN_SECONDS = 1500;

    private static int currentTimeInMinutes;
    private static int time;
    public static boolean timerOff;
    public static int lastSavedTimeBeforeTurnOff = 0;

    private static Timer timer = new Timer();

    private static CountUpTimer sInstance;

    private CountUpTimer() {

    }

    public static CountUpTimer getInstance(){
        if (sInstance == null){
            sInstance = new CountUpTimer();
        }
        return sInstance;
    }

    /**
     * TODO: CHANGE THIS METHOD PLACE, ITS NOT A GOOD PRACTICE FOR IT TO BE HERE.
     * This method is used for inserting or updating an alert. First, it searches the db for all the Objects then,
     * checks if there is an object that has today's date, On which he updates the alert with a new notification.
     * else, he will create a new alert because he didn't register an alert for today. and the new object has 1 alert and today's date
     * @param context so we can create a new object of the view model.
     */
    public static void setForTodayDateAlert(Context context) {
        System.out.println("test");
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String todayDate = df.format(c);

        AlertViewModel alertViewModel = ViewModelProviders.of((FragmentActivity) context).get(AlertViewModel.class);

        LiveData<List<Alert>> alerts = alertViewModel.getAllAlerts();

        for (int i = 0; i < alerts.getValue().size(); i++) {

            if (alerts.getValue().get(i).getDayDate().contains(todayDate)) {
                alerts.getValue().get(i).setDayAlertCounter(alerts.getValue().get(i).getDayAlertCounter() + 1);
                alertViewModel.updateAlert(alerts.getValue().get(i));

            } else {

                Alert alert = new Alert(1, todayDate);
                alertViewModel.insertAlert(alert);
                Log.d(TAG, "setForTodayDateAlert: an alert has been added");

            }
        }

    }

    public static void startTimer(Context context) {
        if (timeAchieved(context) && !timerOff) {

            time = 0;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                    if (timeAchieved(context)){
                        Log.d(TAG, "run: Time has been achieved");
                    }
                    Log.d(TAG, "startTimer: currentTimeOne: " + time);
                }
            };

            timer.scheduleAtFixedRate(timerTask, TWENTY_FIVE_MINUTES_IN_SECONDS , ONE_SECOND_MILLIE_SECONDS);
        } else if(timerOff){
            timer = new Timer();
            time = lastSavedTimeBeforeTurnOff;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                    if (timeAchieved(context)){
                        Log.d(TAG, "run: Time has been achieved");
                    }
                    Log.d(TAG, "startTimer: currentTimeTwo: " + time);

                }
            };

            timer.scheduleAtFixedRate(timerTask, TWENTY_FIVE_MINUTES_IN_SECONDS , ONE_SECOND_MILLIE_SECONDS);

        }else {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                    if (timeAchieved(context)){
                        Log.d(TAG, "run: Time has been achieved");
                    }
                    Log.d(TAG, "startTimer: currentTimeThree: " + time);

                }
            };

            timer.scheduleAtFixedRate(timerTask, TWENTY_FIVE_MINUTES_IN_SECONDS , ONE_SECOND_MILLIE_SECONDS);
        }

    }

    public static void pauseTimer(){
        timer.cancel();

        timerOff = true;
        lastSavedTimeBeforeTurnOff = time;
    }

    public static boolean timeAchieved(Context context) {
        if (1 - currentTimeInMinutes <= 0){
            System.out.println("tewst");
            setForTodayDateAlert(context);
        }
        return 1 - currentTimeInMinutes <= 0;
    }

}
