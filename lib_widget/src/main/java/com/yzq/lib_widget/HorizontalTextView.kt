package com.yzq.lib_widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
    private var contentStr: String?
    private var hintStr: String?
    private var endIconRes: Int = -1
    private var contentLeft = false

    init {

        LayoutInflater.from(context).inflate(R.layout.view_horizontal_text_layout, this)


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalTextView)

        try {
            startIconRes = typeArr.getResourceId(R.styleable.HorizontalTextView_horz_tv_icon, -1)
            endIconRes =
                typeArr.getResourceId(R.styleable.HorizontalTextView_horz_tv_endIcon, -1)
            titleStr = typeArr.getString(R.styleable.HorizontalTextView_horz_tv_title)
            contentStr = typeArr.getString(R.styleable.HorizontalTextView_horz_tv_content)
            hintStr = typeArr.getString(R.styleable.HorizontalTextView_horz_tv_hint)
            contentLeft =
                typeArr.getBoolean(R.styleable.HorizontalTextView_horz_tv_content_left, false)

        } finally {
            typeArr.recycle()
        }


        icon_start.visibility = View.GONE
        icon_end.visibility = View.GONE

        if (startIconRes != -1) {
            icon_start.visibility = View.VISIBLE
            icon_start.setImageResource(startIconRes)
        }
        if (endIconRes != -1) {
            icon_end.visibility = View.VISIBLE
            icon_end.setImageResource(endIconRes)
        }

        tv_title.text = titleStr

        if (contentLeft) {
            tv_content.gravity = Gravity.START
            tv_content.gravity = Gravity.CENTER_VERTICAL
        }
        tv_content.text = contentStr
        tv_content.hint = hintStr


    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        tv_content.text = contentStr
    }


    /**
     * 更改标题
     * @param title String
     */
    fun setTitle(title: String) {
        this.titleStr = title
        tv_title.text = titleStr
    }

    /**
     * 给末尾处icon设置监听
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        tv_content.setOnClickListener(listener)
    }

}