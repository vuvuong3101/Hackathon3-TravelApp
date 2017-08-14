package vu.travelapp.networks.pushData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trongphuong1011 on 8/13/2017.
 */

public interface UploadService {
    @POST("diphuot")
    Call<UploadRespondModel> uploadPost(@Body UploadRequestModel uploadRequestModel);
}
