package com.seu.srtp_imageedit.Rotate;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * 实现左右旋转、上下、左右翻转
 * Created by 周彬 on 2016/7/31.
 */
public class RotateUtil {
    private static int srcWidth;
    private static int srcHeight;

    public static Bitmap Rotate(Bitmap srcBitmap,int rotateButtonId){
        srcWidth=srcBitmap.getWidth();
        srcHeight=srcBitmap.getHeight();
        Matrix matrix=new Matrix();
        switch (rotateButtonId){
            case R.id.left_rotate_button:
                matrix.reset();
                matrix.setRotate(-90);
                break;
            case R.id.right_rotate_button:
                matrix.reset();
                matrix.setRotate(90);
                break;
            case R.id.x_rotate_button:
                matrix.reset();
                matrix.setScale(-1,1);
                break;
            case R.id.y_rotate_button:
                matrix.reset();
                matrix.setScale(1,-1);
                break;
            default:
                break;
        }
        Bitmap bm=Bitmap.createBitmap(srcBitmap,0,0,srcWidth,srcHeight,matrix,false);
        return bm;
    }
}
