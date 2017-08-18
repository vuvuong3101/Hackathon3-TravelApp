package vu.travelapp.models;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModel {
    private String id;
    private String userid;
    private String image;
    private String name;
    private String content;
    private String destination;
    private String timeUpload;
    private int like;

    public DataModel() {
    }

    public String getTimeUpload() {
        return timeUpload;
    }

    public void setTimeUpload(String timeUpload) {
        this.timeUpload = timeUpload;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
