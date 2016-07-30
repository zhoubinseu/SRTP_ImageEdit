package com.seu.srtp_imageedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.seu.srtp_imageedit.Rotate.RotateBottomFragment;

/**
 * Created by Administrator on 2016/7/29.
 */
public class EditImageActivity extends AppCompatActivity{

    private static final String IMAGE_PATH="com.seu.srtp.imageedit.image_path";
    private static final String FUNCTION_CODE="com.seu.srtp.imageedit.function_code";
    private ImageView mImageView;
    private String mImagePath;//当前图片的路径
    private int mFunctionNameId;//当前界面的处理功能ID

    /**
     * 由其他Activity或Fragment调用，启动EditImageActivity
     * @param packageContext
     * @param path
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
        setContentView(R.layout.edit_image_activity);

        mImagePath=getIntent().getStringExtra(IMAGE_PATH);
        mFunctionNameId=getIntent().getIntExtra(FUNCTION_CODE,R.string.tool_adjust);

        mImageView=(ImageView)findViewById(R.id.image_to_edit);
        Image.showImage(mImagePath,mImageView);//显示图片
        //判断功能号，加载碎片，默认布局为旋转操作的布局
        judgeFunction(mFunctionNameId);
    }

    /**
     * 根据接收到的功能名称ID，加载该功能对应的布局
     * @param functionNameId
     */
    private void judgeFunction(int functionNameId){
        switch (functionNameId){
            //旋转
            case R.string.tool_rotate:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                FragmentManager fm=getSupportFragmentManager();
                Fragment fragment=fm.findFragmentById(R.id.bottom_fragment_container);
                if(fragment==null){
                    fragment=new RotateBottomFragment();
                    fm.beginTransaction().add(R.id.bottom_fragment_container,fragment).commit();
                }
                break;
            //调整
            case R.string.tool_adjust:
                Toast.makeText(EditImageActivity.this,functionNameId,Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(EditImageActivity.this,"Have not write!!!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
