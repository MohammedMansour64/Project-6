package com.mohammedev.project6.sync;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SyncUtils {

    public static void startSync(Context context){
        Intent intent = new Intent(context , SyncIntentService.class);
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

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SyncWorker.class , 25 , TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("SyncWorker" , ExistingPeriodicWorkPolicy.KEEP , periodicWorkRequest);

    }
}
