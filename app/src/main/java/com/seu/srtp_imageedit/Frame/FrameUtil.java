package com.seu.srtp_imageedit.Frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.seu.srtp_imageedit.Image;

/**
 * 添加图片边框的工具类
 * Created by 周彬 on 2016/8/2.
 */
public class FrameUtil {
    /**
     * 叠加图片添加相框，由于相框与图片尺寸不同，要对相框图片进行缩放
     * @param context
     * @param srcBitmapDrawable ImageView上显示的实际内容，直接使用Bitmap对象得到的尺寸不是上面显示的图片尺寸
     * @param frameTextureId 使用的相框ID
     * @return
     */
    public static Bitmap addFrameByOverLay(Context context,Drawable srcBitmapDrawable,int frameTextureId){
        Bitmap bm= BitmapFactory.decodeResource(context.getResources(),frameTextureId);
        Drawable[] array = new Drawable[2];
        array[0] = srcBitmapDrawable;
        Bitmap b = resize(bm, srcBitmapDrawable.getBounds().width(), srcBitmapDrawable.getBounds().height());
        array[1] = new BitmapDrawable(b);
        //将两个图层叠加
        LayerDrawable layer = new LayerDrawable(array);
        return Image.DrawableToBitmap(layer);
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
        return Bitmap.createScaledBitmap(bmp, destWidth, destHeight, false);
    }

    /**
     * 通过拼接产生相框,将边框沿图片边缘拼接
     * @return
     * @param context
     * @param srcBitmapDrawable 待处理图像
     * @param frameTextureId 边框资源
     */
    public static Bitmap addFrameByStitch(Context context,Drawable srcBitmapDrawable,int frameTextureId){
        Bitmap bm= BitmapFactory.decodeResource(context.getResources(),frameTextureId);//边框资源图
        Bitmap frameBitmap=resize(bm, 50, 50);//将边框资源图缩小，用于拼接
        bm.recycle();//回收Bitmap资源
        //获得边框的宽高
        final int smallW=frameBitmap.getWidth();
        final int smallH=frameBitmap.getHeight();

        //获得待处理图像的宽高
        final int bigW=srcBitmapDrawable.getIntrinsicWidth();
        final int bigH=srcBitmapDrawable.getIntrinsicHeight();

        //计算长和宽需要的边框资源数目
        int wCount=(int)Math.ceil(bigW*1.0/smallW);
        int hCount=(int)Math.ceil(bigH*1.0/smallH);

        //组合之后的图片的宽高
        int newW=(wCount+2)*smallW;
        int newH=(hCount+2)*smallH;

        //重新定义大小
        Bitmap newBitmap=Bitmap.createBitmap(newW,newH, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        Paint p=new Paint();
        p.setColor(Color.TRANSPARENT);
        //整个图片的区域
        canvas.drawRect(0,0,newW,newH,p);

        //去除相框的区域
        Rect rect = new Rect(smallW, smallH, newW - smallW, newH - smallH);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(rect, paint);

        // 绘原图
        canvas.drawBitmap(Image.DrawableToBitmap(srcBitmapDrawable), (newW - bigW - 2 * smallW) / 2 + smallW, (newH - bigH - 2 * smallH) / 2 + smallH, null);
        //绘边框
        //绘四个角
        int startW = newW - smallW;
        int startH = newH - smallH;
        canvas.drawBitmap(frameBitmap, 0, 0, null);//左上角
        canvas.drawBitmap(frameBitmap, 0, startH, null);//左下角
        canvas.drawBitmap(frameBitmap, startW, startH, null);//右下角
        canvas.drawBitmap(frameBitmap, startW, 0, null);//右上角

        // 绘左右边框
        for (int i = 0, length = hCount; i < length; i++)
        {
            int h = smallH * (i + 1);
            canvas.drawBitmap(frameBitmap, 0, h, null);
            canvas.drawBitmap(frameBitmap, startW, h, null);
        }
        // 绘上下边框
        for (int i = 0, length = wCount; i < length; i++)
        {
            int w = smallW * (i + 1);
            canvas.drawBitmap(frameBitmap, w, startH, null);
            canvas.drawBitmap(frameBitmap, w, 0, null);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return newBitmap;
    }

}
