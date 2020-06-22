package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.item_drop_down_menu_layout.view.*

class DropDownMenuFilterAdapter(layoutResId: Int, data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: String) {


        holder.itemView.tv_content.text = item

    }
}