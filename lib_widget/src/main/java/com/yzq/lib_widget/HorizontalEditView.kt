package com.yzq.lib_widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.lib_widget.databinding.ViewHorizontalEditLayoutBinding


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
    private var inputType: String?


    private val binding: ViewHorizontalEditLayoutBinding

    init {

        binding = ViewHorizontalEditLayoutBinding.inflate(LayoutInflater.from(context), this, true)


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalEditView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_icon, -1)
            inputType =
                typeArr.getString(R.styleable.HorizontalEditView_horz_edit_inputType)
            editEnable =
                typeArr.getBoolean(R.styleable.HorizontalEditView_horz_edit_editEnable, true)
            endIconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_endIcon, -1)
            titleStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_title)
            contentStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_content)
            hint = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_hint)

        } finally {
            typeArr.recycle()
        }

        /*默认隐藏图标*/

        binding.iconStart.visibility = View.GONE

        binding.iconEnd.visibility = View.GONE

        /*显示前面的图标*/
        if (iconRes != -1) {
            binding.iconStart.visibility = View.VISIBLE
            binding.iconStart.setImageResource(iconRes)
        }

        /*显示后面的图标*/
        if (endIconRes != -1) {
            binding.iconEnd.visibility = View.VISIBLE
            binding.iconEnd.setImageResource(endIconRes)
        }

        /*设置inputType*/
        when (inputType) {

            "0" -> binding.inputContent.inputType = InputType.TYPE_CLASS_PHONE
            "1" -> binding.inputContent.inputType = InputType.TYPE_CLASS_NUMBER
            "2" -> binding.inputContent.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            else -> binding.inputContent.inputType = InputType.TYPE_CLASS_TEXT
        }

        binding.tvTitle.text = titleStr
        binding.inputContent.hint = hint
        binding.inputContent.setText(contentStr)
        binding.inputContent.isEnabled = editEnable

    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        binding.inputContent.setText(contentStr)
    }

    /**
     * 获取输入框的文本
     * @return String
     */
    fun getContent(): String {
        return binding.inputContent.text.toString().trim()
    }

    /**
     * 更改标题
     * @param title String
     */
    fun setTitle(title: String) {
        this.titleStr = title
        binding.tvTitle.text = titleStr
    }

    /**
     * 给尾部图标加点击事件
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        binding.iconEnd.setOnClickListener(listener)
    }

    /**
     * 设置编辑框是可用
     * @param b Boolean
     */
    fun setEditEnable(b: Boolean) {
        binding.inputContent.isEnabled = b
    }

}