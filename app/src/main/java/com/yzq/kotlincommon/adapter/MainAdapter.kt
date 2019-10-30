package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.kotlincommon.data.NaviItem
import com.yzq.lib_widget.HorizontalTextView

class MainAdapter(layoutResId: Int, data: List<NaviItem>) :
    BaseQuickAdapter<NaviItem, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: NaviItem) {

        val itemView = helper.itemView as HorizontalTextView

        itemView.setTitle(item.title)


    }
}