package com.seu.srtp_imageedit.Vignette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.seu.srtp_imageedit.Image;

/**
 * Created by Administrator on 2016/8/13.
 */
public class VignetteUtil {
    private static double cRadius = 0.73;//晕影半径,范围[0,1]
    private static double cLight = 0.25;//中心亮度,范围[0,0.25]
    private static double cGradient = 20;//过渡梯度,范围[0,100]

    /**
     * 通过进度条调节晕影
     *
     * @param srcBitmapDrawable
     * @param cRadius
     * @param cLight
     * @param cGradient
     * @return
     */
    public static Bitmap vignette(Drawable srcBitmapDrawable, double cRadius, double cLight, double cGradient) {
        Bitmap srcBitmap = Image.DrawableToBitmap(srcBitmapDrawable);
        int srcHeight = srcBitmap.getHeight();
        int srcWidth = srcBitmap.getWidth();
        Bitmap destBitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int a, r, g, b;
        int color;
        double dist;
        double maxDist = 1.0 / Math.sqrt(srcWidth * srcWidth / 4 + srcHeight * srcHeight / 4);
        double lum;
        int[] aPx = new int[srcWidth * srcHeight];//原图
        int[] bPx = new int[srcWidth * srcHeight];//晕影图像
        srcBitmap.getPixels(aPx, 0, srcWidth, 0, 0, srcWidth, srcHeight);
        //生成晕影图像的像素矩阵
        for (int i = 0; i < srcWidth; i++) {
            for (int j = 0; j < srcHeight; j++) {
                color = aPx[j * srcWidth + i];
                a = Color.alpha(color);
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                dist = Math.sqrt((i - srcWidth / 2) * (i - srcWidth / 2) + (j - srcHeight / 2) * (j - srcHeight / 2));
                lum = 0.75 / (1.0 + Math.exp((dist * maxDist - cRadius) * cGradient)) + cLight;
                r = (int) ((double) r * lum);
                g = (int) ((double) g * lum);
                b = (int) ((double) b * lum);
                bPx[j * srcWidth + i] = Color.argb(a, r, g, b);
            }
        }

        destBitmap.setPixels(bPx, 0, srcWidth, 0, 0, srcWidth, srcHeight);
        return destBitmap;
    }
}
