package vu.travelapp.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterCommentFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.RetrofitFactory;
import vu.travelapp.networks.comment.comment;
import vu.travelapp.networks.notifications_service.NotificationJSON;
import vu.travelapp.networks.notifications_service.NotificationRequestJSON;
import vu.travelapp.networks.notifications_service.NotificationResponse;
import vu.travelapp.networks.notifications_service.PushNotificationService;
import vu.travelapp.networks.pullData.CommentJSONModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment implements View.OnClickListener {
    public DataModel datamodel;
    private RecyclerView rvComment;
    private AdapterCommentFragment adapterCommentFragment;
    private EditText etComment;
    private Button btSendComment;
    private List<DataModel> dataModelList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  String name, urlImage;
    private RelativeLayout back;
    private TextInputEditText inputText;
    private DatabaseReference databaseReference;
    public static boolean isChecked = false;

    private FirebaseMessaging firebaseMessaging;
    List<CommentJSONModel> commentJSONModels = new ArrayList<CommentJSONModel>();
    private static final String TAG = CommentFragment.class.toString();

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        process();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        EventBus.getDefault().register(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datauser", MODE_PRIVATE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(datamodel.getId());
        name = sharedPreferences.getString("name", "");
        String id = sharedPreferences.getString("id", "");
        Log.d("share preferences: ", "" + name + id);
        name = sharedPreferences.getString("name", "");
        urlImage = sharedPreferences.getString("urlImage", "");
        Log.d("share preferences: ", "" + name);
        init(view);
        return view;
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullData();
            }
        });
    }

    private void process() {
        final long timeComment = new Date().getTime();
        btSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final comment comment = new comment(name, String.valueOf(etComment.getText()), urlImage);
                Log.d("comment: ", "" + etComment.getText().toString());
                rvComment.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!comment.equals("")) {
                            databaseReference.push().setValue(comment);
                            Log.d("succesful ", "comment");
                            etComment.setText("");
                            rvComment.smoothScrollToPosition(Integer.valueOf(datamodel.getComment().size()));
                            /*******************PUSH NOTIFICATION*********************/
                            postNotifications();
                            isChecked = true; //Nếu do người dùng click thì không TOAST - Notification tới chính thiết bị này.

                            NotificationJSON notificationJSON = new NotificationJSON();
                            notificationJSON.setBody(String.format("Bạn %s đã bình luận vào ảnh!", comment.getName()));
                            notificationJSON.setTitle("Thông báo");
                            NotificationRequestJSON notificationRequestJSON = new NotificationRequestJSON();
                            notificationRequestJSON.setNotification(notificationJSON);
                            notificationRequestJSON.setTo(datamodel.getId());

                            RetrofitFactory retrofitFactory = RetrofitFactory.getInstance();
                            retrofitFactory.create(PushNotificationService.class).pushNotificationService(notificationRequestJSON).enqueue(new Callback<NotificationResponse>() {
                                @Override
                                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                                    Log.d(TAG, String.format("onResponse: Đã gửi notification %s", response.body().getMessage_id()));
                                }

                                @Override
                                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                                    Log.d(TAG, String.format("onFailure: Lỗi chết người %s", t.toString()));
                                }
                            });
                            /******************************************************************/
                        } else {
                        }
                    }
                });
                pullData();
                hideSoftKeyboard(getActivity());

                adapterCommentFragment.notifyDataSetChanged();
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void pullData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentJSONModels.clear();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    comment comment = (comment) commentSnapshot.getValue(comment.class);
                    CommentJSONModel commentJSONModel = new CommentJSONModel(comment.getName(), comment.getSentence(), comment.getUrlImage());
                    Log.d("comment model 2", " đây rồi: " + comment.getName() + " " + comment.getSentence());
                    commentJSONModels.add(commentJSONModel);
                }
                datamodel.setComment(commentJSONModels);

                Log.d("chạy vào đây", " có hay không?" + datamodel.getComment().size());
                adapterCommentFragment.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        pullData();
        back = (RelativeLayout) view.findViewById(R.id.rl_back_comment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(getActivity());

                getActivity().onBackPressed();
            }
        });
        rvComment = (RecyclerView) view.findViewById(R.id.rv_comment);
        adapterCommentFragment = new AdapterCommentFragment
                (commentJSONModels, getContext(), getActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setAdapter(adapterCommentFragment);
        etComment = (EditText) view.findViewById(R.id.et_comment);
        btSendComment = (Button) view.findViewById(R.id.bt_send_commnet);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
    }

    @Subscribe(sticky = true)
    public void onReceivedData(DataModel datamodel) {
        this.datamodel = datamodel;
    }

    private void postNotifications() {
        if (firebaseMessaging == null) {
            firebaseMessaging = FirebaseMessaging.getInstance();
            firebaseMessaging.subscribeToTopic(datamodel.getId());
            // firebaseMessaging.subscribeToTopic(datamodel.getId()+"_like");
            Log.d(TAG, String.format("postNotifications: Đã đăng kí user vào topic: %s", datamodel.getId()));
        }
    }

    @Override
    public void onClick(View v) {

    }
}
