package com.yzq.kotlincommon.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.extend.load
import com.yzq.kotlincommon.data.Subject

import kotlinx.android.synthetic.main.item_img_list.view.*
import kotlin.random.Random

class ImgListAdapter(layoutResId: Int, data: List<Subject>) : BaseQuickAdapter<Subject, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: Subject) {

        val iv_img = helper.itemView.iv_img

        /*高度随机*/
        iv_img.layoutParams.height = 300 + Random.nextInt(300)


        iv_img.load(item.images.large)


    }


}