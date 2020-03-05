package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.movie.Subject
import com.yzq.lib_img.loadWithThumbnail
import kotlinx.android.synthetic.main.item_movie_layout.view.*


class MovieAdapter(layoutResId: Int, data: MutableList<Subject>) :
    BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: Subject) {


        with(helper.itemView) {

            tv_title.text = item.title

            iv_img.loadWithThumbnail(item.images.small)

        }

    }


}