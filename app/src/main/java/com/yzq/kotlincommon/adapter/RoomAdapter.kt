package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.data.data_base.User
import com.yzq.kotlincommon.R


class RoomAdapter(layoutResId: Int, data: MutableList<User>) :
    BaseQuickAdapter<User, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: User) {
        with(item) {
            helper.setText(R.id.tv_user, "${id}--->${name}")
        }
        helper.addOnClickListener(R.id.tv_delete)
        helper.addOnClickListener(R.id.tv_user)
    }
}