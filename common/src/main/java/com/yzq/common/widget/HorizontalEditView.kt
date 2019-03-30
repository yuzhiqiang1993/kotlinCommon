package com.yzq.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.common.R
import kotlinx.android.synthetic.main.view_horizontal_edit_layout.view.*


/**
 * @description: 水平ItemView，输入框
 * @author : yzq
 * @date   : 2018/9/1
 * @time   : 14:05
 *
 */

class HorizontalEditView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        ConstraintLayout(context, attrs, defStyleAttr) {


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var iconRes: Int = -1
    private var titleStr: String?
    private var contentStr: String?
    private var endIconRes: Int = -1
    private var editEnable = false


    init {

        LayoutInflater.from(context).inflate(R.layout.view_horizontal_edit_layout, this);


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalEditView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_icon, -1)
            editEnable = typeArr.getBoolean(R.styleable.HorizontalEditView_horz_edit_editEnable, false)
            endIconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_endIcon, -1)
            titleStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_title)
            contentStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_content)

        } finally {
            typeArr.recycle()
        }


        startIconIv.visibility = View.GONE
        endIconIv.visibility = View.GONE


        if (iconRes != -1) {
            startIconIv.visibility = View.VISIBLE
            startIconIv.setImageResource(iconRes)
        }

        if (endIconRes != -1) {
            endIconIv.visibility = View.VISIBLE
            endIconIv.setImageResource(endIconRes)
        }
        titleTv.text = titleStr
        contentEt.setText(contentStr)
        contentEt.isEnabled = editEnable

    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        contentEt.setText(contentStr)
    }

    /**
     * 获取输入框的文本
     * @return String
     */
    fun getContent(): String {
        return contentEt.text.toString().trim()
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
     * 给尾部图标加点击事件
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        endIconIv.setOnClickListener(listener)
    }

}