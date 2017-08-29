package vu.travelapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vu.travelapp.R;
import vu.travelapp.models.DataModel;

/**
 * Created by trongphuong1011 on 8/15/2017.
 */

public class AdapterRankFragment extends RecyclerView.Adapter<AdapterRankFragment.RankModelViewHolder> {
    private List<DataModel> dataModels;
    private Context context;
    private View.OnClickListener onClickListener;
    View view;


    public AdapterRankFragment(List<DataModel> dataModels, Context context) {
        this.dataModels = dataModels;
        this.context = context;
    }

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RankModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_rank, viewGroup, false);
        view.setOnClickListener(onClickListener);
        return new RankModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankModelViewHolder rankModelViewHolder, int i) {
        rankModelViewHolder.setData(this.dataModels.get(i), context);
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class RankModelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageRank;
        TextView tvLikeRank;
        TextView tvNameRank;
        TextView tvDestinationRank;

        public RankModelViewHolder(View itemView) {
            super(itemView);
            this.init(itemView);
            view = itemView;
        }

        private void init(View itemView) {
            ivImageRank = (ImageView) itemView.findViewById(R.id.iv_image_rank);
            tvLikeRank = (TextView) itemView.findViewById(R.id.tv_like_rank);
            tvNameRank = (TextView) itemView.findViewById(R.id.user_name);
            tvDestinationRank = (TextView) itemView.findViewById(R.id.tv_destination_rank);
        }

        private void setData(DataModel data, Context context) {
            if (data != null) {
                Picasso.with(context).load(data.getImage()).into(ivImageRank);
                tvLikeRank.setText(String.valueOf(data.getLike()));
                tvNameRank.setText(data.getName());
                tvDestinationRank.setText(data.getDestination());
                view.setTag(data);
            }
        }
    }
}
