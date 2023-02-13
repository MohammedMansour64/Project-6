package com.mohammedev.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.sync.ScreenOnOffService;
import com.mohammedev.project6.sync.SyncUtils;
import com.mohammedev.project6.sync.SyncWorker;
import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.NotificationUtils;
import com.mohammedev.project6.utils.ScreenOnOffReceiver;
import com.mohammedev.project6.viewmodel.AlertViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";

    //TODO: setting the content on the views is not working, try to do it with viewmodel observe, try viewmodel on service one more time.
    //TODO: i forgot how to databind, learn that too.
    //TODO: the final goal is to have livedata and viewmodel and databinding in the project.


    TextView notificationsTextView;
    TextView weeklyAverageTextView;
    Button dailyDataBtn;

    AppExecutor appExecutor = AppExecutor.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationUtils.createWeatherStatusNotificationChannel(this);

        notificationsTextView = findViewById(R.id.notifications_txt);
        weeklyAverageTextView = findViewById(R.id.weekly_average_txt);
        dailyDataBtn = findViewById(R.id.daily_data_btn);

        dailyDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDailyDataActivity = new Intent(MainActivity.this , DailyDataActivity.class);
                startActivity(intentToDailyDataActivity);
            }
        });

        AlertViewModel alertViewModel = ViewModelProviders.of(this).get(AlertViewModel.class);

//        alertViewModel.getAlerts().observe(this, new Observer<List<Alert>>() {
//            @Override
//            public void onChanged(List<Alert> alerts) {
//                Log.d(TAG, "onChanged: " +
//                        "alerts:" +
//                        alerts.toString());
//            }
//        });

        alertViewModel.getAlertsLiveData().observe(this, new Observer<List<Alert>>() {
            @Override
            public void onChanged(List<Alert> alerts) {
                Log.d(TAG, "onChanged: " +
                        "alerts:" +
                        alerts.toString());

                if (alerts.size() > 0){
                        notificationsTextView.setText(String.valueOf(alerts.get(alerts.size() - 1).getDayAlertCounter()));
                }

            }
        });

//        appExecutor.getDiskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        });






    }


}