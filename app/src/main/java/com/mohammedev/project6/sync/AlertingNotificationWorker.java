package com.mohammedev.project6.sync;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mohammedev.project6.utils.AppExecutor;

public class AlertingNotificationWorker extends Worker {

    private static final String TAG = "AlertingNotificationWorker";

    public AlertingNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext() , AlertingNotificationService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    getApplicationContext().startForegroundService(intent);
                }else{
                    getApplicationContext().startService(intent);
                }

            }
        });


        return Result.success();
    }
}
