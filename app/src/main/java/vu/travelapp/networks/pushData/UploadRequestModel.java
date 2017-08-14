package vu.travelapp.networks.pushData;

/**
 * Created by trongphuong1011 on 8/13/2017.
 */

public class UploadRequestModel {
    private String userid;
    private String image;
    private String username;
    private String timeupload;
    private String destination;
    private String content;

    public UploadRequestModel(String userid, String image, String username, String timeupload, String destination, String content) {
        this.userid = userid;
        this.image = image;
        this.username = username;
        this.timeupload = timeupload;
        this.destination = destination;
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeupload() {
        return timeupload;
    }

    public void setTimeupload(String timeupload) {
        this.timeupload = timeupload;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
