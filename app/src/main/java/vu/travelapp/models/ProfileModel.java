package vu.travelapp.models;

import java.net.URL;

/**
 * Created by ADMIN on 8/10/2017.
 */

public class ProfileModel {
    private String image;
    private String name;
    private String id;
    private URL url;
    private String location;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ProfileModel() {
    }

    public String getImageURL() {
        return image;
    }

    public void setImageURL(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
