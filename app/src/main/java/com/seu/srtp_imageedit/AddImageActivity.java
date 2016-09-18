package com.seu.srtp_imageedit;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * 添加待处理图片的Activity
 * Created by 周彬 on 2016/7/18.
 */
public class AddImageActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new AddImageFragment();
    }
}
