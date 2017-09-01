package vu.travelapp.fragments;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
    private RelativeLayout close, share, rlMain;
    private EditText etDescription;
    private TextView tvDestination;
    private ProfileModel profileModel;
    final int REQUEST_TAKE_PHOTO = 1;
    final int REQUEST_CHOOSE_PHOTO = 2;
    private String urlImage;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String myDestination;

    private final String ACCESS_URL = "http://res.cloudinary.com/hanoi-university-of-science-and-technology/image/upload/q_auto:good/";

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
        ivUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto = false;
                ThuVien();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postToServer();
            }
        });
        tvDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }


    private void postToServer() {
        long timeUpload = new Date().getTime();
//        String timeUpload = DateFormat.getDateTimeInstance().format(new Date());
        String destination = tvDestination.getText().toString();
        String description = etDescription.getText().toString();
        if (destination.isEmpty() || description.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter data!", Toast.LENGTH_SHORT).show();
        } else {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://diphuot.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create())
                    .build();
            UploadService uploadService = retrofit.create(UploadService.class);
            uploadService.uploadPost
                    (new UploadRequestModel(profileModel.getId()
                            , profileModel.getUrlImage(), profileModel.getName()
                            , timeUpload, destination, description, 0))
                    .enqueue(new Callback<UploadRespondModel>() {
                        @Override
                        public void onResponse(Call<UploadRespondModel> call, Response<UploadRespondModel> response) {
                            String mess = response.body().getMessage();
                            Log.d("upload data: ", "success" + mess);
                        }

                        @Override
                        public void onFailure(Call<UploadRespondModel> call, Throwable t) {
                            Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            getActivity().onBackPressed();
        }
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
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
                    profileModel.setUrlImage(urlImage + ".jpg");
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
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);

    }

    private void ProcessUI() {
        Picasso.with(getContext()).load(profileModel.getUrlImage()).into(ivUploadImage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                
            }
        });
    }

    private void FindView(View view) {
        close = (RelativeLayout) view.findViewById(R.id.close);
        ivUploadImage = (ImageView) view.findViewById(R.id.iv_upload_image);
        share = (RelativeLayout) view.findViewById(R.id.send);
        etDescription = (EditText) view.findViewById(R.id.et_description);
        tvDestination = (TextView) view.findViewById(R.id.tv_destination);
        rlMain = (RelativeLayout) view.findViewById(R.id.rl_main);
    }

    @Subscribe(sticky = true)
    public void onReceivedProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
        Log.d(TAG, "onReceivedProfileModel: upload data");
    }
    private boolean choosePhoto = false;
    private Bitmap bitmap2;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                myDestination = (String) place.getName();
                tvDestination.setText(myDestination);
                ivUploadImage.setImageBitmap(bitmap2);
                Log.i("hoho", "Place: " + myDestination);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("", status.getStatusMessage());

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (resultCode == getActivity().RESULT_OK && choosePhoto == false) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                    try {
                        Uri imageUri = data.getData();
                        getLinkURL(imageUri);
                        InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmap2 = bitmap;
//                    String path = imageUri.getPath();
//                    ExifInterface exif = new ExifInterface(path);
//                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);
//                    Matrix matrix = new Matrix();
//                    matrix.postRotate(90);
//                    Bitmap sourcebitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                        ivUploadImage.setImageBitmap(bitmap);
                        rlMain.setVisibility(View.VISIBLE);
                        choosePhoto = true;
                        Log.d("chosse"," "+choosePhoto);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Picasso.with(getContext()).load(data.getData()).into(ivUploadImage);
            }
        }
    }
}
