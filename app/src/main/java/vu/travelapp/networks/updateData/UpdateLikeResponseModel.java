package vu.travelapp.networks.updateData;

/**
 * Created by trongphuong1011 on 8/16/2017.
 */

public class UpdateLikeResponseModel {
    private String message;

    public UpdateLikeResponseModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
