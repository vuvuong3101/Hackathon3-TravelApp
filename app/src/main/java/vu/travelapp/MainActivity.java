package vu.travelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import vu.travelapp.fragments.HomeFragment;
import vu.travelapp.fragments.RankFragment;
import vu.travelapp.fragments.UploadFragment;
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;
import vu.travelapp.models.ProfileModel;

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
        Init();
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
                ScreenManager.replaceFragment2(getSupportFragmentManager(), new UploadFragment(),R.id.main);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0 ){
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                }else if (itemIndex == 1) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new RankFragment(), R.id.rl_content);

                }else if (itemIndex == 2){
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                }else if (itemIndex ==3) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }


    private void Init() {
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        user = (RelativeLayout) findViewById(R.id.profile_user);
        setSupportActionBar(toolbar);
        ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
    }
    private void requestFaceboook(){
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
                            profileModel.setUrlImage("https://graph.facebook.com/"+ object.getString("id") +"/picture?type=large");
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
