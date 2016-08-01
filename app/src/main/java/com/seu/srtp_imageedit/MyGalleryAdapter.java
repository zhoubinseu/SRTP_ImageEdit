package com.seu.srtp_imageedit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MyGalleryAdapter extends BaseAdapter{
    private Context mContext;
    private int[] mFrameId;

    public MyGalleryAdapter(){
    }

    public MyGalleryAdapter(Context context,int[] data){
        mContext=context;
        mFrameId=data;
    }

    @Override
    public int getCount() {
        return mFrameId.length;
    }

    @Override
    public Object getItem(int position) {
        return mFrameId[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView frameImage = new ImageView(mContext);
        frameImage.setBackgroundColor(0x00000000);
        frameImage.setImageResource(mFrameId[position]);         //创建一个ImageView
        frameImage.setScaleType(ImageView.ScaleType.FIT_XY);      //设置imgView的缩放类型
        frameImage.setLayoutParams(new Gallery.LayoutParams(200, 200));    //为imgView设置布局参数
        //以下2行适用低版本，需增加attrs.xml
        //TypedArray typedArray = mContext.obtainStyledAttributes(R.styleable.Gallery);
        //frameImage.setBackgroundResource(typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0));
        return frameImage;
    }
}
