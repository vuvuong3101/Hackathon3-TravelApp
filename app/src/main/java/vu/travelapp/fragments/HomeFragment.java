package vu.travelapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterHomeFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.pullData.RetrofitFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.toString();
    private List<DataModel> dataModelList;
    private RecyclerView rvHomeFragment;
    private AdapterHomeFragment adapterHomeFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Đã khởi tạo Home Fragment");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.init(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refeshlayout);
        Refesh();
        return view;
    }

    private void Refesh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                adapterHomeFragment.notifyDataSetChanged();

            }

        });

    }

    private void init(View view) {
        dataModelList = new ArrayList<>();
        GetAllDataModel getAllDataModel = RetrofitFactory.getInstance().create(GetAllDataModel.class);
        getAllDataModel.getDataModels().enqueue(new Callback<List<DataModelJson>>() {
            @Override
            public void onResponse(Call<List<DataModelJson>> call, Response<List<DataModelJson>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    DataModel dataModel = new DataModel();
                    dataModel.setName(response.body().get(i).getUsername());
                    dataModel.setImage(response.body().get(i).getImage());
                    dataModel.setUserid(response.body().get(i).getUserid());
                    dataModel.setContent(response.body().get(i).getContent());
                    dataModel.setLike(response.body().get(i).getLike());
                    dataModel.setId(response.body().get(i).get_id());
                    dataModelList.add(dataModel);
                    Log.d(TAG, "onResponse: Đã lấy dữ liệu từ server");
                }
                adapterHomeFragment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataModelJson>> call, Throwable t) {
                Toast.makeText(getContext(), "Không kết nối", Toast.LENGTH_SHORT).show();
                Log.d(TAG, String.format("onFailure: %s", t.toString()));
            }
        });

        rvHomeFragment = (RecyclerView) view.findViewById(R.id.rv_data_home_fragment);
        adapterHomeFragment = new AdapterHomeFragment(dataModelList, getContext());
        rvHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));

        rvHomeFragment.setAdapter(adapterHomeFragment);



        Log.d(TAG, "init: Đã có dữ liệu");

    }





}
