package vu.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import vu.travelapp.models.ProfileModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button btnFB;
    ProfileModel profileModel = new ProfileModel();
    ImageView imageView;
    private Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setupUI();

        this.onClickSign();
    }

    private void setupUI(){
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

    }


    private void onClickSign(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {         //TODO: set data khi đăng nhập vào facebook và chuyển activity
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            profileModel.setId(object.getString("id"));
                            profileModel.setName(object.getString("name"));
                            profileModel.setImageURL(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                            Log.d(TAG, "onCompleted: Đã lấy dữ liệu người dùng từ facebook");

                            URL url = new URL(profileModel.getImageURL());
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            profileModel.setUrl(url);

                            //TODO: bug nè anh Hưng, bỏ comment đi chạy là crash nhé!
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            profileModel.setBitmap(bmp);

                            //

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
                parameters.putString("fields", "id,name,email,picture.type(large)");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);                  //TODO:Gọi lại hàm request để đổ dữ liệu
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
