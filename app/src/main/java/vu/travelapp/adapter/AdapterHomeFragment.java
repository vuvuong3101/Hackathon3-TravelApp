package vu.travelapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vu.travelapp.R;
import vu.travelapp.activities.MainActivity;
import vu.travelapp.fragments.CommentFragment;
import vu.travelapp.fragments.ImageDetailFragment;
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;
import vu.travelapp.models.DataModel;
import vu.travelapp.models.ProfileModel;
import vu.travelapp.networks.updateData.UpdateLikeRequestModel;
import vu.travelapp.networks.updateData.UpdateLikeResponseModel;
import vu.travelapp.networks.updateData.UpdateService;

/**
 * Created by ADMIN on 8/11/2017.
 */

public class AdapterHomeFragment extends RecyclerView.Adapter<AdapterHomeFragment.HomeModelViewHolder> {
    private static final String TAG = AdapterHomeFragment.class.toString();
    private List<DataModel> dataModels;
    private Context context;
    private final int view_item = 1;
    private final int view_pro = 0;
    private View.OnClickListener onClickListener;
    protected View view;
    private FragmentManager fragmentManager;

    public AdapterHomeFragment(List<DataModel> dataModels, Context context, FragmentManager fragmentManager) {
        this.dataModels = dataModels;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
        TextView tvContent, tvUserName, tvTime, tv_like;
        ImageView ivLike;
        TextView tvLike;
        TextView tvDestination;
        LinearLayout llLike, llComment, content;
        ImageView imageHomeFragment;
        ImageView avatarUser;
        private ProfileModel profileModel;
        boolean like = false;
        LinearLayout llChat;

        public HomeModelViewHolder(View itemView) {
            super(itemView);
            this.init(itemView);
            view = itemView;
        }

        private void init(View itemView) {
            ivItemPictureHome = (ImageView) itemView.findViewById(R.id.item_image);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvUserName = (TextView) itemView.findViewById(R.id.user_name);
            llLike = (LinearLayout) itemView.findViewById(R.id.like);
            llComment = (LinearLayout) itemView.findViewById(R.id.chat);
            imageHomeFragment = (ImageView) itemView.findViewById(R.id.item_image);
            avatarUser = (ImageView) itemView.findViewById(R.id.avatar_user);
            ivLike = (ImageView) itemView.findViewById(R.id.ic_like);
            tvLike = (TextView) itemView.findViewById(R.id.text_like);
            content = (LinearLayout) itemView.findViewById(R.id.item_content);
            tvTime = (TextView) itemView.findViewById(R.id.time);
            tvDestination = (TextView) itemView.findViewById(R.id.tv_destination_home);
            tv_like = (TextView) itemView.findViewById(R.id.text_like);
            llChat = (LinearLayout) itemView.findViewById(R.id.chat);

            avatarUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileModel = new ProfileModel();
                    profileModel.setName(dataModels.get(getAdapterPosition()).getName());
                    profileModel.setId(dataModels.get(getAdapterPosition()).getUserid());
                    profileModel.setUrlImage("https://graph.facebook.com/" + profileModel.getId() + "/picture?type=large");
                    EventBus.getDefault().postSticky(profileModel);
                    ScreenManager.replaceFragment2(fragmentManager, new UserFragment(), R.id.main);
                    Log.d(TAG, String.format("onClick: %s, %s, %s", profileModel.getName(), profileModel.getId(), profileModel.getUrlImage()));
                }
            });

            tvUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileModel = new ProfileModel();
                    profileModel.setName(dataModels.get(getAdapterPosition()).getName());
                    profileModel.setId(dataModels.get(getAdapterPosition()).getUserid());
                    profileModel.setUrlImage("https://graph.facebook.com/" + profileModel.getId() + "/picture?type=large");
                    EventBus.getDefault().postSticky(profileModel);
                    ScreenManager.replaceFragment2(fragmentManager, new UserFragment(), R.id.main);

                }
            });


        }

        public void setData(final DataModel dataModel, Context context) {
            // get thời gian hiện tại:
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            ////
            if (dataModel != null) {
                Picasso.with(context).load(dataModel.getImage()).into(ivItemPictureHome); //        <- chính nó đó
                tvUserName.setText(dataModel.getName());
                tvContent.setText(dataModel.getContent());
                tvTime.setText(dataModel.getTimeUpload());
                view.setTag(dataModel);
                tvDestination.setText(dataModel.getDestination());
                Picasso.with(context).load("https://graph.facebook.com/" + dataModels.get(getAdapterPosition()).getUserid() + "/picture?type=large").into(avatarUser);
            }

            llChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(dataModel);
                    ScreenManager.replaceFragment2(fragmentManager,new CommentFragment(),R.id.main);
                }
            });

            llLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateLike(dataModel);
                }
            });
            final MainActivity imageDetailFragment = (MainActivity) context;
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(dataModel);
                    ScreenManager.replaceFragment2(imageDetailFragment.getSupportFragmentManager(), new ImageDetailFragment(), R.id.main);
                }
            });
        }

        private void UpdateLike(DataModel data) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://diphuot.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create()).build();
            data.getId();
            UpdateService updateService = retrofit.create(UpdateService.class);
            if (like == false) {
                ivLike.setImageResource(R.drawable.ic_like_selected);
                updateService.updatelike(new UpdateLikeRequestModel(data.getId(), data.getLike() + 1)).enqueue(new Callback<UpdateLikeResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateLikeResponseModel> call, Response<UpdateLikeResponseModel> response) {
                        String message = response.body().getMessage();
                        Log.d("ấn like: ", "" + message);
                    }

                    @Override
                    public void onFailure(Call<UpdateLikeResponseModel> call, Throwable t) {
                    }
                });
                like = true;
            } else {
                ivLike.setImageResource(R.drawable.ic_like);
                updateService.updatelike(new UpdateLikeRequestModel(data.getId(), data.getLike())).enqueue(new Callback<UpdateLikeResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateLikeResponseModel> call, Response<UpdateLikeResponseModel> response) {
                        String message = response.body().getMessage();
                        Log.d("bỏ like: ", "" + message);
                    }

                    @Override
                    public void onFailure(Call<UpdateLikeResponseModel> call, Throwable t) {
                    }
                });
                like = false;
            }
        }
    }
}
