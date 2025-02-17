package com.yzq.kotlincommon.ui.fragment.manager

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.tabs.TabLayout
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.ui.fragment.data.PortalTab


/**
 * @description:PortalBarManager
 * @author : yuzhiqiang
 */
class PortalBarManager(private val tabLayout: TabLayout) {

    companion object {
        private const val TAG = "PortalBarManager"
        private const val DEFAULT_ANIMATION_DURATION = 1000L
        private const val FIXED_TAB_HEIGHT_DP = 18//tab icon的高度
        private const val TAB_PADDING_DP = 10//tab icon的左右padding
    }

    //像素密度
    private val density by lazy { tabLayout.context.resources.displayMetrics.density }

    /**
     * 初始化单个Tab
     */
    fun initTabIcon(tab: TabLayout.Tab, position: Int, portalTabList: List<PortalTab>) {
        tab.setCustomView(R.layout.portal_tab_view)
        tab.customView?.let { tabView ->
            setupTabViews(tabView, portalTabList[position], tab.isSelected)
        }
    }

    /**
     * 设置Tab视图
     */
    private fun setupTabViews(tabView: View, portalTab: PortalTab, isSelected: Boolean) {
        val ivTab = tabView.findViewById<AppCompatImageView>(R.id.iv_tab)
        val ivTabSelected = tabView.findViewById<AppCompatImageView>(R.id.iv_tab_selected)

        ivTab.setImageResource(portalTab.icon)
        ivTabSelected.setImageResource(portalTab.selectedIcon)

        // 计算并设置Tab尺寸
        val fixedHeightPx = (FIXED_TAB_HEIGHT_DP * density).toInt()//tab的高度
        val paddingPx = (TAB_PADDING_DP * density).toInt()//左右的padding
        val calculatedWidthPx =
            ((fixedHeightPx * ivTab.drawable.intrinsicWidth) / ivTab.drawable.intrinsicHeight + paddingPx * 2)//tab的的宽度
        val layoutParams = ViewGroup.LayoutParams(calculatedWidthPx, fixedHeightPx)
        ivTab.layoutParams = layoutParams
        ivTabSelected.layoutParams = layoutParams

        // 设置透明度
        setTabAlpha(ivTab, ivTabSelected, isSelected)
    }


    /**
     * 设置Tab透明度
     */
    private fun setTabAlpha(
        ivTab: AppCompatImageView, ivTabSelected: AppCompatImageView, isSelected: Boolean
    ) {
        ivTab.alpha = if (isSelected) 0f else 1f
        ivTabSelected.alpha = if (isSelected) 1f else 0f
    }

    /**
     * 更新Tab选中状态
     */
    fun updateTabSelection(position: Int, portalTabList: List<PortalTab>) {
        portalTabList.indices.forEach { i ->
            tabLayout.getTabAt(i)?.customView?.let { tabView ->
                val ivTab = tabView.findViewById<AppCompatImageView>(R.id.iv_tab)
                val ivTabSelected = tabView.findViewById<AppCompatImageView>(R.id.iv_tab_selected)
                setTabAlpha(ivTab, ivTabSelected, i == position)
            }
        }
    }

    /**
     * 滑动时更新Tab状态
     */
    fun updateTabOnScroll(position: Int, positionOffset: Float) {
        // 更新当前Tab
        updateTabAlphaOnScroll(position, positionOffset)
        // 更新下一个Tab
        updateTabAlphaOnScroll(position + 1, 1 - positionOffset)
    }

    private fun updateTabAlphaOnScroll(position: Int, alpha: Float) {
        tabLayout.getTabAt(position)?.customView?.let { tabView ->
            tabView.findViewById<AppCompatImageView>(R.id.iv_tab).alpha = alpha
            tabView.findViewById<AppCompatImageView>(R.id.iv_tab_selected).alpha = 1 - alpha
        }
    }

    fun hidePortalBar() {
        tabLayout.visibility = View.INVISIBLE
    }

    /**
     * 显示PortalBar，支持动画
     */
    fun showPortalBar(animate: Boolean, duration: Long = DEFAULT_ANIMATION_DURATION) {
        if (!animate) {
            ensureTabLayoutVisible()
            return
        }

        val originalWidth = tabLayout.layoutParams?.width ?: ViewGroup.LayoutParams.WRAP_CONTENT

        try {
            startShowAnimation(duration, originalWidth)
        } catch (e: Exception) {
            ensureTabLayoutVisible(originalWidth)
        }
    }

    private fun startShowAnimation(duration: Long, originalWidth: Int) {
        tabLayout.visibility = View.INVISIBLE

        tabLayout.post {
            val actualWidth = tabLayout.measuredWidth.takeIf { it > 0 } ?: run {
                ensureTabLayoutVisible(originalWidth)
                return@post
            }

            createShowAnimator(actualWidth, duration).start()
        }
    }

    private fun createShowAnimator(targetWidth: Int, duration: Long): ValueAnimator {
        return ValueAnimator.ofInt(0, targetWidth).apply {
            this.duration = duration
            interpolator = DecelerateInterpolator()

            addUpdateListener { animation ->
                try {
                    updateTabLayoutWidth(animation.animatedValue as Int)
                } catch (e: Exception) {
                    ensureTabLayoutVisible(targetWidth)
                    cancel()
                }
            }

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    prepareAnimationStart()
                }

                override fun onAnimationEnd(animation: Animator) {
                    ensureTabLayoutVisible(targetWidth)
                }

                override fun onAnimationCancel(animation: Animator) {
                    ensureTabLayoutVisible(targetWidth)
                }
            })
        }
    }

    private fun prepareAnimationStart() {
        try {
            updateTabLayoutWidth(0)
            tabLayout.post { tabLayout.visibility = View.VISIBLE }
        } catch (e: Exception) {
            ensureTabLayoutVisible()
        }
    }

    private fun updateTabLayoutWidth(width: Int) {
        tabLayout.layoutParams = tabLayout.layoutParams?.apply {
            this.width = width
        }
        tabLayout.requestLayout()
    }

    /**
     * 确保TabLayout可见性的兜底方法
     */
    private fun ensureTabLayoutVisible(width: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        try {
            tabLayout.post {
                try {
                    tabLayout.visibility = View.VISIBLE
                    updateTabLayoutWidth(width)
                } catch (e: Exception) {
                    // 最终兜底：使用默认布局参数
                    tabLayout.visibility = View.VISIBLE
                    tabLayout.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        tabLayout.layoutParams?.height ?: ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        } catch (e: Exception) {
            tabLayout.visibility = View.VISIBLE
        }
    }
}