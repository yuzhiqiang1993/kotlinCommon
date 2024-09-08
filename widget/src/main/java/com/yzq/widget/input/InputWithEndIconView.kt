package com.yzq.widget.input

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.yzq.widget.R


/**
 * @description: 带有右侧图标的输入框
 * @author : yuzhiqiang
 */

open class InputWithEndIconView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InputWithCustomEndView(context, attrs, defStyleAttr) {

    var endIcon: AppCompatImageView? = null

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.InputWithEndImageView,
            0, 0
        ).apply {
            try {
                val iconResId = getResourceId(R.styleable.InputWithEndImageView_icon_src, 0)
                val iconSize = getDimensionPixelSize(
                    R.styleable.InputWithEndImageView_icon_size,
                    R.styleable.InputWithEndImageView_icon_size
                )
                val iconPadding = getDimensionPixelSize(
                    R.styleable.InputWithEndImageView_icon_padding,
                    R.styleable.InputWithEndImageView_icon_padding
                )
                val iconTint = getColor(R.styleable.InputWithEndImageView_icon_tint, 0)
                val iconVisibility =
                    getBoolean(R.styleable.InputWithEndImageView_icon_visibility, true)

                setIcon(iconResId, iconSize, iconPadding, iconTint, iconVisibility)
            } finally {
                recycle()
            }
        }
    }

    protected fun setIcon(
        resId: Int,
        size: Int,
        padding: Int,
        tintColor: Int,
        visibility: Boolean
    ) {
        if (endIcon == null) {
            endIcon = AppCompatImageView(context).apply {
                layoutParams = LayoutParams(size, size).apply {
                    gravity = android.view.Gravity.CENTER_VERTICAL
                    setPadding(padding)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            setCustomView(endIcon!!)
        }

        if (resId != 0) {
            endIcon?.setImageDrawable(ContextCompat.getDrawable(context, resId))
        }

        if (tintColor != 0) {
            DrawableCompat.setTint(endIcon?.drawable!!, tintColor)
        }
        endIcon?.visibility = if (visibility) VISIBLE else GONE
    }

    fun updateIcon(resId: Int) {
        endIcon?.setImageDrawable(ContextCompat.getDrawable(context, resId))
    }

    fun updateIconSize(size: Int) {
        endIcon?.layoutParams = endIcon?.layoutParams?.apply {
            width = size
            height = size
        }
    }

    fun updateIconTint(color: Int) {
        endIcon?.drawable?.let {
            DrawableCompat.setTint(it, color)
        }
    }

    fun showIcon() {
        endIcon?.visibility = View.VISIBLE
    }

    fun hideIcon() {
        endIcon?.visibility = View.INVISIBLE
    }


}