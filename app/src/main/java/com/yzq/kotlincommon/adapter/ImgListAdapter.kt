package com.yzq.kotlincommon.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzq.common.data.movie.Subject
import com.yzq.lib_img.loadWithThumbnail
import kotlinx.android.synthetic.main.item_img_list.view.*

import kotlin.random.Random

class ImgListAdapter(layoutResId: Int, data: MutableList<Subject>) :
    BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data), LoadMoreModule {


    override fun convert(helper: BaseViewHolder, item: Subject) {

        val ivImg = helper.itemView.iv_img

        /*高度随机*/
        ivImg.layoutParams.height = 300 + Random.nextInt(300)


        ivImg.loadWithThumbnail(item.images.small)


    }


}