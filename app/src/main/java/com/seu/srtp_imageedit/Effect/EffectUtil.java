package com.seu.srtp_imageedit.Effect;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class EffectUtil {

    /**
     * 传入选中的处理效果名称ID和原图，返回处理结果
     * @param effectNameId
     * @param srcBitmapDrawable
     * @return
     */
    public static Bitmap process_effect(int effectNameId,Drawable srcBitmapDrawable){
        Bitmap bm=null;
        switch (effectNameId){
            case R.string.effect_negative:
                bm=negative(srcBitmapDrawable);
                break;
            case R.string.effect_emboss:
                bm=emboss(srcBitmapDrawable);
                break;
            case R.string.effect_ice:
                bm=ice(srcBitmapDrawable);
                break;
            case R.string.effect_neon:
                bm=neon(srcBitmapDrawable);
                break;
            case R.string.effect_nostalgic:
                bm=nostalgic(srcBitmapDrawable);
                break;
            case R.string.effect_black_white:
                bm=blackWhite(srcBitmapDrawable);
                break;
            case R.string.effect_halo:
                bm=halo(srcBitmapDrawable);
                break;
            case R.string.effect_binary:
                bm=binaryzation(srcBitmapDrawable);
                break;
            default:
                break;
        }
        return bm;
    }

    //黑白
    private static Bitmap blackWhite(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color;
        int r,g,b,a;//旧的分量
        int r1,g1,b1;//新的分量

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);

        for(int i=0;i<srcWidth*srcHeight;i++){
            color=oldPx[i];
            r=Color.red(color);
            g=Color.green(color);
            b=Color.blue(color);
            a=Color.alpha(color);
            r1=(int)(0.3*r+0.3*g+0.3*b);
            g1=(int)(0.3*r+0.3*g+0.3*b);
            b1=(int)(0.3*r+0.3*g+0.3*b);
            if(r1>255){
                r1=255;
            }
            if(g1>255){
                g1=255;
            }
            if(b1>255){
                b1=255;
            }
            newPx[i]=Color.argb(a,r1,g1,b1);
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }
    //二值化:先进行黑白处理，在设定阈值，进行二值化处理
    private static Bitmap binaryzation(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color;
        int r,g,b,a;//旧的分量
        int r1,g1,b1;//新的分量

        int grey=125;//阈值为125，可调整

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);

        for(int i=0;i<srcWidth*srcHeight;i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            //黑白处理
            r1 = (int) (0.3 * r + 0.3 * g + 0.3 * b);
            g1 = (int) (0.3 * r + 0.3 * g + 0.3 * b);
            b1 = (int) (0.3 * r + 0.3 * g + 0.3 * b);
            //二值化处理
            if (r1 > grey) {
                r1 = 255;
            } else {
                r1 = 0;
            }

            if (g1 > grey) {
                g1 = 255;
            } else {
                g1 = 0;
            }

            if (b1 > grey) {
                b1 = 255;
            } else {
                b1 = 0;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }
    //浮雕
    private static Bitmap emboss(Drawable srcBitmapDrawable){
        //将参数Drawable转化为Bitmap
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color=0;//当前点
        int colorBefore=0;//前一个点
        int r,g,b,a;//前一个点的分量
        int r1,g1,b1;//当前点的分量

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);
        //从1开始循环
        for(int i=1;i<srcWidth*srcHeight;i++){
            colorBefore=oldPx[i-1];
            a=Color.alpha(colorBefore);
            r=Color.red(colorBefore);
            g=Color.green(colorBefore);
            b=Color.blue(colorBefore);
            color=oldPx[i];
            r1=Color.red(color);
            g1=Color.green(color);
            b1=Color.blue(color);
            //算法
            r=(r-r1+127);
            g=(g-g1+127);
            b=(b-b1+127);
            if(r>255){
                r=255;
            }
            if(g>255){
                g=255;
            }
            if(b>255){
                b=255;
            }
            newPx[i]=Color.argb(a,r,g,b);
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }
    //底片
    private static Bitmap negative(Drawable srcBitmapDrawable){
        //将参数Drawable转化为Bitmap
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color;//保存当前的像素点
        int r,g,b,a;//保存当前像素点的RGBA分量

        //将像素点保存到数组
        int[] oldPx=new int[srcWidth*srcHeight];//处理前的像素点
        int[] newPx=new int[srcWidth*srcHeight];//处理后的像素点
        //为oldPx赋值
        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);
        //底片效果的处理算法，对每一个像素点进行处理并写入newPx
        for(int i=0;i<srcWidth*srcHeight;i++) {
            color = oldPx[i];//取出像素点
            //获得分量
            r = Color.red(color);
            g = Color.blue(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            //重新赋值
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);//将新的分量组成新的颜色给新的像素点数组
        }
        destBitmap.setPixels(newPx, 0, srcWidth, 0, 0, srcWidth, srcHeight);//将新的像素点给创建的新bitmap
        return destBitmap;
    }
    //光晕
    private static Bitmap halo(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        return destBitmap;
    }
    //冰冻
    private static Bitmap ice(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color;
        int r,g,b,a;//旧的分量
        //int r1,g1,b1;//新的分量
        int pixel;

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);
        for(int i=0;i<srcWidth*srcHeight;i++){
            color=oldPx[i];
            r=Color.red(color);
            g=Color.green(color);
            b=Color.blue(color);
            a=Color.alpha(color);
            //R
            pixel=(r-g-b)*3/2;
            if(pixel<0){
                pixel=-pixel;
            }
            if(pixel>255){
                pixel=255;
            }
            r=pixel;
            //G
            pixel=(g-r-b)*3/2;
            if(pixel<0){
                pixel=-pixel;
            }
            if(pixel>255){
                pixel=255;
            }
            g=pixel;
            //B
            pixel=(b-g-r)*3/2;
            if(pixel<0){
                pixel=-pixel;
            }
            if(pixel>255){
                pixel=255;
            }
            b=pixel;

            newPx[i]=Color.argb(a,r,g,b);
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }
    //霓虹
    private static Bitmap neon(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        return destBitmap;
    }
    //怀旧
    private static Bitmap nostalgic(Drawable srcBitmapDrawable){
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int srcWidth= srcBitmap.getWidth();
        int srcHeight= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);

        int color;
        int r,g,b,a;//旧的分量
        int r1,g1,b1;//新的分量

        int[] oldPx=new int[srcWidth*srcHeight];
        int[] newPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);
        for(int i=0;i<srcWidth*srcHeight;i++){
            color=oldPx[i];
            r=Color.red(color);
            g=Color.green(color);
            b=Color.blue(color);
            a=Color.alpha(color);
            //怀旧照片算法
            r1=(int)(0.393*r+0.769*g+0.189*b);
            g1=(int)(0.349*r+0.686*g+0.168*b);
            b1=(int)(0.272*r+0.534*g+0.131*b);
            if(r1>255){
                r1=255;
            }
            if(g1>255){
                g1=255;
            }
            if(b1>255){
                b1=255;
            }
            newPx[i]=Color.argb(a,r1,g1,b1);
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }

}
