package com.yzq.widget.drop_down_menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yzq.widget.databinding.LayoutTabItemBinding

/**
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @description tabItem
 * @date 2022/11/11
 * @time 17:20
 */
class TabItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTabItemBinding =
        LayoutTabItemBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(title: String) {
        binding.tvTabTitle.text = title
    }

    fun setTitleColor(textSelectedColor: Int) {
        binding.tvTabTitle.setTextColor(textSelectedColor)
    }

    fun setIcon(menuSelectedIcon: Int, color: Int) {
        binding.ivTabIcon.run {
            setImageResource(menuSelectedIcon)
            setColorFilter(color)
        }
    }
}
