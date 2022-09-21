package com.mohammedev.project6.sync;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SyncUtils {

    private static final String TAG = "SyncUtils";

    public static void startSync(Context context){
        Intent intent = new Intent(context , ScreenOnOffService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }
    }

    public static void scheduleSync(Context context){
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SyncWorker.class , 5 , TimeUnit.SECONDS)
                .setConstraints(constraints)
                .addTag("Periodic")
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .addTag("worker")
                .build();

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(request);

    }
}
