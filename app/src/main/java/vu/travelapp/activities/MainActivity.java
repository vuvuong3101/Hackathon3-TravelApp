package vu.travelapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vu.travelapp.R;
import vu.travelapp.fragments.HomeFragment;
import vu.travelapp.fragments.RankFragment;
import vu.travelapp.fragments.UploadFragment;
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;
import vu.travelapp.models.DataModel;
import vu.travelapp.models.ProfileModel;
import vu.travelapp.networks.pullData.DataModelJson;
import vu.travelapp.networks.pullData.GetAllDataModel;
import vu.travelapp.networks.pullData.RetrofitFactory;
import vu.travelapp.utils.FuzzyMatch;

public class MainActivity extends AppCompatActivity {
    public static String id_me;
    private static final String TAG = MainActivity.class.toString();
    private Toolbar toolbar;
    private RelativeLayout user;
    private ProfileModel profileModel;
    private SpaceNavigationView spaceNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hàm check internet RX
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Toast.makeText(MainActivity.this, "Connection: " + aBoolean, Toast.LENGTH_SHORT).show();
                    }
                });

        Init();
        progress();

    }

    private void progress() {
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestFaceboook();
                ScreenManager.replaceFragment(getSupportFragmentManager(), new UserFragment(), R.id.main);

            }
        });
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_newplace));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_fav));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_search));
        spaceNavigationView.changeCenterButtonIcon(R.drawable.ic_plus);
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                ScreenManager.replaceFragment2(getSupportFragmentManager(), new UploadFragment(), R.id.main);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                } else if (itemIndex == 1) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new RankFragment(), R.id.rl_content);

                } else if (itemIndex == 2) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                } else if (itemIndex == 3) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }
    private List<DataModel> dataModelList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO phần Search
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() > 1;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(final CharSequence charSequence) throws Exception {
                Log.d(TAG, "accept: " + charSequence);

                dataModelList = new ArrayList<>();
                GetAllDataModel getAllDataModel = RetrofitFactory.getInstance().create(GetAllDataModel.class);
                getAllDataModel.getDataModels().enqueue(new Callback<List<DataModelJson>>() {
                    @Override
                    public void onResponse(Call<List<DataModelJson>> call, Response<List<DataModelJson>> response) {
                        List<Integer> ratioList = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            DataModel dataModel = new DataModel();
                            dataModel.setName(response.body().get(i).getUsername());
                            dataModel.setImage(response.body().get(i).getImage());
                            dataModel.setUserid(response.body().get(i).getUserid());
                            dataModel.setDestination(response.body().get(i).getDestination());
                            dataModel.setContent(response.body().get(i).getContent());
                            dataModel.setLike(response.body().get(i).getLike());
                            dataModel.setId(response.body().get(i).get_id());
                            Log.d(TAG, "Lấy dữ liệu " + response.body().get(i).getDestination());
                            int ratio = FuzzyMatch.getRatio((String) charSequence, response.body().get(i).getDestination(), false);
                            Log.d("ratio ",":" + ratio);
                            ratioList.add(ratio);
                        }
                        for (int j = 0; j < ratioList.size(); j ++) {
                            if (Collections.max(ratioList) ==  ratioList.get(j)) {
                               //Todo chưa có layout
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DataModelJson>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Không kết nối", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, String.format("onFailure: %s", t.toString()));
                    }
                });
            }
        });
        return true;
    }

    private void Init() {
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        user = (RelativeLayout) findViewById(R.id.profile_user);
        setSupportActionBar(toolbar);
        ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
    }

    private void requestFaceboook() {
        GraphRequest request = GraphRequest.newMeRequest(
                LoginActivity.getCurrentAccessTokenFacebook(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            profileModel = new ProfileModel();
                            profileModel.setId(object.getString("id"));
                            id_me = profileModel.getId();
                            profileModel.setName(object.getString("name"));
                            profileModel.setUrlImage("https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
                            EventBus.getDefault().postSticky(profileModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, String.format("onCompleted: %s, %s, %s", profileModel.getName(), profileModel.getId(), profileModel.getUrlImage()));
                    }
                });
        request.executeAsync();
    }

    @Override
    public void onBackPressed() {
        spaceNavigationView.changeCurrentItem(0);
        super.onBackPressed();

    }


}
