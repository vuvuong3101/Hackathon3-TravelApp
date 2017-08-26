package vu.travelapp.networks.comment;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public class ResponseCommentJSON {
    private String message;

    public ResponseCommentJSON(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
