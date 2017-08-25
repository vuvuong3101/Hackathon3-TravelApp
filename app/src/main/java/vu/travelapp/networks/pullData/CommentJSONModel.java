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

<<<<<<< HEAD

    public CommentJSONModel(String name, String sentence) {
=======
    public CommentJSONModel(String name, String sentence, String urlImage) {
>>>>>>> 628acd0e42764862ba7e1000543e2a42d8039a09
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
