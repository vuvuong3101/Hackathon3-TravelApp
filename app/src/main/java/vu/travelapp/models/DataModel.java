package vu.travelapp.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import vu.travelapp.networks.pullData.CommentJSONModel;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModel extends RealmObject {
    private String id;
    private String userid;
    private String image;
    private String name;
    private String content;
    private String destination;
    private String timeUpload;
    private int like;
    private RealmList<CommentJSONModel> comment;
    private boolean isLike;
    public DataModel() {
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public RealmList<CommentJSONModel> getComment() {
        return comment;
    }

    public void setComment(RealmList<CommentJSONModel> comment) {
        this.comment = comment;
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
