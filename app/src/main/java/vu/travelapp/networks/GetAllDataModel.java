package vu.travelapp.networks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ADMIN on 8/11/2017.
 */

public interface GetAllDataModel {
    @GET("https://diphuot.herokuapp.com/api/diphuot")
    Call<List<DataModelJson>> getDataModels();
}
