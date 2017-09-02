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
 * Created by trongphuong1011 on 8/20/2017.
 */

public class AdapterSearchFragment extends RecyclerView.Adapter<AdapterSearchFragment.SearchModelViewHolder> {
    private List<DataModel> dataModels;
    private Context context;
    private View.OnClickListener onClickListener;
    View view;

    public AdapterSearchFragment(List<DataModel> dataModel, Context context) {
        this.dataModels = dataModel;
        this.context = context;
    }

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public SearchModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_search, viewGroup, false);
        view.setOnClickListener(onClickListener);
        return new SearchModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchModelViewHolder searchModelViewHolder, int i) {
        searchModelViewHolder.setData(this.dataModels.get(i), context);
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class SearchModelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSearchImage;
        TextView tvDiaDiem, desSearch;

        public SearchModelViewHolder(View itemView) {
            super(itemView);
            init(itemView);
            view = itemView;
        }
        private void init(View itemView) {
            ivSearchImage = (ImageView) itemView.findViewById(R.id.iv_search_image);
            tvDiaDiem = (TextView) itemView.findViewById(R.id.tv_dia_diem);
            desSearch = (TextView) itemView.findViewById(R.id.content_search);
        }

        private void setData(DataModel data, Context context) {
            if(data != null) {
                Picasso.with(context).load(data.getImage()).placeholder(R.drawable.dafault).into(ivSearchImage);
                tvDiaDiem.setText(data.getDestination());
                desSearch.setText(data.getContent());
                view.setTag(data);
            }
        }
    }
}
