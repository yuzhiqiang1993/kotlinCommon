package com.yzq.lib_widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.lib_widget.databinding.ViewHorizontalTextLayoutBinding


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
    private val binding: ViewHorizontalTextLayoutBinding

    init {

        binding = ViewHorizontalTextLayoutBinding.inflate(LayoutInflater.from(context), this, true)


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




        binding.iconStart.visibility = View.GONE

        binding.iconEnd.visibility = View.GONE

        if (startIconRes != -1) {
            binding.iconStart.visibility = View.VISIBLE
            binding.iconStart.setImageResource(startIconRes)
        }
        if (endIconRes != -1) {
            binding.iconEnd.visibility = View.VISIBLE
            binding.iconEnd.setImageResource(endIconRes)
        }


        binding.tvTitle.text = titleStr

        if (contentLeft) {

            binding.tvContent.gravity = Gravity.START
            binding.tvContent.gravity = Gravity.CENTER_VERTICAL
        }
        binding.tvContent.text = contentStr
        binding.tvContent.hint = hintStr


    }


    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        binding.tvContent.text = contentStr
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
     * 给末尾处icon设置监听
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        binding.tvContent.setOnClickListener(listener)
    }

}