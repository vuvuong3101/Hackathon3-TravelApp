package vu.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import vu.travelapp.activities.LoginActivity;
import vu.travelapp.activities.MainActivity;
import vu.travelapp.models.ProfileModel;

public class Intro extends AppCompatActivity {
    private static final String TAG = Intro.class.toString();
    private static final long SPLASH_TIME_OUT = 10;
    CallbackManager callbackManager;
    LinearLayout loginButton;
    Button btnFB;
    ProfileModel profileModel = new ProfileModel();
    ImageView imageView;
     AccessTokenTracker acessTokenTracker;
    AccessToken currentAccessToken =  AccessToken.getCurrentAccessToken();

    public static AccessToken getCurrentAccessTokenFacebook() {
        return AccessToken.getCurrentAccessToken();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setupUI();
        this.onClickSign();
        if (currentAccessToken == null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intro.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 0);

        }else {
            updateWithToken(currentAccessToken);
        }
        acessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
                onClickSign();
                Log.d(TAG, "onCurrentAccessTokenChanged: đã đăng nhập lại với facebook");
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // auto login
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            LoginManager.getInstance().logInWithReadPermissions(Intro.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));
        }else {

        }
    }

    private void setupUI() {
        callbackManager = CallbackManager.Factory.create();
//        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));
    }

    private void onClickSign() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {         //TODO: set data khi đăng nhập vào facebook và chuyển activity
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d(TAG, "onCompleted: " + "Login Success");
                            profileModel.setLocation(object.getString("hometown"));
                            profileModel.setBirthday(object.getString("birthday"));
                            profileModel.setId(object.getString("id"));
                            profileModel.setName(object.getString("name"));
                            profileModel.setUrlImage(object.getJSONObject("picture").getJSONObject("data").getString("url"));

                            URL url = new URL(profileModel.getUrlImage());
                            profileModel.setUrl(url);

                            //TODO: bug nè anh Hưng, bỏ comment đi chạy là crash nhé!
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            profileModel.setBitmap(bmp);

//                            SharedPreferences sharedPreferences = getSharedPreferences("TravelAppId", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("idfacebook",profileModel.getId());
//                            editor.commit();

                            EventBus.getDefault().postSticky(profileModel); //Chuyển dữ liệu profile sang Home Screen Activity
                            Log.d(TAG, "onCompleted: Đã lấy dữ liệu người dùng từ facebook" + profileModel.getLocation() + profileModel.getBirthday());
                            Intent intent = new Intent(Intro.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.type(large), birthday, hometown");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: " + "Cancel FB !!!! :(((((");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(), "Đăng nhập thất bại, kiểm tra kết nối !", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onError: " + " Cant login FB ");
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);                  //TODO:Gọi lại hàm request để đổ dữ liệu
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
