package com.seu.srtp_imageedit.Frame;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.seu.srtp_imageedit.EditImageActivity;
import com.seu.srtp_imageedit.MyGalleryAdapter;
import com.seu.srtp_imageedit.R;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FrameBottomFragment extends Fragment{
    private static final String ICON_FOLDER="frame/icon";
    private static final String TEXTURE_FOLDER="frame/texture";

    private ImageButton mImageButton;
    private Gallery mGallery;//显示在悬浮框中
    private MyGalleryAdapter adapter;
    private ImageView mImageView;//碎片所在活动的ImageView
    private Bitmap mBitmap;//处理之后的图片
    private Drawable srcBitmapDrawable;//未处理的图片


    private int[] mFrameIconId=new int[]{
            R.drawable.eft_blend_normal_border_f1, R.drawable.eft_blend_normal_border_f2,
            R.drawable.eft_blend_normal_border_f3, R.drawable.eft_blend_normal_border_f4,
            R.drawable.eft_blend_normal_border_f5, R.drawable.eft_blend_normal_border_f6,
            R.drawable.eft_blend_normal_border_f7, R.drawable.eft_blend_normal_border_f8,
            R.drawable.eft_blend_normal_border_f9, R.drawable.eft_blend_normal_border_f10,
            R.drawable.eft_blend_normal_border_f11, R.drawable.eft_blend_normal_border_f12,
            R.drawable.eft_blend_normal_border_f13, R.drawable.eft_blend_normal_border_f14,
            R.drawable.eft_blend_normal_border_f15
    };
    private int[] mFrameTextureId=new int[]{
            R.drawable.blend_normal_border_f1,R.drawable.blend_normal_border_f2,
            R.drawable.blend_normal_border_f3,R.drawable.blend_normal_border_f4,
            R.drawable.blend_normal_border_f5,R.drawable.blend_normal_border_f6,
            R.drawable.blend_normal_border_f7,R.drawable.blend_normal_border_f8,
            R.drawable.blend_normal_border_f9,R.drawable.blend_normal_border_f10,
            R.drawable.blend_normal_border_f11,R.drawable.blend_normal_border_f12,
            R.drawable.blend_normal_border_f13,R.drawable.blend_normal_border_f14,
            R.drawable.blend_normal_border_f15,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frame_bottom_fragment, container, false);

        mImageView= (ImageView) getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable=mImageView.getDrawable();//原图

        mImageButton=(ImageButton)v.findViewById(R.id.choose_frame_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"choose_frame",Toast.LENGTH_SHORT).show();
                //点击弹出悬浮框
                showPopUpWindow(v);
            }
        });

        return v;
    }

    /**
     * 显示悬浮框及其中的控件
     * @param v
     */
    private void showPopUpWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.frame_gallery,null,false);
        mGallery=(Gallery)view.findViewById(R.id.choose_frame_gallery);
        adapter=new MyGalleryAdapter(getActivity(),mFrameIconId);
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"将加载"+mFrameTextureId[position],Toast.LENGTH_SHORT).show();
                //mImageView.setDrawingCacheEnabled(true);//确保可以使用getDrawingCache()获得图像
                mBitmap=FrameUtil.addFrame(getActivity(),srcBitmapDrawable,mFrameTextureId[position]);
                //mImageView.setDrawingCacheEnabled(false);//清空画图缓存区,这样每次重新加载图像
                mImageView.setImageBitmap(mBitmap);
            }
        });
        final PopupWindow popupWindow=new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//必须设置背景，否则悬浮框不会消失
        popupWindow.showAsDropDown(mImageButton, 0, 5);
    }

}
