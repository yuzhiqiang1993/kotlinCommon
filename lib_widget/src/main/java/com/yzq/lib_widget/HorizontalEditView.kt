package com.yzq.lib_widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.yzq.lib_widget.databinding.ViewHorizontalEditLayoutBinding


/**
 * @description: 水平ItemView，输入框
 * @author : yzq
 * @date   : 2018/9/1
 * @time   : 14:05
 *
 */

@SuppressLint("ResourceAsColor")
class HorizontalEditView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var iconRes: Int
    private var iconTint: Int = R.color.primary_icon
    private var titleStr: String?
    private var contentStr: String?
    private var hint: String?
    private var endIconRes: Int
    private var endIconTint: Int = R.color.primary_icon
    private var editEnable = true
    private var inputType: String?


    private val binding: ViewHorizontalEditLayoutBinding =
        ViewHorizontalEditLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {


        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalEditView)

        try {
            iconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_icon, -1)
            iconTint = typeArr.getColor(
                R.styleable.HorizontalEditView_horz_edit_icon_tint,
                R.color.primary_icon
            )
            inputType =
                typeArr.getString(R.styleable.HorizontalEditView_horz_edit_inputType)
            editEnable =
                typeArr.getBoolean(R.styleable.HorizontalEditView_horz_edit_editEnable, true)
            endIconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_endIcon, -1)

            endIconTint = typeArr.getColor(
                R.styleable.HorizontalEditView_horz_edit_end_icon_tint,
                R.color.primary_icon
            )
            titleStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_title)
            contentStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_content)
            hint = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_hint)

        } finally {
            typeArr.recycle()
        }


        binding.run {
            /*默认隐藏图标*/

            iconStart.visibility = View.GONE

            iconEnd.visibility = View.GONE

            /*显示前面的图标*/
            if (iconRes != -1) {

                iconStart.visibility = View.VISIBLE
                iconStart.setImageResource(iconRes)
                iconStart.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    iconTint, BlendModeCompat.SRC_ATOP
                )
            }


            /*显示后面的图标*/
            if (endIconRes != -1) {
                iconEnd.visibility = View.VISIBLE
                iconEnd.setImageResource(endIconRes)
                iconEnd.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    endIconTint, BlendModeCompat.SRC_ATOP
                )
            }

            /*设置inputType*/
            when (inputType) {

                "0" -> inputContent.inputType = InputType.TYPE_CLASS_PHONE
                "1" -> inputContent.inputType = InputType.TYPE_CLASS_NUMBER
                "2" -> inputContent.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                else -> inputContent.inputType = InputType.TYPE_CLASS_TEXT
            }

            tvTitle.text = titleStr
            inputContent.hint = hint
            inputContent.setText(contentStr)
            inputContent.isEnabled = editEnable
        }


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