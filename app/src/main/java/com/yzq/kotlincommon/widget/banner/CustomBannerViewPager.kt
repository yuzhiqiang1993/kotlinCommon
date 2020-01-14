package com.yzq.kotlincommon.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.holder.ViewHolder

class CustomBannerViewPager<T, VH : ViewHolder<T>>(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : BannerViewPager<T, VH>(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                false
            )

        }
        return super.dispatchTouchEvent(ev)
    }

}