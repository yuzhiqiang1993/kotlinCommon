package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.data.task.TaskBean
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.item_task_content.view.*

class TaskAdapter(layoutResId: Int, data: MutableList<TaskBean>?) : BaseQuickAdapter<TaskBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: TaskBean) {

        helper.itemView.tv_name.text = item.name

        helper.addOnClickListener(R.id.tv_name)
        helper.addOnClickListener(R.id.tv_delete)
    }
}