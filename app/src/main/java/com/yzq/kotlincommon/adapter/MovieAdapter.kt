package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.extend.loadWithOptions
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.data.Subject
import kotlinx.android.synthetic.main.item_movie_layout.view.*


class MovieAdapter(layoutResId: Int, data: List<Subject>) : BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: Subject) {

        helper.itemView.tv_title.text = item.title

        val iv_img = helper.itemView.iv_img

        iv_img.loadWithOptions(item.images.small, 20)

        helper.addOnClickListener(R.id.iv_img)

    }


}