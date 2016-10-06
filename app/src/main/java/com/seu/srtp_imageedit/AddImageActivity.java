package com.seu.srtp_imageedit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

/**
 * 添加待处理图片的Activity
 * Created by 周彬 on 2016/7/18.
 */
public class AddImageActivity extends SingleFragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得屏幕的相应参数，存入Constant中
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Constant.getInstance().setWindowWidth(width);
        Constant.getInstance().setWindowHeight(height);
    }

    @Override
    protected Fragment createFragment() {
        return new AddImageFragment();
    }
}
