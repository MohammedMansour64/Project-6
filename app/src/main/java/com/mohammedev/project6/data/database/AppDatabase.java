package com.mohammedev.project6.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mohammedev.project6.data.database.dao.AlertsDao;
import com.mohammedev.project6.data.entity.Alert;

@Database(entities = {Alert.class} , version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "alerts_db";

    private static AppDatabase sInstance;

    public abstract AlertsDao alertsDao();

    public static AppDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            AppDatabase.DATABASE_NAME
                    ).build();
                }
            }
        }
        return sInstance;
    }
}
