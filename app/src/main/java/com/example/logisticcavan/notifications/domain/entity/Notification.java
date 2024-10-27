package com.example.logisticcavan.notifications.domain.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notification {

    private String message;
    private String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());


    public Notification() {

    }

    public Notification(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
