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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterSearchFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.RetrofitFactory;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.utils.FuzzyMatch;

/**
 * Created by trongphuong1011 on 8/20/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private List<DataModel> dataModelList;
    private RecyclerView rvSearch;
    private AdapterSearchFragment adapterSearchFragment;
    CharSequence charSequence;
    private TextView notFindLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EventBus.getDefault().register(this);

        init(view);

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    private void init(View view) {
        notFindLocation = (TextView) view.findViewById(R.id.notFindLocation);
        rvSearch = (RecyclerView) view.findViewById(R.id.rv_search);
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
                    dataModel.setDestination(response.body().get(i).getDestination());
                    dataModel.setContent(response.body().get(i).getContent());
                    dataModel.setLike(response.body().get(i).getLike());
                    dataModel.setId(response.body().get(i).get_id());
                    int ratio = FuzzyMatch.getRatio((String) charSequence, response.body().get(i).getDestination(), false);
                    if (ratio > 70) {
                        dataModelList.add(dataModel);
                    }
                }
                if(dataModelList.size()==0){
                    notFindLocation.setVisibility(View.VISIBLE);
                } else{
                    adapterSearchFragment.notifyDataSetChanged();
                }
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
