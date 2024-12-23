package com.yzq.login.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.yzq.base.extend.immersiveRes
import com.yzq.login.R
import com.yzq.login.manager.PageManager


/**
 * @description: 全屏的activity基类
 * @author : yuzhiqiang
 */

open class BasePopupActivity : BaseLoginActivity() {

    protected var bottomSheetView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveRes(com.yzq.resource.R.color.popup_bg, false)

        if (PageManager.hasTopPage()) {
            overridePendingTransition(0, 0)
        } else {
            overridePendingTransition(R.anim.slide_in_bottom, 0)
        }


    }


    fun animateFinish() {
        bottomSheetView?.run {
            val animator =
                ObjectAnimator.ofFloat(this, "translationY", 0f, height.toFloat()).apply {
                    duration = 200
                    interpolator = DecelerateInterpolator()
                }
            animator.doOnEnd {
                super.finish()
                overridePendingTransition(0, R.anim.fade_out)
            }
            animator.start()
        } ?: run {
            super.finish()
            overridePendingTransition(0, R.anim.slide_out_top)
        }
    }
}