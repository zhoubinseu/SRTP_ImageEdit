package com.seu.srtp_imageedit.Frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FrameUtil {
    /**
     * 添加相框，由于相框与图片尺寸不同，要对相框图片进行缩放
     * @param context
     * @param srcBitmapDrawable ImageView上显示的实际内容，直接使用Bitmap对象得到的尺寸不是上面显示的图片尺寸
     * @param frameTextureId 使用的相框ID
     * @return
     */
    public static Bitmap addFrame(Context context,Drawable srcBitmapDrawable,int frameTextureId){
        Bitmap bm= BitmapFactory.decodeResource(context.getResources(),frameTextureId);
        Drawable[] array = new Drawable[2];
        array[0] = srcBitmapDrawable;
        Bitmap b = resize(bm, srcBitmapDrawable.getBounds().width(), srcBitmapDrawable.getBounds().height());
        array[1] = new BitmapDrawable(b);
        //将两个图层叠加
        LayerDrawable layer = new LayerDrawable(array);
        return drawableToBitmap(layer);
    }

    /**
     * 缩放图片到特定尺寸
     * @param bmp
     * @param destWidth
     * @param destHeight
     * @return
     */
    private static Bitmap resize(Bitmap bmp,int destWidth,int destHeight){
        /*
        float width=bmp.getWidth();
        float height=bmp.getHeight();
        Matrix matrix=new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) destWidth) / width;
        float scaleHeight = ((float) destHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bmp,0,0,(int)width,(int)height,matrix,false);
        */
        return Bitmap.createScaledBitmap(bmp,destWidth,destHeight,false);
    }

    /**
     * 将原图和相框叠加生成最后的效果图
     * @param drawable
     * @return
     */
    private static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
