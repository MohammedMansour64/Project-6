package com.mohammedev.project6.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.MainActivity;
import com.mohammedev.project6.data.entity.Alert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;
@RequiresApi(api = Build.VERSION_CODES.O)
public class CountUpTimer {

    private static final String TAG = "CountUpTimer";

    private static final int ONE_SECOND_MILLIE_SECONDS = 1000;
    private static final int TWENTY_FIVE_MINUTES_IN_SECONDS = 1500;

    private static int currentTimeInMinutes;
    private static int time;
    public  boolean timerOff;
    public static int lastSavedTimeBeforeTurnOff = 0;
    public static int matchedDateIndex;

    public static int timeFinished;

    public static AlertsRepository alertsRepository;

    private static CountUpTimer sInstance;

    private static Timer timer = new Timer();

    public static AppExecutor mAppExecutor;

    public static Application app;

    private CountUpTimer(Application application) {
        alertsRepository = new AlertsRepository(application);
        mAppExecutor = AppExecutor.getInstance();
    }

    public static CountUpTimer getInstance(Application application){
        app = application;
        if (sInstance == null){
            sInstance = new CountUpTimer(application);
        }
        return sInstance;
    }


    /**
     * TODO: CHANGE THIS METHOD PLACE, ITS NOT A GOOD PRACTICE FOR IT TO BE HERE.
     * This method is used for inserting or updating an alert. First, it searches the db for all the Objects then,
     * checks if there is an object that has today's date, On which he updates the alert with a new notification.
     * else, he will create a new alert because he didn't register an alert for today. and the new object has 1 alert and today's date
     */
    public void setForTodayDateAlert() {
        timeFinished = 1;

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String todayDate = df.format(c);

        System.out.println("Current time => " + todayDate);

        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                // get instance of today's date
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String todayDate = df.format(c);

                System.out.println("Current time => " + todayDate);

                List<Alert> alerts = alertsRepository.getAlerts();

                searchForMatchingDates(alerts , todayDate);
//                boolean isItContainingTodayDate = false;

                if (matchedDateIndex != -1){
                    Alert alert = alerts.get(matchedDateIndex);
                    alerts.get(matchedDateIndex).setDayAlertCounter(alert.getDayAlertCounter() + 1);
                    alertsRepository.updateAlert(alert);
                    Log.d(TAG, "CountUpTimer: setForTodayDateAlert: Updating data...");
                    Log.d(TAG, "CountUpTimer: setForTodayDateAlert: New Data:" + alert);
                    restartTimer();
                }else if (matchedDateIndex == -1){
                    Alert alert = new Alert(1, todayDate);
                    alertsRepository.insertAlert(alert);
                    restartTimer();
                    Log.d(TAG, "setForTodayDateAlertOne: Adding a new data...");
                    Log.d(TAG, "CountUpTimer: setForTodayDateAlert: New Data: " + alert);
                } else{
                    Alert alert = new Alert(1, todayDate);
                    alertsRepository.insertAlert(alert);
                    restartTimer();
                    Log.d(TAG, "setForTodayDateAlertTwo: Adding a fresh data...");
                    if (alerts != null) {
                        Log.d(TAG, "setForTodayDateAlertTwo: new Alerts:" + alerts.size());
                    }
                    Log.d(TAG, "CountUpTimer: setForTodayDateAlert: New Data: : " + alert);
                }

            }
        });


    }

    public void startTimer() {
        timerOff = false;
        if (!timeAchieved()) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                    if (timeAchieved()){
                        Log.d(TAG, "run: Time has been achieved");
                    }
                    Log.d(TAG, "startTimer: currentTime: " + time);
                }
            };

            timer.scheduleAtFixedRate(timerTask, TWENTY_FIVE_MINUTES_IN_SECONDS , ONE_SECOND_MILLIE_SECONDS);
        }

    }

    public void restartTimer(){
        pauseTimer();
        startTimer();
    }

    public void pauseTimer(){
        timer.cancel();

        timerOff = true;
        timer = new Timer();
        lastSavedTimeBeforeTurnOff = time;
    }

    public boolean timeAchieved() {
        if (25 - currentTimeInMinutes <= 0){
            time = 0;
            lastSavedTimeBeforeTurnOff = 0;
            currentTimeInMinutes = 0;
            setForTodayDateAlert();
            pushNotification();
        }
        return 25 - currentTimeInMinutes <= 0;
    }

    private void pushNotification() {
        NotificationUtils notificationUtils = new NotificationUtils();
        notificationUtils.pushAlertingNotification(app);
    }

    public static void searchForMatchingDates(List<Alert> alertList , String date){
        int index = IntStream.range(0, alertList.size())
                .filter(i -> alertList.get(i).getDayDate().contains(date))
                .findFirst()
                .orElse(-1);
        matchedDateIndex = index;


//        if (alertList != null && !alertList.isEmpty()) {
//            for (int i = 0; i < alertList.size(); i++) {
//                Log.d(TAG, "searchForMatchingDates: " + "list size" + alertList.size() + " " + alertList.get(i).getDayDate() + "today's date: " + date);
//                if (alertList.get(i).getDayDate().contains(date)){
//                    matchedDateIndex = i;
//                    return 1;
//                }else{
//                    return 0;
//                }
//            }
//        }
//        return -1;
    }

}
