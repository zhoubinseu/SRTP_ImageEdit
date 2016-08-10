package com.seu.srtp_imageedit.Adjust;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/9.
 */
public class AdjustUtil {
    private static Matrix mMatrix=new Matrix();
    private static ColorMatrix mColorMatrix=new ColorMatrix();
    private static Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private static Canvas mCanvas;

    public static Bitmap AdjustImage(int adjust_status,int progress,Drawable srcBitmapDrawable){
        Bitmap bm=null;
        switch (adjust_status){
            case R.string.adjust_transparency:
                bm=transparency(srcBitmapDrawable,progress);
                break;
            case R.string.adjust_luminance:
                bm=luminance(srcBitmapDrawable,progress);
                break;
            case R.string.adjust_saturation:
                bm=saturation(srcBitmapDrawable, progress);
                break;
            case R.string.adjust_hue:
                bm=hue(srcBitmapDrawable, progress);
                break;
            case R.string.adjust_contrast:
                bm=contrast(srcBitmapDrawable, progress);
                break;
            default:
                break;
        }
        return bm;
    }

    /**
     * 调整透明度
     * @param srcBitmapDrawable
     * @param progress
     * @return
     */
    private static Bitmap transparency(Drawable srcBitmapDrawable,int progress){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);

        mColorMatrix.set(new float[]{
                1,0,0,0,0,
                0,1,0,0,0,
                0,0,1,0,0,
                0,0,0,progress/255.0f,0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(srcBitmap,0,0,mPaint);
        return destBitmap;
    }

    /**
     * 调整亮度
     * @param srcBitmapDrawable
     * @param progress
     * @return
     */
    private static Bitmap luminance(Drawable srcBitmapDrawable,int progress){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);
        float lum=progress*1.0F/127;
        mColorMatrix.setScale(lum,lum,lum,1);
        /*mColorMatrix.set(new float[]{
                progress/128.0f,0,0,0,0,
                0,progress/128.0f,0,0,0,
                0,0,progress/128.0f,0,0,
                0,0,0,1,0,
        });*/
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(srcBitmap,0,0,mPaint);
        return destBitmap;
    }

    /**
     * 调整饱和度
     * @param srcBitmapDrawable
     * @param progress
     * @return
     */
    private static Bitmap saturation(Drawable srcBitmapDrawable,int progress){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);
        mColorMatrix.setSaturation(progress * 1.0F / 127);
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        return destBitmap;
    }

    /**
     * 调整色调
     * @param srcBitmapDrawable
     * @param progress
     * @return
     */
    private static Bitmap hue(Drawable srcBitmapDrawable,int progress){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);
        float hue=(progress-127)*1.0F/127*20;//改变最后乘的数值能改变调节的幅度
        mColorMatrix.setRotate(0,hue);
        mColorMatrix.setRotate(1, hue);
        mColorMatrix.setRotate(2, hue);
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        return destBitmap;
    }
    /**
     * 调整对比度
     * @param srcBitmapDrawable
     * @param progress
     * @return
     */
    private static Bitmap contrast(Drawable srcBitmapDrawable,int progress){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        Bitmap destBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(destBitmap);

        mColorMatrix.set(new float[]{
                (progress/2+64)/128.0f, 0, 0, 0, 0,
                0, (progress/2+64)/128.0f, 0, 0, 0,
                0, 0, (progress/2+64)/128.0f, 0, 0,
                0, 0, 0, 1, 0
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(srcBitmap,0,0,mPaint);
        return destBitmap;
    }
}
