package com.yzq.login.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.login.R
import com.yzq.login.databinding.ViewPopupTitleBinding


/**
 * @description: 弹窗标题视图
 * @author : yuzhiqiang
 */

class PopupTitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewPopupTitleBinding =
        ViewPopupTitleBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PopupTitleView,
            0, 0
        ).apply {
            try {
                val startText = getString(R.styleable.PopupTitleView_titleStart)
                val endText = getString(R.styleable.PopupTitleView_titleEnd)
                binding.tvTitleStart.setText(startText ?: "")
                binding.tvTitleEnd.setText(endText ?: "")
            } finally {
                recycle()
            }
        }
    }


    fun titleStartOnClick(listener: OnClickListener) {
        binding.tvTitleStart.setOnClickListener(listener)
    }

    fun titleEndOnClick(listener: OnClickListener) {
        binding.tvTitleEnd.setOnClickListener(listener)
    }

}