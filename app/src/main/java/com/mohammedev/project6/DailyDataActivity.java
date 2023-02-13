package com.mohammedev.project6;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.viewmodel.AlertViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DailyDataActivity extends AppCompatActivity {
    private static final String TAG = "DAILY_DATA_ACTIVITY";

    Spinner daySpinner;
    TextView notificationsText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);

        daySpinner = findViewById(R.id.spinner);
        notificationsText = findViewById(R.id.textView4);

        AlertViewModel alertViewModel = ViewModelProviders.of(this).get(AlertViewModel.class);

        alertViewModel.getAlertsLiveData().observe(this, new Observer<List<Alert>>() {
            @Override
            public void onChanged(List<Alert> alerts) {
                List<String> dayDates = alerts.stream().map(Alert::getDayDate).collect(Collectors.toList());
                List<Integer> dayAlertCounters = alerts.stream().map(Alert::getDayAlertCounter).collect(Collectors.toList());
                spinnerArrayAdapter(dayDates , dayAlertCounters);
            }
        });

    }

    public void spinnerArrayAdapter(List<String> dates , List<Integer> counters){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , dates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);


        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                notificationsText.setText(String.valueOf(counters.get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
