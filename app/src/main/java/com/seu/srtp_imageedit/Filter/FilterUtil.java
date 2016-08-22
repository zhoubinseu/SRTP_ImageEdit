package com.seu.srtp_imageedit.Filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class FilterUtil {
    private static ColorMatrix mColorMatrix=new ColorMatrix();//进行滤镜处理要使用的颜色矩阵
    private static float[] colorArray;
    private static Canvas mCanvas;
    private static Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public static Bitmap process_filter(int filer_number_id,Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);

        switch (filer_number_id){
            case R.string.num_1:
                colorArray=new float[]{
                        1,0,0,0,100,
                        0,1,0,0,100,
                        0,0,1,0,0,
                        0,0,0,1,0,
                };
                mColorMatrix.set(colorArray);
                mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
                mCanvas.drawBitmap(srcBitmap,0,0,mPaint);
                break;
            default:
                break;
        }
        return destBitmap;
    }

}
