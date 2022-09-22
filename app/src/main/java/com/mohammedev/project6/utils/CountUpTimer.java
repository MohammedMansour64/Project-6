package com.mohammedev.project6.utils;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer {

    //TODO: for tommorow, fix the time achieved thingy, its buggy

    private static final String TAG = "CountUpTimer";

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

    public static void startTimer() {
        if (timeAchieved() && !timerOff) {
            time = 0;
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

            timer.scheduleAtFixedRate(timerTask, 1500 , 1000);
        } else if(timerOff){
            timer = new Timer();
            time = lastSavedTimeBeforeTurnOff;
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

            timer.scheduleAtFixedRate(timerTask, 1500 , 1000);

        }else {
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

            timer.scheduleAtFixedRate(timerTask, 1500 , 1000);
        }

    }

    public static void pauseTimer(){
        timer.cancel();

        timerOff = true;
        lastSavedTimeBeforeTurnOff = time;
    }

    public static boolean timeAchieved() {
        return 1 - currentTimeInMinutes >= 0;
    }

}
