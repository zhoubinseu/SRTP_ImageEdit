package com.seu.srtp_imageedit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 周彬 on 2016/7/20.
 */
public class Image {
    /**
     * 依然不能适配所有设备，暂时弃用
     *根据Uri获得图片的路径，适配不同API
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = { MediaStore.Images.Media.DATA };
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = { id };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 通过URI获得图片的绝对路径
     * @return
     */
    public static String getImagePathFromUri(Context context,Uri uri){
        Cursor cursor=context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String ImagePath=cursor.getString(idx);//the path of the photo selected
        cursor.close();
        return ImagePath;
    }

    /**
     * 根据图片的绝对路径读取并在ImageView显示图片
     * @param path
     */
    public static void showImage(String path,ImageView mImageView){
        Bitmap bm= BitmapFactory.decodeFile(path);
        mImageView.setImageBitmap(bm);
    }

    /**
     * 根据图片的绝对路径读取并缩放图片，并在ImageView显示图片
     * @param path
     * @param mImageView
     */
    public static void showScaledImage(String path,ImageView mImageView){
        Bitmap bm=Image.resizeImage(path);
        mImageView.setImageBitmap(bm);
    }

    /**
     * 通过Fragment分享图片到其他应用
     * @param imagepath
     * @param fragment
     */
    public static void shareImage(String imagepath,final Fragment fragment){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(imagepath);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        fragment.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 通过Activity分享图片到其他应用
     * @param imagepath
     * @param activity
     */
    public static void shareImage(String imagepath,final Activity activity){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(imagepath);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        activity.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 通过Fragment从相册选择图片
     * @param requestCode
     * @param fragment
     */
    public static void ChooseImage(final int requestCode,final Fragment fragment){
        //Intent chooseIntent=new Intent(Intent.ACTION_GET_CONTENT);
        Intent chooseIntent=new Intent(Intent.ACTION_PICK);
        chooseIntent.setType("image/*");
        fragment.startActivityForResult(chooseIntent, requestCode);
    }

    /**
     * 通过Activity从相册选择图片
     * @param requestCode
     * @param activity
     */
    public static void ChooseImage(final int requestCode,final Activity activity){
        //Intent chooseIntent=new Intent(Intent.ACTION_GET_CONTENT);//获取路径在不同设备上有问题
        Intent chooseIntent=new Intent(Intent.ACTION_PICK);
        chooseIntent.setType("image/*");
        activity.startActivityForResult(chooseIntent, requestCode);
    }

    /**
     * 通过Fragment调用相机功能
     * @param requestCode
     * @param fragment
     * @param photoDir
     */
    public static File TakePhoto(final int requestCode,final Fragment fragment,final File photoDir){
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
        //以时间将照片命名
        String photoName=System.currentTimeMillis()+".jpg";
        //当前照片的路径
        File mCurrentFileName=new File(photoDir,photoName);
        Intent takePhotoIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE,null);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentFileName));
        fragment.startActivityForResult(takePhotoIntent,requestCode);
        return mCurrentFileName;
    }

    /**
     * 通过Activity调用相机功能
     * @param requestCode
     * @param activity
     * @param photoDir
     */
    public static File TakePhoto(final int requestCode,final Activity activity,final File photoDir){
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
        //以时间将照片命名
        String photoName=System.currentTimeMillis()+".jpg";
        //当前照片的路径
        File mCurrentFileName=new File(photoDir,photoName);
        Intent takePhotoIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE,null);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentFileName));
        activity.startActivityForResult(takePhotoIntent,requestCode);
        return mCurrentFileName;
    }

    /**
     * 保存图片到相应的路径下
     * @param srcBitmap
     * @param photoDir
     * @return File
     */
    public static File saveImage(final Bitmap srcBitmap,final File photoDir){
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
        String photoName=System.currentTimeMillis()+".jpg";
        File mCurrentFileName=new File(photoDir,photoName);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(mCurrentFileName);
            srcBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCurrentFileName;
    }

    /**
     * 保存ImageView控件上的图片到相应的路径下
     * @param srcBitmapImageView
     * @param photoDir
     * @return File
     */
    public static File saveImage(final ImageView srcBitmapImageView,final File photoDir){
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
        String photoName=System.currentTimeMillis()+".jpg";
        File mCurrentFileName=new File(photoDir,photoName);
        try {
            //获得要保存的具体图像，而不是Bitmap,Bitmap会与ImageView同样大
            Drawable srcBitmapDrawable=srcBitmapImageView.getDrawable();
            //Drawable转Bitmap
            Bitmap srcBitmap=DrawableToBitmap(srcBitmapDrawable);
            FileOutputStream fileOutputStream=new FileOutputStream(mCurrentFileName);
            srcBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCurrentFileName;
    }

    /**
     * Drawable对象转Bitmap对象
     * @param drawable
     * @return
     */
    public static Bitmap DrawableToBitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 由于相机分辨率很高，图片加载到内存中占用资源过大，对图片像素进行压缩
     * @param imagePath 要压缩的图片的路径
     * @return
     */
    public static Bitmap resizeImage(String imagePath){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;//将此属性设置为true，不会加载图片，仅仅获得尺寸
        BitmapFactory.decodeFile(imagePath, options);//将imagePath路径下的图片的尺寸保存在options
        double ratio=Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024f);//计算缩放率
        options.inSampleSize= (int) Math.ceil(ratio);
        options.inJustDecodeBounds=false;//将此属性设置为false
        Bitmap bmp=BitmapFactory.decodeFile(imagePath,options);//获取缩放后的图片
        return bmp;
    }

    /**
     * 压缩bitmap至指定大小,并将bitmap转换为输出流,待写入文件或者上传
     * @param image
     * @param size 大小,以kb为计算单位
     * @return
     */
    public static byte[] compressBmp(Bitmap image, int size) {
        if(image == null){
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //压缩文件至不超过100K
        while (baos.toByteArray().length / 1024 > size) {
            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        return baos.toByteArray();
    }

}
