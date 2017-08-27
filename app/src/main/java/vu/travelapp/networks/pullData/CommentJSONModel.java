package vu.travelapp.networks.pullData;

import io.realm.RealmObject;
import io.realm.RealmObjectSchema;

/**
 * Created by trongphuong1011 on 8/19/2017.
 */

public class CommentJSONModel extends RealmObject {
    //đó em cho thành object rồi
    // và trong kia em set comment cho nó

    private String name;
    private String sentence;
    private String urlImage;

    public CommentJSONModel() {
    }

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
