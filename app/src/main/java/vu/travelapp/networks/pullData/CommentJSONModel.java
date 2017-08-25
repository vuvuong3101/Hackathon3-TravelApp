package vu.travelapp.networks.pullData;

/**
 * Created by trongphuong1011 on 8/19/2017.
 */

public class CommentJSONModel {
    private String name;
    private String sentence;


    public CommentJSONModel(String name, String sentence) {
        this.name = name;
        this.sentence = sentence;
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
