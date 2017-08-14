package vu.travelapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vu.travelapp.R;
import vu.travelapp.models.ProfileModel;
import vu.travelapp.networks.pushData.UploadRequestModel;
import vu.travelapp.networks.pushData.UploadRespondModel;
import vu.travelapp.networks.pushData.UploadService;

/**
 * Created by trongphuong1011 on 8/12/2017.
 */

public class UploadFragment extends Fragment {
    private static final String TAG = "Trong Phuong";
    private ImageView ivUploadImage;
    private Button btnUpload;
    private ImageView circleImageView;
    private TextView tvUserName;
    private EditText etDescription;
    private EditText etDestination;
    private ImageView ivclose, ivcamera;
    private ProfileModel profileModel;
    final int REQUEST_TAKE_PHOTO = 1;
    final int REQUEST_CHOOSE_PHOTO = 2;
    private LinearLayout llMain;
    private String urlImage;
    //    private String destination = profileModel.getName();
//    private String description;
    private final String ACCESS_URL = "http://res.cloudinary.com/hanoi-university-of-science-and-technology/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        EventBus.getDefault().register(this);
        FindView(view);

        ThuVien();

        setEvent();
        ProcessUI();
        return view;
    }

    private void setEvent() {
        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChupHinh();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postToServer();
            }
        });
    }

    private void postToServer() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diphuot.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create())
                .build();
        UploadService uploadService = retrofit.create(UploadService.class);
        uploadService.uploadPost
                (new UploadRequestModel(profileModel.getId()
                        , profileModel.getUrlImage(), profileModel.getName()
                        , "1/1/2017", "Đây là địa điểm", "Đây là mô tả"))
                .enqueue(new Callback<UploadRespondModel>() {
                    @Override
                    public void onResponse(Call<UploadRespondModel> call, Response<UploadRespondModel> response) {
                        String mess = response.body().getMessage();
                        Log.d("Hàm on Respond retrofit: ", "" + mess);
                    }

                    @Override
                    public void onFailure(Call<UploadRespondModel> call, Throwable t) {
                        Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getLinkURL(final Uri uri) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(uri);
                    Map config = new HashMap();
                    config.put("cloud_name", "hanoi-university-of-science-and-technology");
                    config.put("api_key", "265947211736866");
                    config.put("api_secret", "hSeTC462QKOMlpVqWI8f_DVeqDs");
                    Cloudinary cloudinary = new Cloudinary(config);
                    String id = cloudinary.randomPublicId();
                    cloudinary.uploader().upload(is, ObjectUtils.asMap("public_id", id));
                    cloudinary.url().generate(id + ".jpg");
                    urlImage = ACCESS_URL + id;
                    profileModel.setUrlImage(urlImage+".jpg");
                    Log.d("link: ", "" + urlImage + ".jpg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

    }

    private void ChupHinh() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void ThuVien() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);

    }

    private void ProcessUI() {
        tvUserName.setText(profileModel.getName());
        Picasso.with(getContext()).load(profileModel.getImageURL()).into(circleImageView);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void FindView(View view) {
<<<<<<< HEAD
        ivclose = (ImageView) view.findViewById(R.id.iv_close);
        ivUploadImage = (ImageView) view.findViewById(R.id.iv_upload_image);
        btnThuVien = (Button) view.findViewById(R.id.btn_gallery);
        btnCamera = (Button) view.findViewById(R.id.btn_camera);
=======
        ivclose = view.findViewById(R.id.iv_close);
        ivUploadImage = view.findViewById(R.id.iv_upload_image);
        btnUpload = view.findViewById(R.id.bt_upload);
        circleImageView = view.findViewById(R.id.avatar_upload);
        ivcamera = view.findViewById(R.id.iv_camera);
        tvUserName = view.findViewById(R.id.username_upload);
        etDescription = view.findViewById(R.id.et_description);
        llMain = view.findViewById(R.id.ll_main);
        etDescription = view.findViewById(R.id.et_destination);
    }

    @Subscribe(sticky = true)
    public void onReceivedProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
        Log.d(TAG, "onReceivedProfileModel: upload data");
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> b4040669a9edde84b971423f67bc922e06e1a8b0
=======
>>>>>>> b4040669a9edde84b971423f67bc922e06e1a8b0
=======
>>>>>>> b4040669a9edde84b971423f67bc922e06e1a8b0
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    final Uri imageUri = data.getData();
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivUploadImage.setImageBitmap(bitmap);

                    getLinkURL(imageUri);

                    llMain.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Picasso.with(getContext()).load(data.getData()).into(ivUploadImage);
            }
        }
    }

}
