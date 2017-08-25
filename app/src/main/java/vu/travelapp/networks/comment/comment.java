package vu.travelapp.networks.comment;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public class comment {
    private String name;
    private String sentence;

    public comment(String name, String sentence) {
        this.name = name;
        this.sentence = sentence;
    }

    public comment() {
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
