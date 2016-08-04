package com.seu.srtp_imageedit.Scrawl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScrawlBottomFragment extends Fragment implements View.OnClickListener{
    private ImageView mImageView;
    private Bitmap mBitmap;
    private Button mStyleButtom;
    private Button mColorButton;
    private Button mSizeButton;
    private Button mRubberButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_scrawl,container,false);

        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        mStyleButtom=(Button)v.findViewById(R.id.choose_brush_style);
        mColorButton=(Button)v.findViewById(R.id.choose_brush_color);
        mSizeButton=(Button)v.findViewById(R.id.choose_brush_size);
        mRubberButton=(Button)v.findViewById(R.id.choose_brush_rubber);
        mStyleButtom.setOnClickListener(this);
        mColorButton.setOnClickListener(this);
        mSizeButton.setOnClickListener(this);
        mRubberButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_brush_style:
                break;
            case R.id.choose_brush_color:
                break;
            case R.id.choose_brush_size:
                break;
            case R.id.choose_brush_rubber:
                break;
            default:
                break;
        }
    }
}
