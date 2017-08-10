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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vu.travelapp.MainActivity;
import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.R;
import vu.travelapp.models.ProfileModel;

public class UserFragment extends Fragment {
    private static final String TAG = UserFragment.class.toString();
    ImageView ivBack, avatar, ivImageCrop;
    Dialog dialog;
    TextView tvName;
    ImageView direction;

    ProfileModel profileModel;

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
        //
        avatar.setImageBitmap(profileModel.getBitmap());
        tvName.setText(profileModel.getName());

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

    }

    private void FindView(View view) {
        tvName = view.findViewById(R.id.profile_user_name);
        ivBack = view.findViewById(R.id.back);
        avatar = view.findViewById(R.id.profile_avatar);
        direction = view.findViewById(R.id.iv_direction);
    }



    @Subscribe(sticky = true)
    public void onReceivedProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
        Log.d(TAG, "onReceivedProfileModel: Đã đổ dữ liệu vào profile Fragment");
    }

}
