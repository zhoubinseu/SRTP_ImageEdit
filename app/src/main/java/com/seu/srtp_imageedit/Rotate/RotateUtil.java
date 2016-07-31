package com.seu.srtp_imageedit.Rotate;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.seu.srtp_imageedit.R;

/**水平、垂直翻转，左右旋转（存在问题：图片不断变小）
 * Created by Administrator on 2016/7/31.
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
