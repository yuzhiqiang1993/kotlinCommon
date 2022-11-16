package com.yzq.kotlincommon.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.movie.Subject
import com.yzq.img.loadWithThumbnail
import com.yzq.kotlincommon.R

class MovieAdapter(layoutResId: Int, data: MutableList<Subject>) :
    BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: Subject) {

        holder.apply {
            setText(R.id.tv_title, item.title)
            itemView.findViewById<AppCompatImageView>(R.id.iv_img)
                .loadWithThumbnail(item.images.small)
        }
    }
}
