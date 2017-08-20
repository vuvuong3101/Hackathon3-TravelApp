package vu.travelapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vu.travelapp.R;

import vu.travelapp.models.DataModel;
import vu.travelapp.networks.pullData.CommentJSONModel;

public class ImageDetailFragment extends Fragment {
    private FloatingActionButton fabDirection;
    private DataModel dataModel;
    private ImageView ivBack , imageHeader;
    private TextView tvDestination, tvContent;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout iv_back;
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
//        fabDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().postSticky(dataModel);
//                Intent intent = new Intent(getActivity(),MapsActivity.class);
//                startActivity(intent);
//            }
//        });
        imageHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(CommentJSONModel commentJSONModel: dataModel.getComment()){
                    Log.d("","adapter home nhé!: "+ commentJSONModel.getName()+"   "+commentJSONModel.getSentence());
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
//        tvDestination.setText(dataModel.getDestination());
//        tvContent.setText(dataModel.getContent());
//        Picasso.with(getContext()).load(dataModel.getImage()).into(imageHeader);

    }

    @Subscribe(sticky = true)
    public void onReceivedDataModel(DataModel dataModel){
        this.dataModel = dataModel;
        Log.d("data destination: ",""+ dataModel.getDestination());
    }

    private void init(View view) {
//        fabDirection = (FloatingActionButton) view.findViewById(R.id.fab_direction);
        iv_back = (RelativeLayout) view.findViewById(R.id.rl_back);
        imageHeader = (ImageView) view.findViewById(R.id.header);
//        tvDestination = (TextView) view.findViewById(R.id.tv_destination);
        viewPager = (ViewPager) view.findViewById(R.id.vp_details);
        tabLayout  = (TabLayout) view.findViewById(R.id.tablayout);
//        tvContent = (TextView) view.findViewById(R.id.tv_content);
    }

}
