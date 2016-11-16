package com.seu.srtp_imageedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.seu.srtp_imageedit.Adjust.AdjustBottomFragment;
import com.seu.srtp_imageedit.Effect.EffectBottomFragment;
import com.seu.srtp_imageedit.Filter.FilterBottomFragment;
import com.seu.srtp_imageedit.Frame.FrameBottomFragment;
import com.seu.srtp_imageedit.Rotate.RotateBottomFragment;
import com.seu.srtp_imageedit.Scrawl.ScrawlBottomFragment;
import com.seu.srtp_imageedit.Sharpen.SharpenBottomFragment;
import com.seu.srtp_imageedit.Vignette.VignetteBottomFragment;

import java.io.File;

/**
 * 编辑图片的Activity
 * Created by 周彬 on 2016/7/29.
 */
public class EditImageActivity extends AppCompatActivity{

    private static final String IMAGE_PATH="com.seu.srtp.imageedit.image_path";
    private static final String FUNCTION_CODE="com.seu.srtp.imageedit.function_code";

    private File PHOTO_DIR=new File(Environment.getExternalStorageDirectory() + "/SRTP_ImageEdit/TempApplyPic");

    private ImageView mImageView;
    private ImageButton mCancelButton;//取消按钮，取消对图片做的处理
    private ImageButton mApplyButton;//应用按钮，应用对图片的处理

    private String mImagePath;//当前图片的路径
    private int mFunctionNameId;//当前界面的处理功能ID

    /**
     * 由其他Activity或Fragment调用，启动EditImageActivity
     * @param packageContext
     * @param path 传入此界面进行处理的图片的路径
     * @return
     */
    public static Intent newIntent(Context packageContext,String path,int functionNameId){
        Intent intent=new Intent(packageContext,EditImageActivity.class);
        intent.putExtra(IMAGE_PATH, path);
        intent.putExtra(FUNCTION_CODE,functionNameId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image_activity);//加载布局

        mImagePath=getIntent().getStringExtra(IMAGE_PATH);//从Intent对象中获得图片路径
        mFunctionNameId=getIntent().getIntExtra(FUNCTION_CODE, R.string.tool_adjust);//获得当前界面要进行的处理效果

        mImageView=(ImageView)findViewById(R.id.image_to_edit);
        //显示经过缩放的图片,可有效防止OOM
        Image.showScaledImage(mImagePath,mImageView);
        //判断功能号，加载碎片，默认布局为旋转操作的布局
        judgeFunction(mFunctionNameId);

        //取消和应用按钮的点击事件
        mCancelButton=(ImageButton)findViewById(R.id.cancel_button);
        //每次需要从外部存储读取，较慢，待改进
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image.showImage(mImagePath, mImageView);//显示原图
                //将Constant中的部分常量重新设置
                Constant.getInstance().setRotateCounter(0);//重置旋转计数器
            }
        });
        mApplyButton=(ImageButton)findViewById(R.id.confirm_button);
        //每次都要先进行保存，获得路径，耗时，待改进
        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path=Image.saveImage(mImageView,PHOTO_DIR).getAbsolutePath();
                startActivity(ShowImageActivity.newIntent(EditImageActivity.this,path));
            }
        });
    }

    /**
     * 添加Fragment对象
     * @param fragment
     */
    private void addFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.bottom_fragment_container,fragment).commit();
    }

    /**
     * 根据接收到的功能名称ID，加载该功能对应的布局
     * @param functionNameId
     */
    private void judgeFunction(int functionNameId){
        switch (functionNameId){
            //调整
            case R.string.tool_adjust:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new AdjustBottomFragment());
                break;
            //旋转
            case R.string.tool_rotate:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new RotateBottomFragment());
                break;
            //相框
            case R.string.filter_frame:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new FrameBottomFragment());
                break;
            //特效
            case R.string.effect:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new EffectBottomFragment());
                break;
            //锐化
            case R.string.sharpen:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new SharpenBottomFragment());
                break;
            //晕影
            case R.string.effect_vignette:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                addFragment(new VignetteBottomFragment());
                break;
            default:
                Toast.makeText(EditImageActivity.this,"Have not write!!!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
