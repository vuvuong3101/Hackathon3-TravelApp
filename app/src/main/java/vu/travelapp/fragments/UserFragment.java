package vu.travelapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vu.travelapp.LoginActivity;
import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.R;
import vu.travelapp.models.ProfileModel;

public class UserFragment extends Fragment {
    private static final String TAG = UserFragment.class.toString();
    ImageView  avatar, ivImageCrop;
    Dialog dialog;
    TextView tvName, tvLocation, tvBirthday;
    ImageView direction;
    RelativeLayout ivBack, ivSetting;
    ProfileModel profileModel;
    String locationSplit1[], locationSplit2[], locationSplit3[];
    String a, b, location;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        EventBus.getDefault().register(this);
        FindView(view);
        ProcessUI();
        return view;
    }

    private void ProcessUI() {
        locationSplit1 =  profileModel.getLocation().split(",");
        locationSplit2 = locationSplit1[1].split(":");
        a = locationSplit2[1];
        b = a.replace("}", "").substring(1, a.length() -2);
        tvName.setText(profileModel.getName());
        tvLocation.setText(b);
        Log.d(TAG, "ProcessUI: " + profileModel.getLocation());
        tvBirthday.setText(profileModel.getBirthday());
        Picasso.with(getContext()).load(profileModel.getImageURL()).into(avatar);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();

            }
        });
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + " logout FB");
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                LoginManager.getInstance().logOut();

            }
        });

    }

    private void FindView(View view) {
        tvName = view.findViewById(R.id.profile_user_name);
        tvLocation =  view.findViewById(R.id.location);
        tvBirthday =  view.findViewById(R.id.birthday);
        ivBack = view.findViewById(R.id.rl_back);
        avatar = view.findViewById(R.id.profile_avatar);
        direction = view.findViewById(R.id.iv_map);
        ivSetting = view.findViewById(R.id.rl_setting);
    }

    @Subscribe(sticky = true)
    public void onReceivedProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
        Log.d(TAG, "onReceivedProfileModel: Đã đổ dữ liệu vào profile Fragment");
    }

}
