package vu.travelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

<<<<<<< HEAD
=======
import vu.travelapp.Maps.MapsActivity;
import vu.travelapp.fragments.HomeFragment;
>>>>>>> 99930b6a9257884b49d3cdf60e486b8ceee3ad7c
import vu.travelapp.fragments.UserFragment;
import vu.travelapp.managers.ScreenManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private Toolbar toolbar;
    private RelativeLayout user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getSupportFragmentManager(), new UserFragment(), R.id.main);

            }
        });


    }
    private void Init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        user = (RelativeLayout) findViewById(R.id.profile_user);
        setSupportActionBar(toolbar);
        ScreenManager.replaceFragment(getSupportFragmentManager(), new HomeFragment(),R.id.rl_content );
    }
}
