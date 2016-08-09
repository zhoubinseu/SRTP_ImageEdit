package com.seu.srtp_imageedit.Adjust;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.seu.srtp_imageedit.Frame.FrameUtil;
import com.seu.srtp_imageedit.Function_Item;
import com.seu.srtp_imageedit.Function_Item_Adapter;
import com.seu.srtp_imageedit.ImageGalleryAdapter;
import com.seu.srtp_imageedit.ImageTextGalleryAdapter;
import com.seu.srtp_imageedit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AdjustBottomFragment extends Fragment{

    private ImageButton mChooseAdjustButton;
    private ImageView mImageView;//该碎片所在的活动的ImageView
    private Bitmap mBitmap;//处理后的图片

    private List<Function_Item> mAdjustFunctionList=new ArrayList<Function_Item>();//图片调整功能列表
    private ListView mListView;
    private Function_Item_Adapter adapter;

    private float mLuminance;//亮度
    private float mHue;//色调
    private float mSaturation;//饱和度
    private float mContrast;//对比度
    private float mTransparency;//透明度
    private float mTemperature;//色温

    private void initFunctionList(){
        Function_Item luminance_item=new Function_Item(R.string.adjust_luminance,R.drawable.ic_luminance_button);
        mAdjustFunctionList.add(luminance_item);
        Function_Item hue_item=new Function_Item(R.string.adjust_hue,R.drawable.ic_hue_button);
        mAdjustFunctionList.add(hue_item);
        Function_Item saturation_item=new Function_Item(R.string.adjust_saturation,R.drawable.ic_saturation_button);
        mAdjustFunctionList.add(saturation_item);
        Function_Item contrast_item=new Function_Item(R.string.adjust_contrast,R.drawable.ic_contrast_button);
        mAdjustFunctionList.add(contrast_item);
        Function_Item transparent_item=new Function_Item(R.string.adjust_transparency,R.drawable.ic_transparent_button);
        mAdjustFunctionList.add(transparent_item);
        Function_Item temperature_item=new Function_Item(R.string.adjust_temperature,R.drawable.ic_temperature_button);
        mAdjustFunctionList.add(temperature_item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_adjust,container,false);

         initFunctionList();
         mImageView= (ImageView) getActivity().findViewById(R.id.image_to_edit);
         mChooseAdjustButton=(ImageButton)v.findViewById(R.id.choose_adjust_effect_button);
         mChooseAdjustButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //Toast.makeText(getActivity(), "choose adjust effect", Toast.LENGTH_SHORT).show();
                 showPopUpWindow(v);//显示悬浮框选择功能
             }
         });

        return v;
    }

    /**
     * 显示悬浮框及其中的控件
     * @param v
     */
    private void showPopUpWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.adjust_function_popup_list,null,false);
        mListView=(ListView)view.findViewById(R.id.adjust_function_list);
        adapter=new Function_Item_Adapter(getActivity(),R.layout.operation_list_item,mAdjustFunctionList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function_Item item = mAdjustFunctionList.get(position);
                Toast.makeText(getActivity(), item.getmFuntionNameId(), Toast.LENGTH_SHORT).show();
            }
        });

        final PopupWindow popupWindow=new PopupWindow(view,mImageView.getWidth()/2,500,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //必须设置背景，否则悬浮框不会消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //悬浮框出现在图片区域中间
        popupWindow.showAtLocation(mImageView, Gravity.CENTER,0,0);
    }



}
