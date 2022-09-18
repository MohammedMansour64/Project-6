package com.mohammedev.project6.sync;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mohammedev.project6.Background.AlertsRepository;
import com.mohammedev.project6.data.entity.Alert;
import com.mohammedev.project6.utils.AppExecutor;
import com.mohammedev.project6.utils.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SyncWorker extends Worker {
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = "SyncWorker";


    //TODO: What happens after 25 minutes occur.
    //TODO: insert to the database a new Alert.
    @NonNull
    @Override
    public Result doWork() {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {
//                NotificationUtils.getSyncNotificationTwo(getApplicationContext());
//                Intent intent = new Intent(getApplicationContext() , SyncIntentService.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                    getApplicationContext().startForegroundService(intent);
//                }else{
//                    getApplicationContext().startService(intent);
//                }

                for (int i = 0; i <= 10; i++){
                    Log.d(TAG, "doWork: number: " + "All Might");
                }

            }
        });



        return Result.success();
    }
}
