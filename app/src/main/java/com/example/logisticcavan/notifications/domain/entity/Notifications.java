package com.example.logisticcavan.notifications.domain.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Notifications {


    public Notifications(){

    }
private List<Notification> notifications;

    public Notifications(List<Notification> notifications) {

        this.notifications = notifications;
    }


    public List<Notification> getNotifications() {
        return notifications;
    }
    public void addNotification(Notification notification){
        notifications.add(notification);
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
