package vu.travelapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import vu.travelapp.R;

public class SplashActivity extends AppCompatActivity {
    private String TAG = SplashActivity.class.toString();
    private static final int SPLASH_SHOW_TIME = 4800;
    AccessTokenTracker accessTokenTracker;
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        this.updateWithToken(AccessToken.getCurrentAccessToken());
        Log.d(TAG, "onCreate: " + AccessToken.getCurrentAccessToken());
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
               if (currentAccessToken == null){


               }else ;
                Log.d(TAG, String.format("onCurrentAccessTokenChanged: \n%s \n%s", oldAccessToken, currentAccessToken));
            }
        };
    }
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            LoginManager.getInstance().logInWithReadPermissions(SplashActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));
            new BackgroundSplashTask().execute();
        }else {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}
