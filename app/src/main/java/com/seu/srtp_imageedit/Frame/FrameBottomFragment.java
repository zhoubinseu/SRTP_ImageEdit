package com.seu.srtp_imageedit.Frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FrameBottomFragment extends Fragment{
    private ImageButton mImageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frame_bottom_fragment,container,false);

        mImageButton=(ImageButton)v.findViewById(R.id.choose_frame_button);


        return v;
    }
}
