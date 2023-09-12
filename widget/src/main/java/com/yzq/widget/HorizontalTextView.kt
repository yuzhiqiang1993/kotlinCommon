package com.yzq.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.*
import com.yzq.widget.databinding.ViewHorizontalTextLayoutBinding

/**
 * @description: 水平ItemView，文本框
 * @author : yzq
 * @date : 2018/9/1
 * @time : 14:05
 *
 */

@SuppressLint("ResourceAsColor")
class HorizontalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var startIconRes = -1
    private var iconTint = R.color.primary_icon
    private var titleStr = ""
    private var contentStr = ""
    private var hintStr = ""
    private var endIconRes = -1
    private var endIconTint = R.color.primary_icon
    private var contentLeft = false
    private val binding =

        ViewHorizontalTextLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        /*可以代替传统的try catch*/
        context.withStyledAttributes(attrs, R.styleable.HorizontalTextView) {
            startIconRes = getResourceId(R.styleable.HorizontalTextView_horz_tv_icon, -1)
            iconTint = getColor(
                R.styleable.HorizontalTextView_horz_tv_icon_tint,
                R.color.primary_icon
            )
            endIconRes =
                getResourceId(R.styleable.HorizontalTextView_horz_tv_endIcon, -1)

            endIconTint = getColor(
                R.styleable.HorizontalEditView_horz_edit_end_icon_tint,
                R.color.primary_icon
            )

            titleStr = getString(R.styleable.HorizontalTextView_horz_tv_title) ?: ""
            contentStr = getString(R.styleable.HorizontalTextView_horz_tv_content) ?: ""
            hintStr = getString(R.styleable.HorizontalTextView_horz_tv_hint) ?: ""
            contentLeft =
                getBoolean(R.styleable.HorizontalTextView_horz_tv_content_left, false)
        }

        binding.run {
            iconStart.visibility = View.GONE

            iconEnd.visibility = View.GONE

            if (startIconRes != -1) {

                iconStart.visibility = View.VISIBLE
                iconStart.setImageResource(startIconRes)
                iconStart.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    iconTint, BlendModeCompat.SRC_ATOP
                )
            }
            if (endIconRes != -1) {
                iconEnd.visibility = View.VISIBLE
                iconEnd.setImageResource(endIconRes)
                iconEnd.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    endIconTint, BlendModeCompat.SRC_ATOP
                )
            }

            tvTitle.text = titleStr

            if (contentLeft) {

                tvContent.gravity = Gravity.START
                tvContent.gravity = Gravity.CENTER_VERTICAL
            }
            tvContent.text = contentStr
            tvContent.hint = hintStr
        }
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
