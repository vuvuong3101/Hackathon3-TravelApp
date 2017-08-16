package vu.travelapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.R;

public class ImageDetailFragment extends Fragment {
    private FloatingActionButton fabDirection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail,container,false);
        init(view);
        process();
        return view;
    }

    private void process() {
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {
        fabDirection = (FloatingActionButton) view.findViewById(R.id.fab_direction);
    }
}
