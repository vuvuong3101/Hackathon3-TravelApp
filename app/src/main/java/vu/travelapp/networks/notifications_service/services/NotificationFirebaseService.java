package vu.travelapp.networks.notifications_service.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vu.travelapp.R;
import vu.travelapp.activities.MainActivity;
import vu.travelapp.adapter.AdapterHomeFragment;
import vu.travelapp.fragments.CommentFragment;
import vu.travelapp.fragments.HomeFragment;


/**
 * Created by ADMIN on 8/25/2017.
 */

public class NotificationFirebaseService extends FirebaseMessagingService {
    private static final String TAG = NotificationFirebaseService.class.toString();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // không hiện noti với chính thiết bị chủ động tạo noti.
        this.createNotification();
        this.createToast();

        CommentFragment.isChecked = false;
        AdapterHomeFragment.isLike = false;
        Log.d(TAG, String.format("onMessageReceived: new notification, isCheck: %s", CommentFragment.isChecked));
    }

    private void createNotification() {
        if (!CommentFragment.isChecked) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentText("Thông báo từ Travel App");
            builder.setContentText("Có thông bình luận mới chưa đọc");
            builder.setSmallIcon(R.drawable.ic_share_white_24dp);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            notificationManager.notify(1, builder.build());
        }
//        if (AdapterHomeFragment.isLike){
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//            Intent intent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
//
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setContentText("Thông báo từ Travel App");
//            builder.setContentText("Bài viết của bạn được người khác like!");
//            builder.setSmallIcon(R.drawable.ic_share_white_24dp);
//            builder.setContentIntent(pendingIntent);
//            builder.setAutoCancel(true);
//
//            notificationManager.notify(1, builder.build());
//        }
    }

    private void createToast() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (!CommentFragment.isChecked) {
                    final View viewToast = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cus_toast, null);
                    ((TextView) viewToast.findViewById(R.id.tv_title)).setText("Thông báo");
                    ((TextView) viewToast.findViewById(R.id.tv_mess)).setText("Bạn có thông báo mới");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(viewToast);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
                    toast.show();
                }
//                if (AdapterHomeFragment.isLike) {
//                    final View viewToast = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cus_toast, null);
//                    ((TextView) viewToast.findViewById(R.id.tv_title)).setText("Thông báo");
//                    ((TextView) viewToast.findViewById(R.id.tv_mess)).setText("Một bạn đã like ảnh bạn");
//                    Toast toast = new Toast(getApplicationContext());
//                    toast.setView(viewToast);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
//                    toast.show();
//
//                }
            }
        });
    }
}
