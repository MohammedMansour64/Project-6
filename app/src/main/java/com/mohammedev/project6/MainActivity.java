package com.mohammedev.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mohammedev.project6.R;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.sync.SyncUtils;
import com.mohammedev.project6.utils.NotificationUtils;
import com.mohammedev.project6.utils.ScreenBroadcastReceiver;
import com.mohammedev.project6.viewmodel.AlertViewModel;

import java.util.List;

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

        ScreenBroadcastReceiver.startTimer(getApplicationContext());

//        SyncUtils.startSync(this);
        SyncUtils.scheduleSync(this);
    }


}