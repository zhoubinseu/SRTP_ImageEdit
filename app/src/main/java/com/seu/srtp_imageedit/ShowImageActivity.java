package com.seu.srtp_imageedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ShowImageActivity extends AppCompatActivity{
    private static final String IMAGE_PATH="com.seu.srtp.imageedit.image_path";
    private File  PHOTO_DIR=new File(Environment.getExternalStorageDirectory() + "/SRTP_ImageEdit/CameraCache");

    private ImageView mImageView;
    //private Bitmap mBitmap;//显示在ImageView上的图片

    private String mImagePath;//当前图片的路径

    /**
     * 由其他Activity或Fragment调用，启动EditActivity
     * @param packageContext
     * @param path
     * @return
     */
    public static Intent newIntent(Context packageContext,String path){
        Intent intent=new Intent(packageContext,ShowImageActivity.class);
        intent.putExtra(IMAGE_PATH, path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置ActionBar的属性
        ActionBar actionBar=getSupportActionBar();
        //actionBar.setTitle(R.string.menu_open);
        //不显示标题
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setLogo(R.mipmap.ic_launcher);
        //actionBar.setDisplayUseLogoEnabled(true);

        setContentView(R.layout.show_image_activity);
        mImageView=(ImageView)findViewById(R.id.show_image_image_view);
        mImagePath=getIntent().getStringExtra(IMAGE_PATH);
        //Toast.makeText(ShowImageActivity.this,path,Toast.LENGTH_SHORT).show();
        Image.showImage(mImagePath, mImageView);
    }

    /**
     * 在ActionBar上创建Menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_image_menu, menu);
        return true;
    }

    /**
     * 点击菜单项的响应事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_share:
                //Toast.makeText(ShowImageActivity.this,"分享"+mImagePath,Toast.LENGTH_SHORT).show();
                Image.shareImage(mImagePath,ShowImageActivity.this);
                return true;
            case R.id.menu_item_save:
                mImageView.setDrawingCacheEnabled(true);//确保可以使用getDrawingCache()获得图像
                String path=Image.saveImage(mImageView.getDrawingCache(),PHOTO_DIR).getAbsolutePath();
                mImageView.setDrawingCacheEnabled(false);//清空画图缓存区,这样保证连续保存时每次重新加载图像
                Toast.makeText(ShowImageActivity.this,"保存到"+path,Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
