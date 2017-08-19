package vu.travelapp.fragments.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vu.travelapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentComment extends Fragment {


    public FragmentComment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_comment, container, false);
    }

}
