package com.seu.srtp_imageedit.Sharpen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.seu.srtp_imageedit.Image;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SharpenUtil {
    /**
     * 实现锐化，根据progress调整delta值
     * 像素点处理方法:
     * int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
     *float delta = 0.3;
     *E.r = A.r * laplacian[0] * delta + B.r * laplacian[1] * delta
     * + C.r * laplacian[2] * delta + D.r * laplacian[3] * delta
     * + E.r * laplacian[4] * delta + F.r * laplacian[5] * delta
     * + G.r * laplacian[6] * delta + H.r * laplacian[7] * delta
     * + I.r * laplacian[8] * delta;
     *
     * @param progress
     * @param srcBitmapDrawable
     * @return
     */
    public static Bitmap sharpen(int progress,Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        // 拉普拉斯矩阵
        int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
        float delta;
        delta=progress/100.0f;
        int index;

        int color;
        int r,g,b,a;//旧的分量
        int r1=0;
        int g1=0;
        int b1=0;//新的分量

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];
        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);

        for(int i=1;i<srcHeight-1;i++){
            for(int j=1;j<srcWidth-1;j++){
                index=0;
                //根据当前像素点周围8个像素点计算新的值
                for (int m = -1; m <= 1; m++)
                {
                    for (int n = -1; n <= 1; n++)
                    {
                        color = oldPx[(i + n) * srcWidth + j + m];
                        r = Color.red(color);
                        g = Color.green(color);
                        b = Color.blue(color);

                        r1 = r1 + (int) (r * laplacian[index] * delta);
                        g1 = g1 + (int) (g * laplacian[index] * delta);
                        b1 = b1 + (int) (b * laplacian[index] * delta);
                        index++;
                    }
                }

                r1 = Math.min(255, Math.max(0, r1));
                g1 = Math.min(255, Math.max(0, g1));
                b1 = Math.min(255, Math.max(0, b1));

                newPx[i * srcWidth + j] = Color.argb(255, r1, g1, b1);
                r1 = 0;
                g1 = 0;
                b1 = 0;
            }
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }
}
