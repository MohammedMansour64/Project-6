package com.mohammedev.project6.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alerts")
public class Alert {

    @PrimaryKey
    private int id = 0;
    private int dayAlertCounter;

    private String dayDate;

    public Alert(int dayAlertCounter, String dayDate) {
        this.dayAlertCounter = dayAlertCounter;
        this.dayDate = dayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayAlertCounter() {
        return dayAlertCounter;
    }

    public void setDayAlertCounter(int dayAlertCounter) {
        this.dayAlertCounter = dayAlertCounter;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }
}
