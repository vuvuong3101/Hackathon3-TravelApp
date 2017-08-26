package vu.travelapp.networks.notifications_service;

/**
 * Created by ADMIN on 8/25/2017.
 */

public class NotificationJSON {
    private String body;
    private String title;

    public NotificationJSON() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
