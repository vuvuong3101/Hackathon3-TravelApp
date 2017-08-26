package vu.travelapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import vu.travelapp.R;
import vu.travelapp.models.DataModel;

/**
 * Created by ADMIN on 8/17/2017.
 */

public class AdapterPostedProfileFragment extends RecyclerView.Adapter<AdapterPostedProfileFragment.PostedProfileFragmentViewHolder> {
    protected Context context;
    protected List<DataModel> dataModels;
    protected View.OnClickListener onClickListener;

    public AdapterPostedProfileFragment(Context context, List<DataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }

    public void setOnItemClick(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public PostedProfileFragmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_posted_profile_fragment, viewGroup, false);
        view.setOnClickListener(this.onClickListener);
        return new PostedProfileFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostedProfileFragmentViewHolder postedProfileFragmentViewHolder, int i) {
        postedProfileFragmentViewHolder.setData(dataModels.get(i));
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class PostedProfileFragmentViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivPosted;
        protected View view;

        public PostedProfileFragmentViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ivPosted = (ImageView) this.view.findViewById(R.id.iv_posted);
        }


        public void setData(DataModel data){
            Picasso.with(context).load(data.getImage()).into(ivPosted);
            view.setTag(data);
        }
    }
}
