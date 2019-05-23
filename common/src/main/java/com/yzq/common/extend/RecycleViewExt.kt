package com.yzq.common.extend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yzq.common.widget.ItemDecoration


/**
 * @description: 对RecycleView的扩展
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 15:49
 *
 */


fun RecyclerView.init(layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context), hasImg: Boolean = false, needItemDecoration: Boolean = true) {
    this.layoutManager = layoutManager

    if (needItemDecoration) {
        addItemDecoration(ItemDecoration.baseItemDecoration(context))
    }


    if (hasImg) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(context).resumeRequests()

                } else {
                    Glide.with(context).pauseRequests()

                }
            }
        })
    }


}