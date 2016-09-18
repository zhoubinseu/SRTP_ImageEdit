package com.seu.srtp_imageedit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * 添加待处理图片的Fragment
 * Created by 周彬 on 2016/7/18.
 */
public class AddImageFragment extends Fragment{
    private ImageButton mChooseImageButton;
    private TextView mChooseImageTextView;
    private ImageButton mTakePhotoImageButton;
    private TextView mTakePhotoTextView;
    private ImageView mImageView;

    private static final int REQUEST_ALBUM=0;
    private static final int REQUEST_CAMERA=1;
    //拍摄的照片的存储文件夹
    private File PHOTO_DIR = null;
    //拍摄的照片的绝对路径
    private String photoPath=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为拍摄的照片创建一个图片文件夹
        //PHOTO_DIR=getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        PHOTO_DIR=new File(Environment.getExternalStorageDirectory() + "/SRTP_ImageEdit/CameraCache");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_image_fragment,container,false);

        mImageView=(ImageView)v.findViewById(R.id.add_image_image_view);

        mChooseImageButton=(ImageButton)v.findViewById(R.id.choose_image_image_button);
        mChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image.ChooseImage(REQUEST_ALBUM,AddImageFragment.this);
            }
        });
        mChooseImageTextView=(TextView)v.findViewById(R.id.choose_image_text_view);
        mChooseImageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Image.ChooseImage(REQUEST_ALBUM,AddImageFragment.this);
            }
        });

        mTakePhotoImageButton=(ImageButton)v.findViewById(R.id.take_photo_image_button);
        mTakePhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPath=Image.TakePhoto(REQUEST_CAMERA,AddImageFragment.this,PHOTO_DIR).getAbsolutePath();
            }
        });
        mTakePhotoTextView=(TextView)v.findViewById(R.id.take_photo_text_view);
        mTakePhotoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPath=Image.TakePhoto(REQUEST_CAMERA,AddImageFragment.this,PHOTO_DIR).getAbsolutePath();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_ALBUM){
            //将选中图片的路径传递给编辑界面
            Uri uri=data.getData();
            //String path= Image.getRealPathFromUri(getActivity(), uri);
            String path=Image.getImagePathFromUri(getActivity(),uri);
            startActivity(ShowImageActivity.newIntent(getActivity(), path));
        }else if(requestCode==REQUEST_CAMERA){
            startActivity(ShowImageActivity.newIntent(getActivity(), photoPath));
        }
    }

}
