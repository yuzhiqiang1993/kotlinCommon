package com.yzq.login.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.login.R
import com.yzq.login.databinding.ViewTitleBackBinding


/**
 *
 * @description:  标题栏, 带有返回按钮
 * @author : yuzhiqiang
 *
 */

class TitleBackView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewTitleBackBinding =
        ViewTitleBackBinding.inflate(LayoutInflater.from(context), this, true)

    private var onBackIvClick: (() -> Unit)? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleBackView,
            0, 0
        ).apply {
            try {
                val titleText = getString(R.styleable.TitleBackView_titleText)
                setTitleText(titleText ?: "")

                // 设置返回按钮点击事件
                binding.ivBack.setOnClickListener {
                    onBackIvClick?.invoke()
                }

            } finally {
                recycle()
            }
        }
    }


    private fun setTitleText(title: String) {
        binding.tvTitle.text = title
    }

    /**
     * 设置返回按钮点击监听
     */
    fun onBackIvClick(listener: (() -> Unit)?) {
        onBackIvClick = listener
    }
}