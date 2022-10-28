package com.yzq.recycleview_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.base_adapter.R


/**
 * @description: Adapter加载更多布局
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 10:31
 *
 */

open class AdapterLoadMoreView : BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.tv_load_more_complete)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.tv_load_more_end)
    }


    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.tv_load_more_failed)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.layout_loading_more)
    }

    override fun getRootView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(R.layout.layout_load_more, parent, false)!!
}