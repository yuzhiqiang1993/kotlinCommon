package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.task.TaskBean
import com.yzq.kotlincommon.R

class TaskAdapter(layoutResId: Int, data: MutableList<TaskBean>) :
    BaseQuickAdapter<TaskBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: TaskBean) {

        holder.setText(R.id.tv_name, item.name)

    }
}