package vu.travelapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vu.travelapp.R;
import vu.travelapp.models.DataModel;
import vu.travelapp.networks.updateData.UpdateLikeRequestModel;
import vu.travelapp.networks.updateData.UpdateLikeResponseModel;
import vu.travelapp.networks.updateData.UpdateService;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class AdapterHomeFragment extends RecyclerView.Adapter<AdapterHomeFragment.HomeModelViewHolder> {
    private List<DataModel> dataModels;
    private Context context;
    private final int view_item = 1;
    private final int view_pro = 0;
    private View.OnClickListener onClickListener;
    View view;

    public AdapterHomeFragment(List<DataModel> dataModels, Context context) {
        this.dataModels = dataModels;
        this.context = context;
    }

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public HomeModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_home_two, viewGroup, false);
        view.setOnClickListener(onClickListener);
        return new HomeModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeModelViewHolder homeModelViewHolder, int i) {
        homeModelViewHolder.setData(this.dataModels.get(i), this.context);
    }

    public void add(DataModel s) {
        dataModels.add(s);
        notifyDataSetChanged();
    }

    public void clear() {
        dataModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class HomeModelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemPictureHome;
        TextView tvContent, tvUserName;
        ImageView ivLike, ivComment;
        TextView tvLike, tvComment;
        LinearLayout llLike, llComment;


        public HomeModelViewHolder(View itemView) {
            super(itemView);
            this.init(itemView);
            view = itemView;
        }

        private void init(View itemView) {
            ivItemPictureHome = (ImageView) itemView.findViewById(R.id.item_image);
            tvContent = (TextView) itemView.findViewById(R.id.time);
            tvUserName = (TextView) itemView.findViewById(R.id.user_name);
            ivLike = (ImageView) itemView.findViewById(R.id.ic_like);
            ivComment = (ImageView) itemView.findViewById(R.id.ic_comment);
            llLike = (LinearLayout) itemView.findViewById(R.id.like);
            llComment = (LinearLayout) itemView.findViewById(R.id.comment);
        }

        public void setData(final DataModel data, Context context) {
            if (data != null) {
                Picasso.with(context).load(data.getImage()).into(ivItemPictureHome); //        <- chính nó đó
                tvUserName.setText(data.getName());
                tvContent.setText(data.getContent());
                view.setTag(data);
            }
            llLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateLike(data);
                }
            });
        }
        private void UpdateLike(DataModel data) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://diphuot.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create()).build();
            data.getId();
            UpdateService updateService = retrofit.create(UpdateService.class);
            updateService.updatelike(new UpdateLikeRequestModel(data.getId(),data.getLike()+1)).enqueue(new Callback<UpdateLikeResponseModel>() {
                @Override
                public void onResponse(Call<UpdateLikeResponseModel> call, Response<UpdateLikeResponseModel> response) {
                    String message = response.body().getMessage();
                    Log.d("update like nè: ",""+message);
                }

                @Override
                public void onFailure(Call<UpdateLikeResponseModel> call, Throwable t) {
                    Log.d("có bug vỡ mồm ở update like: ",""+t);
                }
            });
        }
    }
}
