package vu.travelapp.networks;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class DataModelJson {
    @SerializedName("_id")
    private String id;
    @SerializedName("base64image")
    private String base64image;
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

    public String getBase64image() {
        return base64image;
    }

    public void setBase64image(String base64image) {
        this.base64image = base64image;
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
