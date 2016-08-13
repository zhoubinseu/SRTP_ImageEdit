package com.seu.srtp_imageedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ShowImageActivity extends AppCompatActivity{
    private static final String IMAGE_PATH="com.seu.srtp.imageedit.image_path";
    private File  PHOTO_DIR=new File(Environment.getExternalStorageDirectory() + "/SRTP_ImageEdit/CameraCache");

    private List<Function_Item> mFunctionList=new ArrayList<Function_Item>();//功能列表
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ImageView mImageView;
    //private Bitmap mBitmap;//显示在ImageView上的图片

    private String mImagePath;//当前图片的路径

    /**
     * 由其他Activity或Fragment调用，启动ShowImageActivity
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
        mDrawerLayout=(DrawerLayout)findViewById(R.id.show_image_drawer_layout);
        mListView=(ListView)findViewById(R.id.choose_operation_list);
        mImageView=(ImageView)findViewById(R.id.show_image_image_view);

        initFunctionList();
        Function_Item_Adapter adapter=new Function_Item_Adapter(ShowImageActivity.this,R.layout.operation_list_item,mFunctionList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function_Item item=mFunctionList.get(position);
                //Toast.makeText(ShowImageActivity.this,item.getmFuntionNameId(),Toast.LENGTH_SHORT).show();
                //将图片的路径和选择的功能的名称ID作为Intent的参数，打开EditImageActivity
                startActivity(EditImageActivity.newIntent(ShowImageActivity.this,mImagePath,item.getmFuntionNameId()));
            }
        });

        mImagePath=getIntent().getStringExtra(IMAGE_PATH);
        Toast.makeText(ShowImageActivity.this,mImagePath,Toast.LENGTH_SHORT).show();
        //此处显示图片不必缩放
        Image.showImage(mImagePath, mImageView);
    }

    /**
     * 初始化功能项列表的数据
     */
    private void initFunctionList(){
        Function_Item adjust_item=new Function_Item(R.string.tool_adjust,R.drawable.ic_adjust_button);
        mFunctionList.add(adjust_item);
        Function_Item rotate_item=new Function_Item(R.string.tool_rotate,R.drawable.ic_right_rotate_button);
        mFunctionList.add(rotate_item);
        Function_Item frame_item=new Function_Item(R.string.filter_frame,R.drawable.ic_frame_button);
        mFunctionList.add(frame_item);
        Function_Item scrawl_item=new Function_Item(R.string.tool_scrawl,R.drawable.ic_brush_button);
        mFunctionList.add(scrawl_item);
        Function_Item sharpen_item=new Function_Item(R.string.sharpen,R.drawable.ic_sharpen_button);
        mFunctionList.add(sharpen_item);
        Function_Item effect_item=new Function_Item(R.string.effect,R.drawable.ic_effect_button);
        mFunctionList.add(effect_item);
        Function_Item vignette_item=new Function_Item(R.string.effect_vignette,R.drawable.ic_vignette_button);
        mFunctionList.add(vignette_item);
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
                //mImageView.setDrawingCacheEnabled(true);//确保可以使用getDrawingCache()获得图像
                //String path=Image.saveImage(mImageView.getDrawingCache(),PHOTO_DIR).getAbsolutePath();
                //mImageView.setDrawingCacheEnabled(false);//清空画图缓存区,这样保证连续保存时每次重新加载图像
                String path=Image.saveImage(mImageView,PHOTO_DIR).getAbsolutePath();
                Toast.makeText(ShowImageActivity.this,"保存到"+path,Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
