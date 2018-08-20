package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.kotlincommon.data.TaskBean
import kotlinx.android.synthetic.main.item_task_layout.view.*

class TaskAdapter(layoutResId: Int, data: MutableList<TaskBean>?) : BaseQuickAdapter<TaskBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: TaskBean) {


        helper.itemView.nameTv.text = item.name
    }
}