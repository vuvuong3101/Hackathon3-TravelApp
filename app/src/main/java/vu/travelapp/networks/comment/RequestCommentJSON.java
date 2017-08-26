package vu.travelapp.networks.comment;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public class RequestCommentJSON {
    private String id;
    private comment comment;

    public RequestCommentJSON(String id, vu.travelapp.networks.comment.comment comment) {
        this.id = id;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public vu.travelapp.networks.comment.comment getComment() {
        return comment;
    }

    public void setComment(vu.travelapp.networks.comment.comment comment) {
        this.comment = comment;
    }
}
