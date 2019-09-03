package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.lib_widget.HorizontalTextView

class MainAdapter(layoutResId: Int, data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: String) {

        val itemView = helper.itemView as com.yzq.lib_widget.HorizontalTextView

        itemView.setTitle(item)


    }
}