package com.seu.srtp_imageedit.Sharpen;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SharpenBottomFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{

    private String seekBarStatus;//保存当前进度条状态
    private String seekBarVisible="VISIBLE";//进度条可见
    private String seekBarGone="GONE";//进度条被隐藏

    private ImageButton mSharpenButton;
    private SeekBar mSeekBar;
    private ImageView mImageView;//该碎片所在的活动的ImageView
    private Drawable srcBitmapDrawable;//待处理图像的内容
    private Bitmap mBitmap;//处理后的图片

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_sharpen,container,false);

        seekBarStatus=seekBarGone;
        mSeekBar=(SeekBar)getActivity().findViewById(R.id.adjust_seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        srcBitmapDrawable=mImageView.getDrawable();

        mSharpenButton=(ImageButton)v.findViewById(R.id.choose_sharpen_button);
        mSharpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBarStatus.equals(seekBarGone)){
                    seekBarStatus=seekBarVisible;
                    mSeekBar.setVisibility(View.VISIBLE);
                }else if(seekBarStatus.equals(seekBarVisible)){
                    seekBarStatus=seekBarGone;
                    mSeekBar.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress=seekBar.getProgress();
        mBitmap=SharpenUtil.sharpen(progress,srcBitmapDrawable);
        mImageView.setImageBitmap(mBitmap);
        Toast.makeText(getActivity(),progress/100.0f+"",Toast.LENGTH_SHORT).show();
    }
}
