package com.seu.srtp_imageedit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义功能项的适配器
 * Created by Administrator on 2016/7/28.
 */
public class Function_Item_Adapter extends ArrayAdapter<Function_Item>{
    private int mResourceId;

    public Function_Item_Adapter(Context context, int textViewResourceId, List<Function_Item> objects) {
        super(context, textViewResourceId, objects);
        mResourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Function_Item item = getItem(position); // 获取当前项的功能实例
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
        }else{
            view=convertView;
        }
        ImageView functionIcom = (ImageView) view.findViewById(R.id.opt_icon);
        TextView functionName = (TextView) view.findViewById(R.id.opt_name_text);
        functionIcom.setImageResource(item.getmIconId());
        functionName.setText(item.getmFuntionNameId());
        return view;
    }
}
