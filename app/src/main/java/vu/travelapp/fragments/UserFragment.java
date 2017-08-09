package vu.travelapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vu.travelapp.R;

public class UserFragment extends Fragment {
    private ImageView ivBack, avatar;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        FindView(view);
        ProcessUI();
        return view;
    }

    private void ProcessUI() {
        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        //
    }

    private void FindView(View view) {
        ivBack = view.findViewById(R.id.back);
        avatar = view.findViewById(R.id.profile_avatar);
    }

    public void showDialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_image);
        dialog.show();
    }


}
