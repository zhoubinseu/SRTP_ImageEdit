package com.seu.srtp_imageedit.Filter;

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
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.seu.srtp_imageedit.Effect.EffectUtil;
import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.ImageTextGalleryAdapter;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class FilterBottomFragment extends Fragment{
    private ImageButton mFilterButton;
    private ImageView mImageView;//Fragment所在的Activity的ImageView
    private Bitmap mBitmap;//处理后的图片
    private Drawable srcBitmapDrawable;//未处理的图片内容
    private Gallery mGallery;
    private ImageTextGalleryAdapter adapter;

    private int[] mImageId=new int[]{
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
    };
    private int[] mTextId=new int[]{
            R.string.num_1,R.string.num_2,
            R.string.num_3,R.string.num_4,
            R.string.num_5,R.string.num_6,
            R.string.num_7,R.string.num_8,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_filter,container,false);

        mFilterButton=(ImageButton)v.findViewById(R.id.choose_filter_button);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpWindow(v);
            }
        });
        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable=mImageView.getDrawable();

        return v;
    }

    /**
     * 显示悬浮框
     * @param v
     */
    private void showPopUpWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.filter_gallery, null, false);
        mGallery=(Gallery)view.findViewById(R.id.choose_filter_gallery);
        adapter=new ImageTextGalleryAdapter(getActivity(),mImageId,mTextId);
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //处理
                mBitmap=FilterUtil.process_filter(mTextId[position],srcBitmapDrawable);
                mImageView.setImageBitmap(mBitmap);
                //Toast.makeText(getActivity(), "CHOOSE"+mTextId[position], Toast.LENGTH_SHORT).show();
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
        popupWindow.showAsDropDown(mFilterButton, 0, 5);
    }

}
