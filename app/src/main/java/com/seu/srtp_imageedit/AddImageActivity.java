package com.seu.srtp_imageedit;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/7/18.
 */
public class AddImageActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new AddImageFragment();
    }
}
