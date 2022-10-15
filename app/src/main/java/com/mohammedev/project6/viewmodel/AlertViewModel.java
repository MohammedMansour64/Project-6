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
import com.mohammedev.project6.utils.ScreenOnOffReceiver;

import java.util.List;

public class AlertViewModel extends AndroidViewModel {

    private AlertsRepository repository;

    private ScreenOnOffReceiver mScreenReceiver;


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
        repository = new AlertsRepository(application);

        mContext = application.getApplicationContext();
        mScreenReceiver = new ScreenOnOffReceiver(application);
        registerScreenStatusReceiver();
    }

    private void registerScreenStatusReceiver(){

        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        mContext.registerReceiver(mScreenReceiver , screenFilter);
    }
}
