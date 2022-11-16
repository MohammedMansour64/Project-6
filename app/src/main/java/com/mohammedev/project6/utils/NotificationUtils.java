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
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mohammedev.project6.MainActivity;
import com.mohammedev.project6.R;
import com.mohammedev.project6.data.entity.Alert;

/**
 * This class is for creating Notification Channels and notifications.
 */

public class NotificationUtils {
    private static final String USAGE_ALERT_CHANNEL_ID = "Usage Alert";
    public static final int ALERT_NOTIFICATION_ID = 1;

    /**
     * This Method is for creating the notification channel, that then used to create notifications
     * Notification channels are like categories so we can organize notifications
     * for instance, user profile notifications are labeled under e.g. UserNotificationChannel.
     * and Sales notifications are labeled under e.g. SalesNotificationChannel.
     * @param context is for getting the system service
     */
    public static void createWeatherStatusNotificationChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Usage Alert";
            String description = "This channel used for displaying notifications after using the phone for more than 25 minutes";
            NotificationChannel channel = new NotificationChannel(
                    USAGE_ALERT_CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public static Notification getSyncNotification(Context context , Alert alert){
        String notificationTitle = "We Care";


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context , USAGE_ALERT_CHANNEL_ID)
                .setOngoing(true)
                .setContentTitle(notificationTitle)
                .setSmallIcon(R.drawable.app_icon);

        return notificationBuilder.build();

    }

    public static void getSyncNotificationTwo(Context context){
        String notificationTitle = context.getString(R.string.app_name);
        String notificationText = "Test";
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, USAGE_ALERT_CHANNEL_ID);
        notificationBuilder.setColor(ContextCompat.getColor(context , R.color.colorAccent));
        notificationBuilder.setSmallIcon(R.drawable.circle);
        notificationBuilder.setLargeIcon(largeIcon);
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationText);
        notificationBuilder.setAutoCancel(true);
        Intent intent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0 , PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ALERT_NOTIFICATION_ID , notification);
    }
}
