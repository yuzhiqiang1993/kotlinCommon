package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.NaviItem
import com.yzq.widget.HorizontalTextView

class MainAdapter(layoutResId: Int, data: MutableList<NaviItem>) :
    BaseQuickAdapter<NaviItem, BaseViewHolder>(layoutResId, data) {


    override fun convert(holder: BaseViewHolder, item: NaviItem) {

        val itemView = holder.itemView as HorizontalTextView

        itemView.setTitle(item.title)


    }
}