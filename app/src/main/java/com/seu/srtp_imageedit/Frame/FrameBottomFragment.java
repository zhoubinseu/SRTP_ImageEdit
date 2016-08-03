package com.seu.srtp_imageedit.Frame;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.seu.srtp_imageedit.MyGalleryAdapter;
import com.seu.srtp_imageedit.R;

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
            R.drawable.eft_blend_normal_border_f15,
            R.drawable.icon_frame_1,R.drawable.icon_frame_2,
            R.drawable.icon_frame_3,R.drawable.icon_frame_4,
            R.drawable.icon_frame_5,R.drawable.icon_frame_6,
            R.drawable.icon_frame_7,R.drawable.icon_frame_8,
            R.drawable.icon_frame_9,R.drawable.icon_frame_10,
            R.drawable.icon_frame_11,R.drawable.icon_frame_12,
            R.drawable.icon_frame_13,R.drawable.icon_frame_14,
            R.drawable.icon_frame_15,
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
            R.drawable.frame_1,R.drawable.frame_2,
            R.drawable.frame_3,R.drawable.frame_4,
            R.drawable.frame_5,R.drawable.frame_6,
            R.drawable.frame_7,R.drawable.frame_8,
            R.drawable.frame_9,R.drawable.frame_10,
            R.drawable.frame_11,R.drawable.frame_12,
            R.drawable.frame_13,R.drawable.frame_14,
            R.drawable.frame_15,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_frame, container, false);

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
                if(position<=14){
                    mBitmap=FrameUtil.addFrameByOverLay(getActivity(), srcBitmapDrawable, mFrameTextureId[position]);
                }else if (position<=29){
                    mBitmap=FrameUtil.addFrameByStitch(getActivity(),srcBitmapDrawable,mFrameTextureId[position]);
                }
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
