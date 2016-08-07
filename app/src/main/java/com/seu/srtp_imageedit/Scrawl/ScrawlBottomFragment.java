package com.seu.srtp_imageedit.Scrawl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.seu.srtp_imageedit.Image;
import com.seu.srtp_imageedit.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScrawlBottomFragment extends Fragment implements View.OnClickListener{
    private ImageView mImageView;
    private Bitmap mDestBitmap;//最终处理完的图片
    private Button mStyleButton;
    private Button mColorButton;
    private Button mSizeButton;
    private Button mRubberButton;

    private Canvas mCanvas;//画板
    private Bitmap mBitmap;//画布
    private Paint mPaint;//画笔
    private int downX;
    private int downY;

    class MyOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //获取当前的事件，来判断一下，当前是按下，移动，抬起。
            int action=event.getAction();//当前用户产生的动作
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("按下");
//初始化画纸
                    InitPanel();
//得到按下时的x、y值
                    downX=(int) event.getX();
                    downY=(int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("移动");
//取出移动下的x y值
                    int moveX=(int) event.getX();
                    int moveY=(int) event.getY();
//把按下的店和移动的点，两个点连成线
/*startX:按下的x
*startY:按下的y
*stopX:移动的x
*stopY:移动的y
*/
                    mCanvas.drawLine(downX, downY, moveX, moveY, mPaint);
                    mImageView.setImageBitmap(mBitmap);//把画完线的图片设置给imageView控件显示
//把按下的x和按下的y更新成移动后的x和y的值
                    downX=moveX;//将移动的x轴的值，赋值给按下的x轴的值，为了方便下一次进行连线
                    downY=moveY;//将移动的y轴的值，赋值给按下的y轴的值，为了方便下一次进行连线
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("抬起");
                    break;
                default:
                    break;
            }
            return true;//true 消费当前的事件，false不消费事件，有其他人来处理
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_fragment_scrawl,container,false);

        mImageView=(ImageView)getActivity().findViewById(R.id.image_to_edit);
        mStyleButton=(Button)v.findViewById(R.id.choose_brush_style);
        mColorButton=(Button)v.findViewById(R.id.choose_brush_color);
        mSizeButton=(Button)v.findViewById(R.id.choose_brush_size);
        mRubberButton=(Button)v.findViewById(R.id.choose_brush_rubber);
        mStyleButton.setOnClickListener(this);
        mColorButton.setOnClickListener(this);
        mSizeButton.setOnClickListener(this);
        mRubberButton.setOnClickListener(this);

        mImageView.setOnTouchListener(new MyOnTouchListener());

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_brush_style:
                break;
            case R.id.choose_brush_color:
                break;
            case R.id.choose_brush_size:
                break;
            case R.id.choose_brush_rubber:
                break;
            default:
                break;
        }
    }

    /*
* 初始化画纸
*/
    private void InitPanel(){
        if(mBitmap==null){
//Canvas画板、bitmap画纸、画笔
            //创建一张空白的画纸，指定宽和高和ImageView中显示的图片内容的宽高一模一样
            //mBitmap=Bitmap.createBitmap(mImageView.getWidth(),mImageView.getHeight(), Bitmap.Config.ARGB_8888);
            mBitmap=Image.DrawableToBitmap(mImageView.getDrawable());
            //mBitmap=Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight());
//初始化一个画板。
            mCanvas=new Canvas(mBitmap);
//初始化一个画笔，指定画笔的颜色，指定画笔的画出来线的粗细
            mPaint=new Paint();
            mPaint.setColor(Color.GREEN);//指定画笔的颜色
            mPaint.setStrokeWidth(20);//指定画笔画出来的线的宽度
//用画板向画纸画一个黄色的颜色
            mCanvas.drawColor(Color.YELLOW);//这句代码执行完毕后，panel画纸的颜色就可以变成黄色
//把画纸给imageView控件来显示
            mImageView.setImageBitmap(mBitmap);
        }
    }


}
