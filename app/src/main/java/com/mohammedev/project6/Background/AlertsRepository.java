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

    private static AlertsRepository sInstance;

    private AlertsRepository(Context context) {
        db = AppDatabase.getInstance(context);
        mAlertsDao = db.alertsDao();
        SyncUtils.scheduleSync(context);
    }

    public static AlertsRepository getInstance(Context context){
        if (sInstance == null){
            sInstance = new AlertsRepository(context.getApplicationContext());
        }
        return sInstance;
    }

    public LiveData<List<Alert>> getAlerts(){
        LiveData<List<Alert>> allAlerts = db.alertsDao().getAlerts();
        return allAlerts;
    }

    public void insertAlert(Alert alert){
        new InsertAsyncTaskAlert(mAlertsDao).execute(alert);
    }

    private static class InsertAsyncTaskAlert extends AsyncTask<Alert , Void , Void> {
        private AlertsDao mAlertsDao;

        public InsertAsyncTaskAlert(AlertsDao alertsDao) {
            mAlertsDao = alertsDao;
        }

        @Override
        protected Void doInBackground(Alert... alerts) {
            mAlertsDao.insertAlert(alerts[0]);

            return null;
        }
    }
}
