package vu.travelapp.networks.updateData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by trongphuong1011 on 8/16/2017.
 */

public interface UpdateService {
    @PUT("diphuot")
    Call<UpdateLikeResponseModel> updatelike(@Body UpdateLikeRequestModel updateLikeRequestModel);
}
