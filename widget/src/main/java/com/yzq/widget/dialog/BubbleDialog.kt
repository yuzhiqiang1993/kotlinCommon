package com.yzq.widget.dialog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import com.yzq.coroutine.runMain
import com.yzq.widget.R
import com.yzq.widget.databinding.LayoutBubbleDialogBinding


/**
 * @description ios风格的loading框
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/2
 * @time    15:39
 */

class BubbleDialog @JvmOverloads constructor(
    context: Context,
    var title: String = context.getString(R.string.loading),
    private val canceledOnTouchOutside: Boolean = false,
    @StyleRes themeResId: Int = R.style.BubbleDialog,
) : AppCompatDialog(context, themeResId) {


    private lateinit var binding: LayoutBubbleDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutBubbleDialogBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.tvTitle.text = title
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        val rotateDrawable = binding.ivLoading.background as RotateDrawable
        ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    fun showLoading(): BubbleDialog {
        runMain {
            super.show()
        }

        return this
    }

    override fun dismiss() {
        runMain {
            super.dismiss()
        }

    }

    /**
     * 更新标题文本
     */
    fun updateTitle(text: String): BubbleDialog {
        if (isShowing) {
            runMain {
                binding.tvTitle.text = text
            }
        } else {
            title = text
        }

        return this
    }
}