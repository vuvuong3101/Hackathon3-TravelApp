package vu.travelapp.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import vu.travelapp.R;
import vu.travelapp.models.ProfileModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private static final long SPLASH_TIME_OUT = 10;
    CallbackManager callbackManager;
    LinearLayout loginButton;
    Button btnFB;
    ProfileModel profileModel = new ProfileModel();
    ImageView imageView;
     AccessTokenTracker acessTokenTracker;

    public static AccessToken getCurrentAccessTokenFacebook() {
        return AccessToken.getCurrentAccessToken();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        acessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
                //onClickSign();
                Log.d(TAG, String.format("onCurrentAccessTokenChanged: đã đăng nhập lại với facebook\n%s\n%s", oldAccessToken, newAccessToken));
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());

        // lấy ra keyhash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "vu.travelapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }
        this.setupUI();
        this.onClickSign();
        Log.d(TAG, "onCreate: Đã khởi tạo Login Activity");
    }

    // auto login
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));
        }
    }

    private void setupUI() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LinearLayout) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.clickanimation));
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));
            }
        });
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

                            EventBus.getDefault().postSticky(profileModel); //Chuyển dữ liệu profile sang Home Screen Activity
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            Log.d(TAG, "onCompleted: Đã lấy dữ liệu người dùng từ facebook" + profileModel.getLocation() + profileModel.getBirthday());
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
