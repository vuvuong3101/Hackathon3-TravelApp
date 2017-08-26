package vu.travelapp.networks.notifications_service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ADMIN on 8/25/2017.
 */

public interface PushNotificationService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAA9W7LLV4:APA91bEvLvL986GyiyU12QdG0h1pBndLDsgCL59-9ZDjatoyO_XBsWd40pfo3jxol7tt4e0yztdVgp3B0r6g6VLT0_BYJdwzFo5AZDK1LqFJhktWYh9CxAq483gk1tj0wo1cfK5kdGLg"
    })
    @POST("https://fcm.googleapis.com/fcm/send")
    Call<NotificationResponse> pushNotificationService(@Body NotificationRequestJSON notificationRequestJSON);
}
