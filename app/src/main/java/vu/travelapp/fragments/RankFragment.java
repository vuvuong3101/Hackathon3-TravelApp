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
import vu.travelapp.adapter.AdapterRankFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.pullData.RetrofitFactory;

/**
 * Created by trongphuong1011 on 8/15/2017.
 */

public class RankFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = RankFragment.class.toString();
    private List<DataModel> dataModelList;
    private RecyclerView rvRankFragment;
    private AdapterRankFragment adapterRankFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        init(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refeshlayout_rank);
        Refresh();
        return view;

    }

    private void Refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(getView());
            }
        });
    }
    private void init(View view) {
        dataModelList = new ArrayList<>();
        GetAllDataModel getAllDataModel = RetrofitFactory.getInstance().create(GetAllDataModel.class);
        getAllDataModel.getDataModels().enqueue(new Callback<List<DataModelJson>>() {
            @Override
            public void onResponse(Call<List<DataModelJson>> call, Response<List<DataModelJson>> response) {
                int maxLike = 0;
                for (int i = 0; i < response.body().size(); i++) {
                    DataModel dataModel = new DataModel();
                    dataModel.setName(response.body().get(i).getUsername());
                    dataModel.setImage(response.body().get(i).getImage());
                    dataModel.setUserid(response.body().get(i).getUserid());
                    dataModel.setContent(response.body().get(i).getContent());
                    dataModel.setLike(response.body().get(i).getLike());
                    dataModel.setId(response.body().get(i).get_id());
                    if (dataModel.getLike() > maxLike) {
                        maxLike = dataModel.getLike();
                        dataModelList.add(0, dataModel);
                        Log.d("Max like: ", "" + maxLike);
                    } else {
                        int dataSize = dataModelList.size();
                        for (int j = 0; j < dataSize; j++) {
                            if (dataModel.getLike() >= dataModelList.get(j).getLike()) {
                                dataModelList.add(j, dataModel);
                                break;
                            }
                            if (dataModel.getLike() < dataModelList.get(dataSize - 1).getLike()) {
                                dataModelList.add(dataModel);
                                break;
                            }
                        }
                    }
                    Log.d(TAG, "onResponse: Đã lấy dữ liệu cho Rank Fragment!!!!!");
                }
                adapterRankFragment.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<DataModelJson>> call, Throwable t) {
                Toast.makeText(getContext(), "Không kết nối", Toast.LENGTH_SHORT).show();
                Log.d(TAG, String.format("onFailure: %s", t.toString()));
            }
        });

        rvRankFragment = (RecyclerView) view.findViewById(R.id.rv_data_rank_fragment);
        adapterRankFragment = new AdapterRankFragment(dataModelList, getContext());
        rvRankFragment.setLayoutManager(new LinearLayoutManager(getContext()));

        rvRankFragment.setAdapter(adapterRankFragment);

        adapterRankFragment.setOnItemClick(this);
        Log.d(TAG, "init: Đã có dữ liệu");
    }

    @Override
    public void onClick(View v) {
        DataModel dataModel = (DataModel) v.getTag();
        Log.d("home fragment: ", "" + dataModel.getLike());
    }

}
