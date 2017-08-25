package vu.travelapp.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class RetrofitFactory {
    private static Retrofit retrofit;

    private static RetrofitFactory instance = new RetrofitFactory();

    public static RetrofitFactory getInstance() {
        return instance;
    }

    private RetrofitFactory() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://diphuot.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public <T> T create (final  Class<T> service){
        return retrofit.create(service);
    }
}
