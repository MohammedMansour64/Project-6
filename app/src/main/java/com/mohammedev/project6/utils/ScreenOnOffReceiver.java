package com.mohammedev.project6.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
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

    private static int currentTimeInMinutes;
    private static int time;

    private static final String TAG = "ScreenBroadcastReceiver";


    /**
     * Method used to get state of the screen. Whether is it off or on
     *
     * @return the boolean state of the screen
     */
    public static boolean screenState() {
        Intent intent = new Intent(Intent.ACTION_SCREEN_ON);
        return Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_ON);
    }


    /**
     * Method for start calculating time.
     */
    public static void startTimer(Context context) {
        Timer timer = new Timer();
            if (timeAchieved(context)) {
                time = 0;
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        time++;
                        currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                        Log.d(TAG, "run: timerTime: " + time);
                    }
                };
                timer.schedule(timerTask, 1500000);
            } else {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        time++;
                        currentTimeInMinutes = ((Math.round(time) % 86400) % 3600) / 60;
                        Log.d(TAG, "run: timerTime: " + time);
                    }
                };
                timer.schedule(timerTask, 1500000);

            }

    }

    /**
     * This method is an indicator for which 25 minutes of mobile usage has been reached.
     * once it is reached, it then updates an alert or inserts one using the setForTodayDateAlert method V.
     * @param context the context is used here for initializing the viewModel.
     * @return this method returns a boolean value.
     */
    public static boolean timeAchieved(Context context) {

        if (25 - currentTimeInMinutes == 0) {

            AlertViewModel alertViewModel = ViewModelProviders.of((FragmentActivity) context).get(AlertViewModel.class);

            setForTodayDateAlert(alertViewModel.getAllAlerts(), alertViewModel);

            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used for inserting or updating an alert. First, it searches the db for all the Objects then,
     * checks if there is an object that has today's date, On which he updates the alert with a new notification.
     * else, he will create a new alert because he didn't register an alert for today. and the new object has 1 alert and today's date
     * @param alerts the list where he searches for similar dates
     * @param alertViewModel used for initializing the view model and then updates or inserts an alert.
     */
    public static void setForTodayDateAlert(LiveData<List<Alert>> alerts, AlertViewModel alertViewModel) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String todayDate = df.format(c);

        for (int i = 0; i < alerts.getValue().size(); i++) {

            if (alerts.getValue().get(i).getDayDate().contains(todayDate)) {

                alerts.getValue().get(i).setDayAlertCounter(alerts.getValue().get(i).getDayAlertCounter() + 1);
                alertViewModel.updateAlert(alerts.getValue().get(i));

            } else {

                Alert alert = new Alert(1, todayDate);
                alertViewModel.insertAlert(alert);

            }
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "onReceive: screenState: " + "false");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            startTimer(context);
            Log.d(TAG, "onReceive: screenState: " + "true");
        }
    }
}
