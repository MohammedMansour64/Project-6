package com.mohammedev.project6.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.utils.AppExecutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SyncWorker extends Worker {
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //TODO: What happens after 25 minutes occur.
    //TODO: insert to the database a new Alert.
    @NonNull
    @Override
    public Result doWork() {
        AlertsRepository repository = AlertsRepository.getInstance(getApplicationContext());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        Alert alert = new Alert(0 , strDate);

        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        return Result.success();
    }
}
