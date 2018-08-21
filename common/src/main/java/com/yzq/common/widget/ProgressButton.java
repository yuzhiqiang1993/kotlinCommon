package com.yzq.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;


/**
 * @author : yzq
 * @description: 自定义下载按钮
 * @date : 2018/8/20
 * @time : 17:22
 */

public class ProgressButton extends View {

    /*当前进度*/
    private int currentProgress = 0;
    /*总进度*/
    private int totalProgress = 100;

    /*文字*/
    private String text = "下载";

    /*圆角矩形画笔*/


    /*new的时候调用*/
    public ProgressButton(Context context) {
        this(context, null);
    }

    /*在XML布局中使用时调用*/
    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*获取宽度*/
        int width = MeasureSpec.getSize(widthMeasureSpec);
        /*获取宽度测量模式*/
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        /*获取高度*/
        int height = MeasureSpec.getSize(heightMeasureSpec);
        /*高度的测量模式*/
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        LogUtils.i("宽度：" + width + "模式：" + widthMode);
        LogUtils.i("高度：" + height + "模式：" + heightMode);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        LogUtils.i("onSizeChanged宽度：" + w);
        LogUtils.i("onSizeChanged高度：" + h);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        LogUtils.i("onSizeChanged宽度：" + width);
        LogUtils.i("onSizeChanged高度：" + height);


        /*   先画圆角矩形*/

        //  RectF rectF=new RectF(getLeft(),);



        /*在圆角矩形中画文字*/
        // canvas.drawText(text);


        /*画进度*/


    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    public static int format2Int(double i) {
        return (int) i;
    }
}
