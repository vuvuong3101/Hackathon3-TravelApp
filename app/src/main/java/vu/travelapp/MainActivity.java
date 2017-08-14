package vu.travelapp;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import vu.travelapp.fragments.HomeFragment;
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
        bottomNavigation.setDefaultSelectedIndex(2);

        bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int item, int i, boolean b) {
                if(item == R.id.bbn_item3){
                    ScreenManager.openFragment(getSupportFragmentManager(), new UploadFragment(),R.id.main);
                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int item, int i, boolean b) {

            }
        });


    }



    private void Init(){
        bottomNavigation = (BottomNavigation) findViewById(R.id.bottomNavigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        user = (RelativeLayout) findViewById(R.id.profile_user);
        setSupportActionBar(toolbar);
        ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.rl_content);
    }
}
