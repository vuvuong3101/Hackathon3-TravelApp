package vu.travelapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.activities.MainActivity;
import vu.travelapp.adapter.AdapterSearchFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.pullData.RetrofitFactory;
import vu.travelapp.utils.FuzzyMatch;

/**
 * Created by trongphuong1011 on 8/20/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private List<DataModel> dataModelList;
    private RecyclerView rvSearch;
    private AdapterSearchFragment adapterSearchFragment;
    private CharSequence charSequence;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EventBus.getDefault().register(this);
        init(view);
        return view;
    }

    private void init(View view) {
        rvSearch = (RecyclerView) view.findViewById(R.id.rv_search);
        dataModelList = new ArrayList<>();
        GetAllDataModel getAllDataModel = RetrofitFactory.getInstance().create(GetAllDataModel.class);
        getAllDataModel.getDataModels().enqueue(new Callback<List<DataModelJson>>() {
            @Override
            public void onResponse(Call<List<DataModelJson>> call, Response<List<DataModelJson>> response) {
                int maxRatio = 0;
                for (int i = 0; i < response.body().size(); i++) {
                    DataModel dataModel = new DataModel();
                    dataModel.setName(response.body().get(i).getUsername());
                    dataModel.setImage(response.body().get(i).getImage());
                    dataModel.setUserid(response.body().get(i).getUserid());
                    dataModel.setDestination(response.body().get(i).getDestination());
                    dataModel.setContent(response.body().get(i).getContent());
                    dataModel.setLike(response.body().get(i).getLike());
                    dataModel.setId(response.body().get(i).get_id());
                    int ratio = FuzzyMatch.getRatio((String) charSequence, response.body().get(i).getDestination(), false);
                    if (ratio > maxRatio) {
                        maxRatio = ratio;
                        dataModelList.add(0, dataModel);
                        Log.d("Max ratio: ", "" + maxRatio);
                    } else{
                        dataModelList.add(dataModel);
                        Log.d("dataModel", ": "+dataModel.getDestination());
                    }
                }
                Log.d(" Kết quả",":  "+dataModelList.get(0).getDestination());
                adapterSearchFragment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataModelJson>> call, Throwable t) {
            }
        });
        adapterSearchFragment = new AdapterSearchFragment(dataModelList, getContext());
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(adapterSearchFragment);
        adapterSearchFragment.setOnItemClick(this);
    }

    @Subscribe(sticky = true)
    public void onReceived(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @Override
    public void onClick(View v) {
        DataModel dataModel = (DataModel) v.getTag();
        Log.d("home fragment: ", "" + dataModel.getLike());
    }
}
