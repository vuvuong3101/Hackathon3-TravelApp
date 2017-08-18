package vu.travelapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.R;
import vu.travelapp.models.DataModel;

public class ImageDetailFragment extends Fragment {
    private FloatingActionButton fabDirection;
    private DataModel dataModel;
    private ImageView ivBack , imageHeader;
    private TextView tvDestination, tvContent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail,container,false);
        EventBus.getDefault().register(this);
        init(view);
        process();
        return view;
    }

    private void process() {
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(dataModel);
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tvDestination.setText(dataModel.getDestination());
        tvContent.setText(dataModel.getContent());
        Picasso.with(getContext()).load(dataModel.getImage()).into(imageHeader);
    }

    @Subscribe(sticky = true)
    public void onReceivedDataModel(DataModel dataModel){
        this.dataModel = dataModel;
        Log.d("data destination: ",""+ dataModel.getDestination());
    }

    private void init(View view) {
        fabDirection = (FloatingActionButton) view.findViewById(R.id.fab_direction);
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        imageHeader = (ImageView) view.findViewById(R.id.header);
        tvDestination = (TextView) view.findViewById(R.id.tv_destination);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
    }
}
