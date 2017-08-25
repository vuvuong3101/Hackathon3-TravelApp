package vu.travelapp.networks.comment;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public class comment {
    private String name;
    private String sentence;
    private String urlImage;

    public comment() {
    }

    public comment(String name, String sentence, String urlImage) {
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
