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

    public AdapterHomeFragment(List<DataModel> dataModels, Context context) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @Override
    public HomeModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_home, viewGroup, false);

        return new HomeModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeModelViewHolder homeModelViewHolder, int i) {
        homeModelViewHolder.setData(this.dataModels.get(i), this.context);
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class HomeModelViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemPictureHome;
        TextView tvContent, tvUserName;
        View itemView;


        public HomeModelViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.init(itemView);
        }

        private void init(View itemView){
            ivItemPictureHome = itemView.findViewById(R.id.item_image);
            tvContent = itemView.findViewById(R.id.time);
            tvUserName = itemView.findViewById(R.id.user_name);

        }

        public void setData(DataModel data, Context context){
            Picasso.with(context).load(data.getBase64image()).into(ivItemPictureHome); //        <- chính nó đó
            tvUserName.setText(data.getName());
            tvContent.setText(data.getContent());
        }
    }
}