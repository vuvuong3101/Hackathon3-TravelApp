package vu.travelapp.networks.notifications_service.services;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ADMIN on 8/25/2017.
 */

public class NotificationFirebaseService extends FirebaseMessagingService {
    private static final String TAG = NotificationFirebaseService.class.toString();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Toast.makeText(this, "Có thông báo mới", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMessageReceived: new notification");
    }
}
