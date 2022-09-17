package com.mohammedev.project6.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.mohammedev.project6.data.entity.Alert;

/**
 * This class is for creating Notification Channels and notifications.
 */

public class NotificationUtils {
    private static final String USAGE_ALERT_CHANNEL_ID = "Usage Alert";
    private static final int WEATHER_NOTIFICATION_ID = 1;

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


    public static void showUsageAlertNotification(Context context , Alert alert){
        if (alert == null) return;

        if ()
    }
}
