package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.data_base.User
import com.yzq.kotlincommon.R


class RoomAdapter(layoutResId: Int, data: MutableList<User>) :
    BaseQuickAdapter<User, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: User) {
        with(item) {
            holder.setText(R.id.tv_user, "${id}--->${name}")
        }
    }
}