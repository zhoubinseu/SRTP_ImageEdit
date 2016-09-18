package com.seu.srtp_imageedit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * gallery显示图片和文字
 * Created by 周彬 on 2016/8/4.
 */
public class ImageTextGalleryAdapter extends BaseAdapter{
    private Context mContext;
    private int[] mImageId;
    private int[] mTextId;

    public ImageTextGalleryAdapter(){
    }

    public ImageTextGalleryAdapter(Context context,int[] image,int[] text){
        mContext=context;
        mImageId=image;
        mTextId=text;
    }

    @Override
    public int getCount() {
        return mImageId.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageId[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            //View的findViewById()方法也是比较耗时的，因此需要考虑中调用一次，之后用
            //View的getTag()来获取这个ViewHolder对象
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.image_text_gallery_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
            holder.textView = (TextView) convertView.findViewById(R.id.gallery_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(mImageId[position]);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);      //设置imgView的缩放类型
        holder.textView.setText(mTextId[position]);

        return convertView;
    }

    //增加这样一个静态类，缓存已经获得的控件实例，这样不用每次都重新加载
    final class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

}
