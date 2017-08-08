package vu.travelapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vu.travelapp.R;
import vu.travelapp.managers.ScreenManager;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ImageView proFiles;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        proFiles = view.findViewById(R.id.profile_user);

        proFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " +" ahihihi");
                Fragment fm = new UserFragment();
                ScreenManager.openFragment(getFragmentManager(), fm, R.id.content_home );
            }
        });
        return view;
    }




}
