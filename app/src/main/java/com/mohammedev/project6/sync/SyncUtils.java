package com.mohammedev.project6.sync;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.mohammedev.project6.utils.CountUpTimer;

import java.util.concurrent.TimeUnit;

public class SyncUtils {

    private static final String TAG = "SyncUtils";

    public static void startSync(Context context){

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .addTag("SyncNotificationWorker")
                .setConstraints(constraints)
                .build();

//        Intent intent = new Intent(context , SyncWorker.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            context.startForegroundService(intent);
//        }else{
//            context.startService(intent);
//        }

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(request);
    }


    public static void scheduleSync(Context context){
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(AlertingNotificationWorker.class)
                .addTag("alertingNotificationWorker")
                .setConstraints(constraints)
                .build();

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(oneTimeWorkRequest);

    }
}
