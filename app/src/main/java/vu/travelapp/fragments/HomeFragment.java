package vu.travelapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterHomeFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.RetrofitFactory;
import vu.travelapp.networks.comment.comment;
import vu.travelapp.networks.pullData.CommentJSONModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.toString();
    public static List<DataModel> dataModelList;
    private RecyclerView rvHomeFragment;
    private AdapterHomeFragment adapterHomeFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView ivImageHome;
    private DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String name, id; //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.commit();
        this.getProfileModel();
        this.init(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        Refesh();
        Log.d(TAG, String.format("onCreateView: %s ", name));
        return view;
    }


    private void getProfileModel() { //TODO: lấy data profile_model và add user vào topic
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datauser", MODE_PRIVATE);
        this.name = sharedPreferences.getString("name", "");
    }

    private void Refesh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(getView());
            }

        });

    }

    private void init(View view) {
        ivImageHome = (ImageView) view.findViewById(R.id.item_image);

        dataModelList = new ArrayList<>();
        GetAllDataModel getAllDataModel = RetrofitFactory.getInstance().create(GetAllDataModel.class);
        getAllDataModel.getDataModels().enqueue(new Callback<List<DataModelJson>>() {
            @Override
            public void onResponse(Call<List<DataModelJson>> call, Response<List<DataModelJson>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    final DataModel dataModel = new DataModel();
                    dataModel.setTimeUpload(response.body().get(i).getTimeupload());
                    dataModel.setName(response.body().get(i).getUsername());
                    dataModel.setImage(response.body().get(i).getImage());
                    dataModel.setUserid(response.body().get(i).getUserid());
                    dataModel.setDestination(response.body().get(i).getDestination());
                    dataModel.setContent(response.body().get(i).getContent());
                    dataModel.setLike(response.body().get(i).getLike());
                    dataModel.setId(response.body().get(i).get_id());
                    databaseReference = database.getReference(response.body().get(i).get_id());
                    final List<CommentJSONModel> commentJSONModels = new ArrayList<CommentJSONModel>();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                                comment comment = (comment) commentSnapshot.getValue(comment.class);
                                CommentJSONModel commentJSONModel = new CommentJSONModel(comment.getName(), comment.getSentence(), comment.getUrlImage());
                                commentJSONModels.add(commentJSONModel);
                            }
                            Log.d("size: ", "" + commentJSONModels.size());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if (name.equals(dataModel.getName())) {
                        FirebaseMessaging.getInstance().subscribeToTopic(dataModel.getId());
                        FirebaseMessaging.getInstance().subscribeToTopic(dataModel.getId() + "_like");
                        Log.d(TAG, String.format("getProfileModel: Đã subrice 1 topic %s", name));
                    }
                    dataModel.setComment(commentJSONModels);
                    dataModelList.add(dataModel);
                }
                adapterHomeFragment.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<DataModelJson>> call, Throwable t) {
                Toast.makeText(getContext(), "Không kết nối", Toast.LENGTH_SHORT).show();
            }
        });
        rvHomeFragment = (RecyclerView) view.findViewById(R.id.rv_data_home_fragment);
        adapterHomeFragment = new AdapterHomeFragment(dataModelList, getContext(), getActivity().getSupportFragmentManager(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvHomeFragment.setLayoutManager(linearLayoutManager);
        rvHomeFragment.setAdapter(adapterHomeFragment);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        // Log.d(TAG, "onStop: Đã hủy đăng ký nhận listPosted");
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        DataModel dataModel = (DataModel) v.getTag();
        Log.d("home fragment: ", "" + dataModel.getLike());
    }
}
