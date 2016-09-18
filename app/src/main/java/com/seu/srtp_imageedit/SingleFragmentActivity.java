package com.seu.srtp_imageedit;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 周彬 on 2016/7/18.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{
    //由子类实现，选择自己的Fragment
    protected abstract Fragment createFragment();
    //便于子类设置自己的布局，默认为single_fragment.xml
    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.single_fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.single_fragment_fragment_container);

        if(fragment==null){
            fragment=createFragment();
            fm.beginTransaction().add(R.id.single_fragment_fragment_container,fragment).commit();
        }
    }
}
