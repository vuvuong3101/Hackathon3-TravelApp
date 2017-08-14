package vu.travelapp.networks.pullData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModelJson {
    @SerializedName("_id")
    private String id;
    @SerializedName("image")
    private String image;
    @SerializedName("username")
    private String username;
    @SerializedName("content")
    private String content;

    public DataModelJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
