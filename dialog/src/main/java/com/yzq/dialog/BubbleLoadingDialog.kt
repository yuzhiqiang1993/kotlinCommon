package com.yzq.dialog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.FragmentActivity
import com.yzq.coroutine.safety_coroutine.runMain
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.databinding.LayoutBubbleDialogBinding


/**
 * @description: 菊花loading弹窗
 * @author : yuzhiqiang
 */

class BubbleLoadingDialog(activity: FragmentActivity) :
    BaseDialogFragment<BubbleLoadingDialog>(activity) {

    private var loadingContent: String = "加载中"
    private var objectAnimator: ObjectAnimator? = null

    private val binding: LayoutBubbleDialogBinding by lazy {
        LayoutBubbleDialogBinding.inflate(layoutInflater)
    }


    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun initWidget(contentView: View) {
        val rotateDrawable = binding.ivLoading.background as RotateDrawable
        objectAnimator = ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
        loadingContent = requireContext().resources.getString(R.string.loading)
        binding.tvLoading.text = loadingContent
    }


    fun content(content: String): BubbleLoadingDialog {
        this.loadingContent = content
        if (isShowing()) {
            runMain {
                binding.tvLoading.text = loadingContent
            }
        }
        return this
    }


    override fun safeDismiss() {
        //这里要取消动画，不然会内存泄漏
        objectAnimator?.cancel()
        super.safeDismiss()
    }


}