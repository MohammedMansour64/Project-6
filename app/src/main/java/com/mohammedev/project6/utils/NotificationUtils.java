package com.mohammedev.project6.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mohammedev.project6.MainActivity;
import com.mohammedev.project6.R;
import com.mohammedev.project6.data.entity.Alert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * This class is for creating Notification Channels and notifications.
 */

public class NotificationUtils {
    public static final String USAGE_ALERT_CHANNEL_ID = "Usage Alert";
    public static final int ALERT_NOTIFICATION_ID = 1;
    public static final int ALERT2_NOTIFICATION_ID = 2;

    /**
     * This Method is for creating the notification channel, that then used to create notifications
     * Notification channels are like categories so we can organize notifications
     * for instance, user profile notifications are labeled under e.g. UserNotificationChannel.
     * and Sales notifications are labeled under e.g. SalesNotificationChannel.
     * @param context is for getting the system service
     */
//    public static void createWeatherStatusNotificationChannel(Context context){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            CharSequence name = "Usage Alert";
//            String description = "This channel used for displaying notifications after using the phone for more than 25 minutes";
//            NotificationChannel channel = new NotificationChannel(
//                    USAGE_ALERT_CHANNEL_ID,
//                    name,
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//
//            channel.setDescription(description);
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification getSyncNotification(Context context , Alert alert){

//        NotificationChannel channel = new NotificationChannel(USAGE_ALERT_CHANNEL_ID, "My Foreground Service", NotificationManager.IMPORTANCE_LOW);
//
//        channel.setLightColor(Color.BLUE);
//
//        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
//
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        assert manager != null;
//        manager.createNotificationChannel(channel);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, USAGE_ALERT_CHANNEL_ID);
//
//        Notification notification = notificationBuilder
//                .setOngoing(true)
//                .setSmallIcon(R.drawable.app_icon)
//                .setContentTitle("App is running on foreground")
//                .setPriority(NotificationManager.IMPORTANCE_LOW)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setChannelId("MyChannelId")
//                .build();
//
//
//







        String notificationTitle = "We Care";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context , USAGE_ALERT_CHANNEL_ID)
                .setOngoing(true)
                .setContentTitle(notificationTitle)
                .setSmallIcon(R.drawable.app_icon);

        return notificationBuilder.build();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pushAlertingNotification(Context context){
        // Notifications only work on api 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // Resources.
            String[] alertAdvices = context.getResources().getStringArray(R.array.notificationAlertsQuotes);
            Collections.shuffle(Arrays.asList(alertAdvices));
            String notificationTitle = context.getString(R.string.app_name);
            String notificationText = alertAdvices[0];

            // Builder.
            NotificationCompat.Builder Builder = new NotificationCompat.Builder(context, USAGE_ALERT_CHANNEL_ID);
            Builder.setColor(ContextCompat.getColor(context , R.color.ic_color));
            Builder.setSmallIcon(R.drawable.app_icon);
            Builder.setContentTitle(notificationTitle);
            Builder.setContentText(notificationText);
            Builder.setAutoCancel(false);

            // IDK.
            Intent intent = new Intent(context, MainActivity.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(intent);

            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0 , PendingIntent.FLAG_IMMUTABLE);
            Builder.setContentIntent(pendingIntent);

            Notification notification = Builder.build();


            NotificationChannel channel = new NotificationChannel(String.valueOf(NotificationUtils.ALERT2_NOTIFICATION_ID) , "Alerting Notification" , NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            manager.notify(ALERT_NOTIFICATION_ID , notification);

        }

    }
}
