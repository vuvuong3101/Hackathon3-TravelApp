package vu.travelapp.networks.pullData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModelJson {
    @SerializedName("_id")
    private String _id;
    @SerializedName("userid")
    private String userid;
    @SerializedName("image")
    private String image;
    @SerializedName("destination")
    private String destination;
    @SerializedName("username")
    private String username;
    @SerializedName("content")
    private String content;
    @SerializedName("like")
    private int like;
    @SerializedName("timeupload")
    private String timeupload;
    @SerializedName("comment")
    private List<CommentJSONModel> comment;

    public String getTimeupload() {
        return timeupload;
    }

    public List<CommentJSONModel> getComment() {
        return comment;
    }

    public void setComment(List<CommentJSONModel> comment) {
        this.comment = comment;
    }

    public void setTimeupload(String timeupload) {
        this.timeupload = timeupload;
    }

    public DataModelJson() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
