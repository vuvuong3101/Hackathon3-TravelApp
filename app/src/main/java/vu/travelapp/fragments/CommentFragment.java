package vu.travelapp.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vu.travelapp.R;
import vu.travelapp.adapter.AdapterCommentFragment;
import vu.travelapp.adapter.AdapterRankFragment;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.comment.CommentService;
import vu.travelapp.networks.comment.RequestCommentJSON;
import vu.travelapp.networks.comment.ResponseCommentJSON;
import vu.travelapp.networks.comment.comment;
import vu.travelapp.networks.pullData.CommentJSONModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.pullData.RetrofitFactory;
import vu.travelapp.networks.pushData.UploadService;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment implements View.OnClickListener {
    public DataModel datamodel;
    private RecyclerView rvComment;
    private AdapterCommentFragment adapterCommentFragment;
    private EditText etComment;
    private Button btSendComment;
    private List<DataModel> dataModelList;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        EventBus.getDefault().register(this);
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datauser", Context.MODE_PRIVATE);
//        name = sharedPreferences.getString("name","");
        Log.d("share preferences: ","" + name);
        init(view);
        process();
        return view;
    }

    private void process() {
        btSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final comment comment = new comment(name,String.valueOf(etComment.getText()));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://diphuot.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create())
                        .build();
                CommentService commentService = retrofit.create(CommentService.class);
                commentService.commentService(new RequestCommentJSON(datamodel.getId(), comment)).enqueue(new Callback<ResponseCommentJSON>() {
                    @Override
                    public void onResponse(Call<ResponseCommentJSON> call, Response<ResponseCommentJSON> response) {
                        String mess = response.body().getMessage();
                        Log.d("upload data: ", "success" + mess);
                    }

                    @Override
                    public void onFailure(Call<ResponseCommentJSON> call, Throwable t) {
                        Log.d("error: ",""+t);
                    }
                });
            }
        });
    }

    private void init(View view) {
        rvComment = (RecyclerView) view.findViewById(R.id.rv_comment);
        adapterCommentFragment = new AdapterCommentFragment
                (datamodel.getComment(), getContext(), getActivity().getSupportFragmentManager());
        rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComment.setAdapter(adapterCommentFragment);
        etComment = (EditText) view.findViewById(R.id.et_comment);
        btSendComment = (Button) view.findViewById(R.id.bt_send_commnet);
    }

    @Subscribe(sticky = true)
    public void onReceivedData(DataModel datamodel) {
        this.datamodel = datamodel;
    }


    @Override
    public void onClick(View v) {

    }
}
