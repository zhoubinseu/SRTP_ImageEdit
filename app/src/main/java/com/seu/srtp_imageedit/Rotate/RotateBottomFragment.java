package com.seu.srtp_imageedit.Rotate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.seu.srtp_imageedit.R;

/**
 * 旋转操作的操作工具栏
 * Created by Administrator on 2016/7/30.
 */
public class RotateBottomFragment extends Fragment implements View.OnClickListener{
    private ImageButton mLeftRotateButton;//左转
    private ImageButton mRightRotateButton;//右转
    private ImageButton mXRotateButton;//水平翻转
    private ImageButton mYRotateButton;//垂直翻转
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.rotate_bottom_fragment,container,false);

        mLeftRotateButton=(ImageButton)v.findViewById(R.id.left_rotate_button);
        mRightRotateButton=(ImageButton)v.findViewById(R.id.right_rotate_button);
        mXRotateButton=(ImageButton)v.findViewById(R.id.x_rotate_button);
        mYRotateButton=(ImageButton)v.findViewById(R.id.y_rotate_button);
        mLeftRotateButton.setOnClickListener(this);
        mRightRotateButton.setOnClickListener(this);
        mXRotateButton.setOnClickListener(this);
        mYRotateButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_rotate_button:
                Toast.makeText(getActivity(),"left rotate",Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_rotate_button:
                Toast.makeText(getActivity(),"right rotate",Toast.LENGTH_SHORT).show();
                break;
            case R.id.x_rotate_button:
                Toast.makeText(getActivity(),"x rotate",Toast.LENGTH_SHORT).show();
                break;
            case R.id.y_rotate_button:
                Toast.makeText(getActivity(),"y rotate",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
