package com.mohammedev.project6.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.data.entity.Alert;

import java.util.List;

public class AlertViewModel extends AndroidViewModel {

    private AlertsRepository repository;
    public AlertViewModel(@NonNull Application application) {
        super(application);
        repository = AlertsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Alert>> getAllAlerts(){
        return repository.getAlerts();
    }

    public void insertAlert(Alert alert){
        repository.insertAlert(alert);
    }
}
