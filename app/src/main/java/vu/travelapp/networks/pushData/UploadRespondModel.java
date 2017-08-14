package vu.travelapp.networks.pushData;

/**
 * Created by trongphuong1011 on 8/13/2017.
 */

public class UploadRespondModel {
    private String message;

    public UploadRespondModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
