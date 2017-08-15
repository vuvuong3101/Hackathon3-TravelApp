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
        View view = layoutInflater.inflate(R.layout.item_home, viewGroup, false);
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

        public HomeModelViewHolder(View itemView) {
            super(itemView);
            this.init(itemView);
            view = itemView;
        }

        private void init(View itemView) {
            ivItemPictureHome = (ImageView) itemView.findViewById(R.id.item_image);
            tvContent = (TextView) itemView.findViewById(R.id.time);
            tvUserName = (TextView) itemView.findViewById(R.id.user_name);
        }

        public void setData(DataModel data, Context context) {
            if (data != null) {
                Picasso.with(context).load(data.getImage()).resize(1280, 720).onlyScaleDown().into(ivItemPictureHome); //        <- chính nó đó
                tvUserName.setText(data.getName());
                tvContent.setText(data.getContent());
                view.setTag(data);
            }
        }
    }
}
