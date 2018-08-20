package com.yzq.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


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

        MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();


        /*   先画圆角矩形*/

     //   RectF rectF=new RectF(getLeft(),);



        /*在圆角矩形中画文字*/
        // canvas.drawText(text);


        /*画进度*/


    }


}
