package vu.travelapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import vu.travelapp.R;

/**
 * Created by trongphuong1011 on 8/12/2017.
 */

public class UploadFragment extends Fragment {
    private ImageView ivUploadImage;
    private Button btnThuVien, btnCamera;
    private ImageView ivclose;
    final int REQUEST_TAKE_PHOTO = 1;
    final int REQUEST_CHOOSE_PHOTO = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        FindView(view);
        setEvent();
        ProcessUI();
        return view;
    }

    private void setEvent() {
        btnThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuVien();
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChupHinh();
            }
        });
    }

    private void ChupHinh(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);
    }

    private void ThuVien(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    private void ProcessUI() {
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void FindView(View view) {
        ivclose = view.findViewById(R.id.iv_close);
        ivUploadImage = view.findViewById(R.id.iv_upload_image);
        btnThuVien = view.findViewById(R.id.btn_gallery);
        btnCamera = view.findViewById(R.id.btn_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivUploadImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(requestCode == REQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivUploadImage.setImageBitmap(bitmap);
            }
        }
    }
}
