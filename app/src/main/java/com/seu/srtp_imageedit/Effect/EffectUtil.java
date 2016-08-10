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
    /** 水平方向模糊度,用于模糊特效 */
    private static float hRadius = 10;
    /** 竖直方向模糊度,用于模糊特效 */
    private static float vRadius = 10;
    /** 模糊迭代度,用于模糊特效 */
    private static int iterations = 7;

    /**
     * 传入选中的处理效果名称ID和原图，返回处理结果
     * @param effectNameId
     * @param srcBitmapDrawable
     * @return
     */
    public static Bitmap process_effect(int effectNameId,Drawable srcBitmapDrawable){
        Bitmap bm=null;
        switch (effectNameId){
            case R.string.effect_sketch:
                bm=sketch(srcBitmapDrawable);
                break;
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
            case R.string.effect_light:
                bm=light(srcBitmapDrawable);
                break;
            case R.string.effect_blur:
                bm=blur(srcBitmapDrawable);
                break;
            case R.string.effect_poster:
                bm=poster(srcBitmapDrawable);
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

        int color;//当前像素
        int color_right;//当前像素右侧的像素
        int color_bottom;//当前像素下方的像素
        int r,g,b;

        int[] oldPx=new int[srcWidth*srcHeight];

        srcBitmap.getPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);

        for(int i=0;i<srcWidth-1;i++){
            for(int j=0;j<srcHeight-1;j++){
                color=oldPx[j*srcWidth+i];
                //获取i+1像素
                color_right = oldPx[j*srcWidth+i+1];
                //获取j+1像素
                color_bottom = oldPx[(j+1)*srcWidth+i];

                //计算R分量
                r = (int) (Math.pow((Color.red(color)-Color.red(color_right)),2)
                        +Math.pow((Color.red(color)-Color.red(color_bottom)),2));
                r = ((int) (Math.sqrt(r)*2));
                r = Math.min(255,Math.max(0,r));

                //计算G 分量
                g = (int) (Math.pow((Color.green(color)-Color.green(color_right)),2)
                        +Math.pow((Color.green(color)-Color.green(color_bottom)),2));
                g = ((int) (Math.sqrt(g)*2));
                g = Math.min(255,Math.max(0,g));
                //计算B分量
                b = (int) (Math.pow((Color.blue(color)-Color.blue(color_right)),2)
                        +Math.pow((Color.blue(color)-Color.blue(color_bottom)),2));
                b = ((int) (Math.sqrt(b)*2));
                b = Math.min(255,Math.max(0,b));

                oldPx[j*srcWidth+i] = Color.argb(Color.alpha(color),r,g,b);
            }
        }
        destBitmap.setPixels(oldPx,0,srcWidth,0,0,srcWidth,srcHeight);
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
    //光照
    private static Bitmap light(Drawable srcBitmapDrawable){
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

        // 围绕圆形光照
        int centerX = srcWidth / 2;
        int centerY = srcHeight / 2;
        int radius = Math.min(centerX, centerY);
        float strength = 100F;// 光照强度100-150

        for (int i = 0; i < srcHeight; i++) {
            for (int k = 0; k < srcWidth; k++) {
                // 获取前一个像素颜色  
                color = oldPx[srcWidth * i + k];
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                a = Color.alpha(color);
                r1 = r;
                g1 = g;
                b1 = b;
                // 计算当前点到光照中心的距离,平面坐标系中两点之间的距离  
                int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(
                        (centerX - k), 2));
                if (distance < radius * radius) {
                    // 按照距离大小计算增强的光照值  
                    int result = (int) (strength * (1.0 - Math.sqrt(distance) / radius));
                    r1 = r + result;
                    g1 = g1 + result;
                    b1 = b + result;
                }
                r1 = Math.min(255, Math.max(0, r1));
                g1 = Math.min(255, Math.max(0, g1));
                b1 = Math.min(255, Math.max(0, b1));
                newPx[srcWidth * i + k] = Color.argb(a, r1, g1, b1);
            }
        }
        destBitmap.setPixels(newPx,0,srcWidth,0,0,srcWidth,srcHeight);
        return destBitmap;
    }

    //模糊效果
    private static Bitmap blur(Drawable srcBitmapDrawable) {

        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];

        srcBitmap.getPixels(inPixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < iterations; i++) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
        blurFractional(inPixels, outPixels, width, height, hRadius);
        blurFractional(outPixels, inPixels, height, width, vRadius);
        destBitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        return destBitmap;
    }
    private static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];
        for (int i = 0; i < 256 * tableSize; i++) {
            divide[i] = i / tableSize;
        }

        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];
                int i1 = x + r + 1;
                if (i1 > widthMinus1) {
                    i1 = widthMinus1;
                }
                int i2 = x - r;
                if (i2 < 0) {
                    i2 = 0;
                }
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];
                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }
    private static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];
                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }
    private static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    //素描
    private static Bitmap sketch(Drawable srcBitmapDrawable) {
        Bitmap srcBitmap= Image.DrawableToBitmap(srcBitmapDrawable);
        int width= srcBitmap.getWidth();
        int height= srcBitmap.getHeight();
        Bitmap destBitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] pixels = new int[width * height]; // 存储变换图像
        int[] linpix = new int[width * height]; // 存储灰度图像

        srcBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        // 灰度图像
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {  // 拉普拉斯算子模板 { 0, -1, 0, -1, -5, -1, 0, -1, 0
                // 获取前一个像素颜色
                pixColor = pixels[width * j + i];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                // 灰度图像
                int gray = (int) (0.3 * pixR + 0.59 * pixG + 0.11 * pixB);
                linpix[width * j + i] = Color.argb(255, gray, gray, gray);
                // 图像反向
                gray = 255 - gray;
                pixels[width * j + i] = Color.argb(255, gray, gray, gray);
            }
        }
        int radius = Math.min(width / 2, height / 2);
        int[] copixels = gaussBlur(pixels, width, height, 10, 10 / 3); // 高斯模糊
        // 采用半径10
        int[] result = colorDodge(linpix, copixels); // 素描图像 颜色减淡
        destBitmap.setPixels(result, 0, width, 0, 0, width, height);
        return destBitmap;
    }
    // 高斯模糊
    private static int[] gaussBlur(int[] data, int width, int height, int radius, float sigma) {
        float pa = (float) (1 / (Math.sqrt(2 * Math.PI) * sigma));
        float pb = -1.0f / (2 * sigma * sigma);

        // generate the Gauss Matrix
        float[] gaussMatrix = new float[radius * 2 + 1];
        float gaussSum = 0f;
        for (int i = 0, x = -radius; x <= radius; ++x, ++i) {
            float g = (float) (pa * Math.exp(pb * x * x));
            gaussMatrix[i] = g;
            gaussSum += g;
        }
        for (int i = 0, length = gaussMatrix.length; i < length; ++i) {
            gaussMatrix[i] /= gaussSum;
        }

        // x direction
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = x + j;
                    if (k >= 0 && k < width) {
                        int index = y * width + k;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];
                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);
                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }

        // y direction
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = y + j;
                    if (k >= 0 && k < height) {
                        int index = k * width + x;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];
                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);
                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }
        return data;
    }
    // 颜色减淡
    private static int[] colorDodge(int[] baseColor, int[] mixColor) {
        for (int i = 0, length = baseColor.length; i < length; ++i) {
            int bColor = baseColor[i];
            int br = (bColor & 0x00ff0000) >> 16;
            int bg = (bColor & 0x0000ff00) >> 8;
            int bb = (bColor & 0x000000ff);

            int mColor = mixColor[i];
            int mr = (mColor & 0x00ff0000) >> 16;
            int mg = (mColor & 0x0000ff00) >> 8;
            int mb = (mColor & 0x000000ff);

            int nr = colorDodgeFormular(br, mr);
            int ng = colorDodgeFormular(bg, mg);
            int nb = colorDodgeFormular(bb, mb);
            baseColor[i] = nr << 16 | ng << 8 | nb | 0xff000000;
        }
        return baseColor;
    }
    private static int colorDodgeFormular(int base, int mix) {
        int result = base + (base * mix) / (255 - mix);
        result = result > 255 ? 255 : result;
        return result;
    }

    /**
     * 海报效果
     * @param srcBitmapDrawable
     * @return
     */
    private static Bitmap poster(Drawable srcBitmapDrawable){
        return null;
    }

}
