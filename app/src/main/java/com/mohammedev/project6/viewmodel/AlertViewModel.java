package com.mohammedev.project6.viewmodel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.sync.SyncUtils;

import java.util.List;

public class AlertViewModel extends AndroidViewModel {

    private AlertsRepository repository;


    private BroadcastReceiver mScreenUsabilityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SyncUtils.startSync(mContext);
        }
    };


    public LiveData<List<Alert>> getAllAlerts(){
        return repository.getAlerts();
    }

    public LiveData<Alert> getAlertLiveData(){
        return repository.getAlertLiveData();
    }

    private Context mContext;

    public void insertAlert(Alert alert){
        repository.insertAlert(alert);
    }

    public void updateAlert(Alert alert){
        repository.updateAlert(alert);
    }




    public AlertViewModel(@NonNull Application application) {
        super(application);
        repository = AlertsRepository.getInstance(application.getApplicationContext());

        mContext = application.getApplicationContext();

        IntentFilter screenFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        mContext.registerReceiver(mScreenUsabilityReceiver , screenFilter);
    }
}
