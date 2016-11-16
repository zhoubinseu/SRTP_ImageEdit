package com.seu.srtp_imageedit.Rotate;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.seu.srtp_imageedit.Constant;
import com.seu.srtp_imageedit.R;

/**
 * 旋转操作的操作工具栏
 * Created by 周彬 on 2016/7/30.
 */
public class RotateBottomFragment extends Fragment implements View.OnClickListener{
    private ImageButton mLeftRotateButton;//左转
    private ImageButton mRightRotateButton;//右转
    private ImageButton mXRotateButton;//水平翻转
    private ImageButton mYRotateButton;//垂直翻转
    private ImageView mImageView;//碎片所在活动的ImageView
    private Drawable srcBitmapDrawable;//待处理图片的Drawable
    private Bitmap mBitmap;//处理之后的图片

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_rotate,container,false);
        //每次进入旋转界面时，将Constant中的旋转计数器设为0
        Constant.getInstance().setRotateCounter(0);

        mLeftRotateButton=(ImageButton)v.findViewById(R.id.left_rotate_button);
        mRightRotateButton=(ImageButton)v.findViewById(R.id.right_rotate_button);
        mXRotateButton=(ImageButton)v.findViewById(R.id.x_rotate_button);
        mYRotateButton=(ImageButton)v.findViewById(R.id.y_rotate_button);
        mLeftRotateButton.setOnClickListener(this);
        mRightRotateButton.setOnClickListener(this);
        mXRotateButton.setOnClickListener(this);
        mYRotateButton.setOnClickListener(this);

        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable = mImageView.getDrawable();
        //计算当前图片的尺寸及旋转后的尺寸并保存
        init(srcBitmapDrawable);

        return v;
    }
    //当旋转界面进入后台，或被销毁时，重置旋转计数器
    @Override
    public void onPause() {
        super.onPause();
        Constant.getInstance().setRotateCounter(0);
    }

    @Override
    public void onClick(View v) {
        mImageView.setDrawingCacheEnabled(true);//确保可以使用getDrawingCache()获得图像
        switch (v.getId()){
            case R.id.left_rotate_button:
                //Toast.makeText(getActivity(),"left rotate",Toast.LENGTH_SHORT).show();
                //旋转计数器-1
                Constant.getInstance().setRotateCounter(Constant.getInstance().getRotateCounter()-1);
                mBitmap=RotateUtil.Rotate(mImageView.getDrawingCache(),R.id.left_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.right_rotate_button:
                //Toast.makeText(getActivity(),"right rotate",Toast.LENGTH_SHORT).show();
                //旋转计数器+1
                Constant.getInstance().setRotateCounter(Constant.getInstance().getRotateCounter()+1);
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
                //mBitmap=RotateUtil.Rotate(srcBitmapDrawable,R.id.y_rotate_button);
                mImageView.setImageBitmap(mBitmap);
                break;
            default:
                break;
        }
        mImageView.setDrawingCacheEnabled(false);//清空画图缓存区,这样保证连续旋转时每次重新加载图像
    }

    /**
     * 计算图片旋转处理过程中的尺寸
     * @param srcBitmapDrawable 待处理的图片Drawable对象
     */
    private static void init(Drawable srcBitmapDrawable){
        /*
        若原图height小于屏幕宽度，表明旋转90度后屏幕能容纳
        若原图height大于屏幕宽度，旋转90度后新的宽会超出，需要按照比例缩小图片
         */
        Constant.getInstance().setRotateSrcW( srcBitmapDrawable.getIntrinsicWidth());
        Constant.getInstance().setRotateSrcH(srcBitmapDrawable.getIntrinsicHeight());
        if(Constant.getInstance().getRotateSrcH()< Constant.getInstance().getWindowWidth()){
            Constant.getInstance().setRotateNewW(Constant.getInstance().getRotateSrcH());
            Constant.getInstance().setRotateNewH(Constant.getInstance().getRotateSrcW());
        }else{
            Constant.getInstance().setRotateNewW(Constant.getInstance().getWindowWidth()-1);
            Constant.getInstance().setRotateNewH(Constant.getInstance().getRotateNewW()*
                    Constant.getInstance().getRotateSrcW()/Constant.getInstance().getRotateSrcH());
        }
    }
}
