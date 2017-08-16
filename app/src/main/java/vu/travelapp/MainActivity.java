package vu.travelapp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import vu.travelapp.fragments.HomeFragment;
import vu.travelapp.fragments.RankFragment;
import vu.travelapp.fragments.UploadFragment;
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private Toolbar toolbar;
    private BottomNavigation bottomNavigation;
    private RelativeLayout user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.replaceFragment(getSupportFragmentManager(), new UserFragment(), R.id.main);

            }
        });
        bottomNavigation.setDefaultSelectedIndex(0);

        bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int item, int i, boolean b) {
<<<<<<< HEAD
                if (item == R.id.bbn_item3) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new UploadFragment(), R.id.rl_content);
=======
                if (item == R.id.bbn_item1) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
>>>>>>> a23fd0458f58ceea8c9a9e88bf3972615efbb14b
                }
                if (item == R.id.bbn_item2) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new RankFragment(), R.id.rl_content);
                }
<<<<<<< HEAD
                if (item == R.id.bbn_item1) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
=======
                if (item == R.id.bbn_item3) {
                    ScreenManager.openFragment(getSupportFragmentManager(), new UploadFragment(), R.id.rl_content);
>>>>>>> a23fd0458f58ceea8c9a9e88bf3972615efbb14b
                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int item, int i, boolean b) {

            }
        });


    }


    private void Init() {
        bottomNavigation = (BottomNavigation) findViewById(R.id.bottomNavigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        user = (RelativeLayout) findViewById(R.id.profile_user);
        setSupportActionBar(toolbar);
        ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
    }
}
