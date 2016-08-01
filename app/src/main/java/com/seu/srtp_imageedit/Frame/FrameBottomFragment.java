package com.seu.srtp_imageedit.Frame;

import android.graphics.drawable.ColorDrawable;
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
import android.widget.PopupWindow;
import android.widget.Toast;

import com.seu.srtp_imageedit.MyGalleryAdapter;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FrameBottomFragment extends Fragment{
    private ImageButton mImageButton;
    private Gallery mGallery;//显示在悬浮框中

    private MyGalleryAdapter adapter;

    private int[] mFrameId=new int[]{
            R.drawable.add_image_bg,R.drawable.ic_apply_button,R.mipmap.ic_launcher
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frame_bottom_fragment,container,false);

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
        adapter=new MyGalleryAdapter(getActivity(),mFrameId);
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"点击了frame"+position,Toast.LENGTH_SHORT).show();
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
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(mImageButton, 0, 5);
    }

}
