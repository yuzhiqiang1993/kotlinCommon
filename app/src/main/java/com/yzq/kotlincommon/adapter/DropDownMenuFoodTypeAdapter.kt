package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.kotlincommon.R

class DropDownMenuFoodTypeAdapter(layoutResId: Int, data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: String) {

        holder.setText(R.id.tv_content, item)
     

    }
}