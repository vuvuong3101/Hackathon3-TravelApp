package vu.travelapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vu.travelapp.R;
import vu.travelapp.networks.pullData.CommentJSONModel;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public class AdapterCommentFragment extends RecyclerView.Adapter<AdapterCommentFragment.CommentModelViewHolder> {
    private List<CommentJSONModel> commentJSONModelList;
    private Context context;
    private View.OnClickListener onClickListener;
    View view;
    private FragmentManager fragmentManager;
    private int countCM;

    public int getCountCM() {
        return countCM;
    }

    public AdapterCommentFragment(List<CommentJSONModel> commentJSONModelList, Context context, FragmentManager fragmentManager) {
        this.commentJSONModelList = commentJSONModelList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public CommentModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_comment, viewGroup, false);
        view.setOnClickListener(onClickListener);
        EventBus.getDefault().postSticky(commentJSONModelList);
        return new AdapterCommentFragment.CommentModelViewHolder(view);
    }

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(CommentModelViewHolder commentModelViewHolder, int i) {
        commentModelViewHolder.setData(this.commentJSONModelList.get(i),context);
    }

    @Override
    public int getItemCount() {
        return countCM = commentJSONModelList.size();
    }

    public class CommentModelViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvSentence;
        public CommentModelViewHolder(View itemView) {
            super(itemView);
            this.init(itemView);
            view = itemView;
        }

        private void init(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.user_name_comment);
            tvSentence = (TextView) itemView.findViewById(R.id.tv_comments);
        }

        private void setData(CommentJSONModel data, Context context) {
            if (data != null) {
                tvName.setText(data.getName());
                tvSentence.setText(data.getSentence());
            }
        }
    }
}
