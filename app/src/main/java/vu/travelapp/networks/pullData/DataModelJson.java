package vu.travelapp.networks.pullData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModelJson {
    @SerializedName("userid")
    private String userid;
    @SerializedName("image")
    private String image;
    @SerializedName("username")
    private String username;
    @SerializedName("content")
    private String content;
    @SerializedName("like")
    private int like;

    public DataModelJson() {
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
