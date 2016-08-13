package com.seu.srtp_imageedit.Vignette;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.seu.srtp_imageedit.Function_Item;
import com.seu.srtp_imageedit.Function_Item_Adapter;
import com.seu.srtp_imageedit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/13.
 */
public class VignetteBottomFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{
    private double cRadius = 0.73;//晕影半径,范围[0,1]
    private double cLight = 0.25;//中心亮度,范围[0,0.25]
    private double cGradient = 20;//过渡梯度,范围[0,100]

    private int ADJUST_STATUS;//当前正在调整的属性

    private ImageButton mVignetteButton;
    private ImageButton mAdjustButton;
    private SeekBar mSeekBar;
    private ListView mListView;
    private Function_Item_Adapter adapter;
    private ImageView mImageView;
    private Drawable srcBitmapDrawable;
    private Bitmap mBitmap;//处理后的图像

    private Map<Integer,Integer> mMap=new HashMap<>();
    private List<Function_Item> mAdjustFunctionList=new ArrayList<Function_Item>();

    private void initMap(){
        //保存进度条的process
        mMap.put(R.string.vignette_radius,73);//radius=value/100
        mMap.put(R.string.vignette_light,100);//light=value/400
        mMap.put(R.string.vignette_gradient,20);//gradient=value
    }
    private void initList(){
        Function_Item radius_item=new Function_Item(R.string.vignette_radius,R.drawable.ic_radius_button);
        mAdjustFunctionList.add(radius_item);
        Function_Item light_item=new Function_Item(R.string.vignette_light,R.drawable.ic_luminance_button);
        mAdjustFunctionList.add(light_item);
        Function_Item gradient_item=new Function_Item(R.string.vignette_gradient,R.drawable.ic_gradient_button);
        mAdjustFunctionList.add(gradient_item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_vignette,container,false);

        initMap();
        initList();
        mVignetteButton=(ImageButton)v.findViewById(R.id.vignette_button);
        mVignetteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitmap = VignetteUtil.vignette(srcBitmapDrawable, 0.73, 0.25, 20);
                mImageView.setImageBitmap(mBitmap);
                Toast.makeText(getActivity(),"自动调整",Toast.LENGTH_SHORT).show();
                initMap();
            }
        });
        mAdjustButton=(ImageButton)v.findViewById(R.id.show_vignette_adjust_function);
        mAdjustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏进度条
                mSeekBar.setVisibility(View.GONE);
                showPopUpWindow(v);//显示悬浮框选择功能
            }
        });
        mSeekBar=(SeekBar)getActivity().findViewById(R.id.adjust_seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable=mImageView.getDrawable();
        return v;
    }

    private void showPopUpWindow(View v){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.adjust_function_popup_list,null,false);
        mListView=(ListView)view.findViewById(R.id.adjust_function_list);
        adapter=new Function_Item_Adapter(getActivity(),R.layout.operation_list_item,mAdjustFunctionList);
        mListView.setAdapter(adapter);

        final PopupWindow popupWindow=new PopupWindow(view,mImageView.getWidth()/2,ViewGroup.LayoutParams.WRAP_CONTENT,true);
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
        popupWindow.showAtLocation(mImageView, Gravity.CENTER, 0, 0);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击选择的功能后，悬浮框消失
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                Function_Item item = mAdjustFunctionList.get(position);
                //当前正在调整的图片属性
                ADJUST_STATUS = item.getmFuntionNameId();
                mSeekBar.setVisibility(View.VISIBLE);
                //将进度条调整至此功能下设置的值
                mSeekBar.setProgress(mMap.get(ADJUST_STATUS));
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mMap.put(ADJUST_STATUS,progress);
        switch (ADJUST_STATUS) {
            case R.string.vignette_radius:
                cRadius = progress / 100.0d;
                break;
            case R.string.vignette_light:
                cLight = progress / 400.0d;
                break;
            case R.string.vignette_gradient:
                cGradient = (double)progress;
                break;
            default:
                break;
        }
        mBitmap=VignetteUtil.vignette(srcBitmapDrawable,cRadius,cLight,cGradient);
        mImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
