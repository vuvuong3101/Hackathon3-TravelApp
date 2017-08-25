package vu.travelapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterCommentFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.pullData.CommentJSONModel;

public class ImageDetailFragment extends Fragment {
    private FloatingActionButton fabDirection;
    private DataModel dataModel;
    private ImageView imageHeader;
    private TextView tvDestination, tvContent, tvLike,tvComment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout iv_back;
    private Dialog dialog;
    RecyclerView recyclerView;
    AdapterCommentFragment adapterCommentFragment;
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
        tvContent.setText(dataModel.getContent());
        Picasso.with(getContext()).load(dataModel.getImage()).into(imageHeader);
        tvLike.setText(String.valueOf(dataModel.getLike()));
        tvComment.setText(String.valueOf(dataModel.getComment().size()));

        adapterCommentFragment = new AdapterCommentFragment
                (dataModel.getComment(), getContext(), getActivity().getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterCommentFragment.notifyDataSetChanged();
        recyclerView.setAdapter(adapterCommentFragment);

    }

    @Subscribe(sticky = true)
    public void onReceivedDataModel(DataModel dataModel){
        this.dataModel = dataModel;
        Log.d("data destination: ",""+ dataModel.getDestination());
    }

    private void init(View view) {
        tvLike = (TextView) view.findViewById(R.id.count_like);
        tvComment = (TextView) view.findViewById(R.id.count_comment_detail);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_comment_detail);
        fabDirection = (FloatingActionButton) view.findViewById(R.id.iv_location);
        iv_back = (RelativeLayout) view.findViewById(R.id.back_detals);
        imageHeader = (ImageView) view.findViewById(R.id.header);
//        tvDestination = (TextView) view.findViewById(R.id.tv_destination);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
    }



}
