package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.widget.HorizontalItemView

class MainAdapter(layoutResId: Int, data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: String) {

        var itemView = helper.itemView as HorizontalItemView

        itemView.setTitle(item)


    }
}