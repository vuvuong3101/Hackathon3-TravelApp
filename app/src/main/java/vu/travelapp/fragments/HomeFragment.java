package vu.travelapp.fragments;


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

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterHomeFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.models.RealmHandle;
import vu.travelapp.networks.comment.comment;
import vu.travelapp.networks.pullData.CommentJSONModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.RetrofitFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.toString();
    public static List<DataModel> dataModelList = new ArrayList<>();
    private RecyclerView rvHomeFragment;
    private AdapterHomeFragment adapterHomeFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView ivImageHome;
    private DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.commit();
        setupUI(view);
        init(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        Refesh();
        return view;
    }

    private void setupUI(View view) {
        ivImageHome = (ImageView) view.findViewById(R.id.item_image);
        rvHomeFragment = (RecyclerView) view.findViewById(R.id.rv_data_home_fragment);
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
        adapterHomeFragment = new AdapterHomeFragment(dataModelList, getContext(), getActivity().getSupportFragmentManager(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvHomeFragment.setLayoutManager(linearLayoutManager);
        rvHomeFragment.setAdapter(adapterHomeFragment);
        if(RealmHandle.getDataModel().size() == 0){
            loadData();
        } else {
            Log.d("Realm size ",""+RealmHandle.getDataModel().size());
            dataModelList.addAll(RealmHandle.getDataModel());
            adapterHomeFragment.notifyDataSetChanged();
        }

    }

    private void loadData(){
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
                    final RealmList<CommentJSONModel> commentJSONModels = new RealmList<>();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot commentSnapshot : dataSnapshot.getChildren()){
                                comment comment = (comment) commentSnapshot.getValue(comment.class);
                                CommentJSONModel commentJSONModel = new CommentJSONModel(comment.getName(),comment.getSentence(),comment.getUrlImage());
                                commentJSONModels.add(commentJSONModel);
                            }
                            Log.d("size: ",""+commentJSONModels.size());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    dataModel.setComment((RealmList<CommentJSONModel>) commentJSONModels);
                    RealmHandle.addDataModel(dataModel);
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
    }


    @Override
    public void onClick(View v) {
        DataModel dataModel = (DataModel) v.getTag();
        Log.d("home fragment: ", "" + dataModel.getLike());
    }
}
