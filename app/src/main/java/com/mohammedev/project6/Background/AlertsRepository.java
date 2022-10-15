package com.mohammedev.project6.Background;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mohammedev.project6.data.database.AppDatabase;
import com.mohammedev.project6.data.database.dao.AlertsDao;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.sync.SyncUtils;

import java.util.List;

public class AlertsRepository {
    private final AlertsDao mAlertsDao;

    private final AppDatabase db;


    public AlertsRepository(Application application) {
        db = AppDatabase.getInstance(application);
        mAlertsDao = db.alertsDao();
        SyncUtils.scheduleSync(application);
    }


    public List<Alert> getAlertTwo(){
        final List<Alert> alertLiveData = db.alertsDao().getAlertsTwo();
        return alertLiveData;
    }

    public LiveData<Alert> getAlertLiveData(){
        final LiveData<Alert> alertLiveData = db.alertsDao().getAlert();
        return alertLiveData;
    }

    public LiveData<List<Alert>> getAlerts(){
        LiveData<List<Alert>> allAlerts = mAlertsDao.getAlerts();
        return allAlerts;
    }

    public void insertAlert(Alert alert){
        new InsertAlertAsyncTask(mAlertsDao).execute(alert);
    }

    public void updateAlert(Alert alert){
        new UpdateAlertAsyncTask(mAlertsDao).execute(alert);
    }

    private static class InsertAlertAsyncTask extends AsyncTask<Alert , Void , Void> {
        private AlertsDao mAlertsDao;

        public InsertAlertAsyncTask(AlertsDao alertsDao) {
            mAlertsDao = alertsDao;
        }

        @Override
        protected Void doInBackground(Alert... alerts) {
            mAlertsDao.insertAlert(alerts[0]);

            return null;
        }
    }

    private static class UpdateAlertAsyncTask extends AsyncTask<Alert, Void , Void>{

        private AlertsDao mAlertsDao;

        public UpdateAlertAsyncTask(AlertsDao mAlertsDao) {
            this.mAlertsDao = mAlertsDao;
        }

        @Override
        protected Void doInBackground(Alert... alerts) {

            mAlertsDao.updateAlert(alerts[0]);

            return null;
        }
    }
}
