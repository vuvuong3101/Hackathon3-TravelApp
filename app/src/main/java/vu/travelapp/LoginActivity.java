package vu.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import vu.travelapp.models.ProfileModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private static final long SPLASH_TIME_OUT = 10 ;
    CallbackManager callbackManager;
    Button loginButton;
    Button btnFB;
    ProfileModel profileModel = new ProfileModel();
    ImageView imageView;
    private AccessTokenTracker acessTokenTracker;
    private Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//
        acessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };


        updateWithToken(AccessToken.getCurrentAccessToken());

//        //====================
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
        ////==================
        this.setupUI();

        this.onClickSign();
    }
// auto login
    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown") );

        }
    }


    private void setupUI(){
        callbackManager = CallbackManager.Factory.create();
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_hometown") );
            }
        });
//        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_hometown"));

    }


    private void onClickSign(){
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
                            profileModel.setImageURL(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                            Log.d(TAG, "onCompleted: Đã lấy dữ liệu người dùng từ facebook" + profileModel.getLocation() + profileModel.getBirthday());

                            URL url = new URL(profileModel.getImageURL());
                            profileModel.setUrl(url);

                            //TODO: bug nè anh Hưng, bỏ comment đi chạy là crash nhé!
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            profileModel.setBitmap(bmp);

//                            SharedPreferences sharedPreferences = getSharedPreferences("TravelAppId", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("idfacebook",profileModel.getId());
//                            editor.commit();
                            //
                            EventBus.getDefault().postSticky(profileModel); //Chuyển dữ liệu profile sang Home Screen Activity
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(myIntent);
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
