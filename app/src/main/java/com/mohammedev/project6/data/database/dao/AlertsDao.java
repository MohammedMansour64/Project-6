package com.mohammedev.project6.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mohammedev.project6.data.entity.Alert;

import java.util.List;

@Dao
public interface AlertsDao {

    @Query("SELECT * FROM alerts")
    LiveData<List<Alert>> getAlerts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlert(Alert alert);


}
