package com.yzq.kotlincommon.adapter


import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.movie.Subject
import com.yzq.kotlincommon.R
import com.yzq.lib_img.loadWithThumbnail
import kotlin.random.Random

class ImgListAdapter(layoutResId: Int, data: MutableList<Subject>) :
    BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data), LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: Subject) {

        val ivImg: AppCompatImageView = holder.itemView.findViewById(R.id.iv_img)

        /*高度随机*/
        ivImg.layoutParams.height = 300 + Random.nextInt(300)


        ivImg.loadWithThumbnail(item.images.small)


    }


}