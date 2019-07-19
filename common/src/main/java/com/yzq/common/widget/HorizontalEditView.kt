package com.yzq.common.widget

import android.content.Context
import android.text.InputType
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

    private var iconRes: Int
    private var titleStr: String?
    private var contentStr: String?
    private var hint: String?
    private var endIconRes: Int
    private var editEnable = true
    private var inputType: Int


    init {

        LayoutInflater.from(context).inflate(R.layout.view_horizontal_edit_layout, this);


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalEditView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_icon, -1)
            inputType = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_inputType, -1)
            editEnable = typeArr.getBoolean(R.styleable.HorizontalEditView_horz_edit_editEnable, true)
            endIconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_endIcon, -1)
            titleStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_title)
            contentStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_content)
            hint = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_hint)

        } finally {
            typeArr.recycle()
        }

        /*默认隐藏图标*/
        icon_start.visibility = View.GONE
        icon_end.visibility = View.GONE

        /*显示前面的图标*/
        if (iconRes != -1) {
            icon_start.visibility = View.VISIBLE
            icon_start.setImageResource(iconRes)
        }

        /*显示后面的图标*/
        if (endIconRes != -1) {
            icon_end.visibility = View.VISIBLE
            icon_end.setImageResource(endIconRes)
        }

        /*设置inputType*/
        when (inputType) {
            0 -> input_content.inputType = InputType.TYPE_CLASS_PHONE
            1 -> input_content.inputType = InputType.TYPE_CLASS_NUMBER
            2 -> input_content.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        tv_title.text = titleStr
        input_content.setHint(hint)
        input_content.setText(contentStr)
        input_content.isEnabled = editEnable

    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        input_content.setText(contentStr)
    }

    /**
     * 获取输入框的文本
     * @return String
     */
    fun getContent(): String {
        return input_content.text.toString().trim()
    }

    /**
     * 更改标题
     * @param title String
     */
    fun setTitle(title: String) {
        this.titleStr = title
        tv_title.setText(titleStr)
    }

    /**
     * 给尾部图标加点击事件
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        icon_end.setOnClickListener(listener)
    }

    /**
     * 设置编辑框是可用
     * @param b Boolean
     */
    fun setEditEnable(b: Boolean) {
        input_content.isEnabled = b
    }

}