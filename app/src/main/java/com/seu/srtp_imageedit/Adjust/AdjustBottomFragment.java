package com.seu.srtp_imageedit.Adjust;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AdjustBottomFragment extends Fragment{

    private ImageButton mChooseAdjustButton;
    private ImageView mImageView;//该碎片所在的活动的ImageView
    private Bitmap mBitmap;//处理后的图片

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_adjust,container,false);

        mChooseAdjustButton=(ImageButton)v.findViewById(R.id.choose_adjust_effect_button);
        mChooseAdjustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"choose adjust effect",Toast.LENGTH_SHORT).show();
            }
        });

        mImageView= (ImageView) getActivity().findViewById(R.id.image_to_edit);

        return v;
    }
}
