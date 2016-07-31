package com.seu.srtp_imageedit.Rotate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.seu.srtp_imageedit.R;

/**
 * 旋转操作的操作工具栏
 * Created by Administrator on 2016/7/30.
 */
public class RotateBottomFragment extends Fragment implements View.OnClickListener{
    private ImageButton mLeftRotateButton;//左转
    private ImageButton mRightRotateButton;//右转
    private ImageButton mXRotateButton;//水平翻转
    private ImageButton mYRotateButton;//垂直翻转
    private ImageView mImageView;//碎片所在活动的ImageView
    private Bitmap mBitmap;//处理之后的图片

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.rotate_bottom_fragment,container,false);

        mLeftRotateButton=(ImageButton)v.findViewById(R.id.left_rotate_button);
        mRightRotateButton=(ImageButton)v.findViewById(R.id.right_rotate_button);
        mXRotateButton=(ImageButton)v.findViewById(R.id.x_rotate_button);
        mYRotateButton=(ImageButton)v.findViewById(R.id.y_rotate_button);
        mLeftRotateButton.setOnClickListener(this);
        mRightRotateButton.setOnClickListener(this);
        mXRotateButton.setOnClickListener(this);
        mYRotateButton.setOnClickListener(this);

        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);

        return v;
    }

    @Override
    public void onClick(View v) {
        mImageView.setDrawingCacheEnabled(true);//确保可以使用getDrawingCache()获得图像
        switch (v.getId()){
            case R.id.left_rotate_button:
                //Toast.makeText(getActivity(),"left rotate",Toast.LENGTH_SHORT).show();
                mBitmap=RotateUtil.Rotate(mImageView.getDrawingCache(),R.id.left_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.right_rotate_button:
                //Toast.makeText(getActivity(),"right rotate",Toast.LENGTH_SHORT).show();
                mBitmap=RotateUtil.Rotate(mImageView.getDrawingCache(),R.id.right_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.x_rotate_button:
                //Toast.makeText(getActivity(),"x rotate",Toast.LENGTH_SHORT).show();
                mBitmap=RotateUtil.Rotate(mImageView.getDrawingCache(),R.id.x_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.y_rotate_button:
                //Toast.makeText(getActivity(),"y rotate",Toast.LENGTH_SHORT).show();
                mBitmap=RotateUtil.Rotate(mImageView.getDrawingCache(),R.id.y_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            default:
                break;
        }
        mImageView.setDrawingCacheEnabled(false);//清空画图缓存区,这样保证连续旋转时每次重新加载图像
    }
}
