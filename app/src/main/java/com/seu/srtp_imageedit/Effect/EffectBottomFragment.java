package com.seu.srtp_imageedit.Effect;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.effect.Effect;
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

import com.seu.srtp_imageedit.Frame.FrameUtil;
import com.seu.srtp_imageedit.ImageGalleryAdapter;
import com.seu.srtp_imageedit.ImageTextGalleryAdapter;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class EffectBottomFragment extends Fragment{
    private ImageButton mImageButton;
    private ImageView mImageView;
    private Bitmap mBitmap;//处理后的图像
    private Gallery mGallery;//显示在悬浮框中
    private ImageTextGalleryAdapter adapter;
    private Drawable srcBitmapDrawable;//未处理的图片

    private int[] mImageId=new int[]{
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
    };

    private int[] mTextId=new int[]{
            R.string.effect_sketch,R.string.effect_blur,
            R.string.effect_binary,R.string.effect_black_white,
            R.string.effect_emboss,R.string.effect_negative,
            R.string.effect_ice, R.string.effect_neon,
            R.string.effect_nostalgic, R.string.effect_light,
            R.string.effect_poster,R.string.effect_feather,
            R.string.effect_oil,R.string.effect_molten,
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_effect,container,false);

        mImageButton=(ImageButton)v.findViewById(R.id.choose_effect_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpWindow(v);
            }
        });

        mImageView= (ImageView) getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable=mImageView.getDrawable();

        return v;
    }

    /**
     * 显示悬浮框及其中的控件
     * @param v
     */
    private void showPopUpWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.effect_gallery, null, false);
        mGallery=(Gallery)view.findViewById(R.id.choose_effect_gallery);
        adapter=new ImageTextGalleryAdapter(getActivity(),mImageId,mTextId);
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //处理
                mBitmap=EffectUtil.process_effect(mTextId[position],srcBitmapDrawable);
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
