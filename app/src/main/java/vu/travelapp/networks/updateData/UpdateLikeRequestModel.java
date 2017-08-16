package vu.travelapp.networks.updateData;

/**
 * Created by trongphuong1011 on 8/16/2017.
 */

public class UpdateLikeRequestModel {
    private String id;
    private int like;

    public UpdateLikeRequestModel(String id, int like) {
        this.id = id;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
