package vu.travelapp.networks.pullData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trongphuong1011 on 8/19/2017.
 */

public class CommentJSONModel {
    private String name;
    private String sentence;
    private String urlImage;

    public CommentJSONModel(String name, String sentence, String urlImage) {
        this.name = name;
        this.sentence = sentence;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
