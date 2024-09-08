package com.yzq.login.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yzq.login.databinding.ViewPopupHeaderBinding


/**
 * @description: 弹窗头部视图
 * @author : yuzhiqiang
 */

class PopupHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewPopupHeaderBinding =
        ViewPopupHeaderBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        binding.ivBack.visibility = View.GONE
    }

    fun onIvBackClick(listener: OnClickListener) {
        binding.ivBack.setOnClickListener(listener)
    }

    fun onIvCloseClick(listener: OnClickListener) {
        binding.ivClose.setOnClickListener(listener)
    }


    fun showClose(show: Boolean) {
        binding.ivClose.visibility = if (show) VISIBLE else GONE
    }

    fun showBack(show: Boolean) {
        binding.ivBack.visibility = if (show) VISIBLE else GONE
    }
}