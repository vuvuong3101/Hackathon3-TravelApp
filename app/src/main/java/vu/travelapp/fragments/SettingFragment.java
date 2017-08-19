package vu.travelapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import vu.travelapp.LoginActivity;
import vu.travelapp.R;

/**
 * Created by mac-vuongvu on 8/11/17.
 */

public class SettingFragment extends android.support.v4.app.Fragment {
    private RelativeLayout rlLogout;
    private RelativeLayout rlInfo;
    private RelativeLayout rlBack;
    private String TAG = SettingFragment.class.toString();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        init(view);
        process();

        return view;
    }

    private void process() {
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++){
                  //  Log.d(TAG, String.format("onClick: %s", fm.getBackStackEntryCount()));
                    fm.popBackStack();
                }
                LoginManager.getInstance().logOut();
                Toast.makeText(getContext(), "Đã đăng xuất !", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });
        rlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void init(View view) {
        rlLogout = (RelativeLayout) view.findViewById(R.id.rl_logout);
        rlInfo = (RelativeLayout) view.findViewById(R.id.rl_info);
        rlBack = (RelativeLayout) view.findViewById(R.id.rl_back);
    }

}
