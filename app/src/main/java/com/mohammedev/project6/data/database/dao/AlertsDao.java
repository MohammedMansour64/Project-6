package com.mohammedev.project6.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mohammedev.project6.data.entity.Alert;

import java.util.List;

@Dao
public interface AlertsDao {

    @Query("SELECT * FROM alerts LIMIT 1")
    List<Alert> getAlerts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlert(Alert alert);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAlert(Alert alert);

}
