package vu.travelapp.networks.notifications_service;

/**
 * Created by ADMIN on 8/25/2017.
 */

public class NotificationRequestJSON {
    private String to;
    private NotificationJSON notification;

    public NotificationRequestJSON() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String topic) {
        this.to = String.format("/topics/%s",topic);
    }

    public NotificationJSON getNotification() {
        return notification;
    }

    public void setNotification(NotificationJSON notification) {
        this.notification = notification;
    }
}
