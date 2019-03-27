package com.yzq.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.common.R
import kotlinx.android.synthetic.main.view_horizontal_text_layout.view.*


/**
 * @description: 水平ItemView，文本框
 * @author : yzq
 * @date   : 2018/9/1
 * @time   : 14:05
 *
 */

class HorizontalTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        ConstraintLayout(context, attrs, defStyleAttr) {


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var startIconRes: Int = -1
    private var titleStr: String?
    var contentStr: String?
    private var endIconRes: Int = -1
    private var contentLeft = false

    init {

        LayoutInflater.from(context).inflate(R.layout.view_horizontal_text_layout, this);


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalTextView)

        try {
            startIconRes = typeArr.getResourceId(R.styleable.HorizontalTextView_horz_tv_icon, -1)
            endIconRes =
                    typeArr.getResourceId(R.styleable.HorizontalTextView_horz_tv_endIcon, -1)
            titleStr = typeArr.getString(R.styleable.HorizontalTextView_horz_tv_title)
            contentStr = typeArr.getString(R.styleable.HorizontalTextView_horz_tv_content)
            contentLeft = typeArr.getBoolean(R.styleable.HorizontalTextView_horz_tv_content_left, false)

        } finally {
            typeArr.recycle()
        }


        startIconIv.visibility = View.GONE
        endIconIv.visibility = View.GONE

        if (startIconRes != -1) {
            startIconIv.visibility = View.VISIBLE
            startIconIv.setImageResource(startIconRes)
        }
        if (endIconRes != -1) {
            endIconIv.visibility = View.VISIBLE
            endIconIv.setImageResource(endIconRes)
        }

        titleTv.text = titleStr

        if (contentLeft) {
            contentTv.gravity = Gravity.LEFT
        }
        contentTv.setText(contentStr)


    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content

        contentTv.setText(contentStr)
    }


    /**
     * 更改标题
     * @param title String
     */
    fun setTitle(title: String) {
        this.titleStr = title
        titleTv.setText(titleStr)
    }

    /**
     * 给末尾处icon设置监听
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        contentTv.setOnClickListener(listener)
    }

}