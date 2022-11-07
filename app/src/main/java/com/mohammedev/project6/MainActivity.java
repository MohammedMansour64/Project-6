package com.mohammedev.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
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

    TextView notificationsTextView;
    TextView weeklyAverageTextView;
    Button dailyDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationUtils.createWeatherStatusNotificationChannel(this);

        notificationsTextView = findViewById(R.id.notifications_txt);
        weeklyAverageTextView = findViewById(R.id.weekly_average_txt);
        dailyDataBtn = findViewById(R.id.daily_data_btn);

        AlertViewModel alertViewModel = ViewModelProviders.of(this).get(AlertViewModel.class);




    }


}