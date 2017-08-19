package vu.travelapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import vu.travelapp.R;
import vu.travelapp.adapter.AdapterPostedProfileFragment;
import vu.travelapp.managers.ScreenManager;
import vu.travelapp.models.DataModel;
import vu.travelapp.models.ProfileModel;

public class UserFragment extends Fragment {
    private static final String TAG = UserFragment.class.toString();
    ImageView avatar, ivImageCrop;
    Dialog dialog;
    TextView tvName, tvLocation, tvBirthday;
    RelativeLayout ivBack, ivSetting;
    ProfileModel profileModel;
    String locationSplit1[], locationSplit2[], locationSplit3[];
    String a, b, location;
    protected RecyclerView rvPostProfile;
    protected List<DataModel> listPosted;
    private AdapterPostedProfileFragment adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        EventBus.getDefault().register(this);
        FindView(view);
        ProcessUI();
        return view;
    }

    private void ProcessUI() {
//        locationSplit1 = profileModel.getLocation().split(",");
//        locationSplit2 = locationSplit1[1].split(":");
//        a = locationSplit2[1];
//        b = a.replace("}", "").substring(1, a.length() - 2);
//        tvLocation.setText(b);
        tvName.setText(profileModel.getName());
        Picasso.with(getContext()).load(profileModel.getUrlImage()).into(avatar);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new SettingFragment(),R.id.main);
            }
        });
        Log.d(TAG, "ProcessUI: " + profileModel.getLocation());
    }

    private void FindView(View view) {
        listPosted = new ArrayList<>();
        tvName = (TextView) view.findViewById(R.id.profile_user_name);
        tvLocation = (TextView) view.findViewById(R.id.location);
        ivBack = (RelativeLayout) view.findViewById(R.id.rl_back);
        avatar = (ImageView) view.findViewById(R.id.profile_avatar);
        ivSetting = (RelativeLayout) view.findViewById(R.id.rl_setting);

        adapter = new AdapterPostedProfileFragment(getContext(), listPosted);
        rvPostProfile = (RecyclerView) view.findViewById(R.id.rv_posted_profile_fragment);
        rvPostProfile.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        rvPostProfile.setLayoutManager(gridLayoutManager);

        this.loadPostedPicture();
    }

    private void loadPostedPicture() {
        this.cleardata();
        for (int i = 0; i < HomeFragment.dataModelList.size(); i++){
            if (profileModel.getId().equals(HomeFragment.dataModelList.get(i).getUserid())){
                DataModel data = new DataModel();
                data.setImage(HomeFragment.dataModelList.get(i).getImage());
                listPosted.add(data);
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void cleardata(){
       listPosted.clear();
    }

    @Subscribe(sticky = true)
    public void onReceivedProfileModel(ProfileModel profileModel) {
        try {
            this.profileModel = profileModel;
            tvName.setText(profileModel.getName());
            Picasso.with(getContext()).load(profileModel.getUrlImage()).into(avatar);
            Log.d(TAG, "onReceivedProfileModel: Đã đổ dữ liệu vào profile Fragment");

            this.loadPostedPicture();
        }catch (Exception e){
            Log.d(TAG, "onReceivedProfileModel: "+e.toString());
        }
    }

    public final void CloseandClear() {
        //TODO: 
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
