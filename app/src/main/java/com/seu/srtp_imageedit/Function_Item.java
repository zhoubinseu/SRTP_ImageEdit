package com.seu.srtp_imageedit;

/**
 * 功能项类
 * 包含图像处理功能的图标和名字
 * Created by Administrator on 2016/7/28.
 */
public class Function_Item {
    private int mFuntionNameId;
    private int mIconId;

    public Function_Item(int mFuntionNameId, int mIconId) {
        this.mFuntionNameId = mFuntionNameId;
        this.mIconId = mIconId;
    }

    public int getmFuntionNameId() {
        return mFuntionNameId;
    }

    public int getmIconId() {
        return mIconId;
    }
}
