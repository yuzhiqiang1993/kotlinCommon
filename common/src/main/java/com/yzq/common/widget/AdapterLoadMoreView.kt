package com.yzq.common.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.yzq.common.R


/**
 * @description: Adapter加载更多布局
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 10:31
 *
 */

object AdapterLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.layout_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.layout_loadding_more
    }

    override fun getLoadEndViewId(): Int {
        return 0
    }

    override fun getLoadFailViewId(): Int {
        return R.id.tv_load_failed
    }

}