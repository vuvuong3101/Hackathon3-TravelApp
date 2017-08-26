package vu.travelapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import vu.travelapp.R;
import vu.travelapp.fragments.HomeFragment;
import vu.travelapp.fragments.RankFragment;
import vu.travelapp.fragments.SearchFragment;
import vu.travelapp.fragments.UploadFragment;
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;
import vu.travelapp.models.DataModel;
import vu.travelapp.models.ProfileModel;

public class MainActivity extends AppCompatActivity {
    public static String id_me;
    private static final String TAG = MainActivity.class.toString();
    private Toolbar toolbar;
    private RelativeLayout user;
    private ProfileModel profileModel;
    private SpaceNavigationView spaceNavigationView;
    private SearchView searchView;


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
                .debounce(250, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() > 1;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(final CharSequence charSequence) throws Exception {
                Log.d(TAG, "accept: " + charSequence);
                EventBus.getDefault().postSticky(charSequence);
                ScreenManager.openFragment(getSupportFragmentManager(), new SearchFragment(), R.id.rl_content);
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
        hideSoftKeyboard(this);
        super.onBackPressed();


    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


}
