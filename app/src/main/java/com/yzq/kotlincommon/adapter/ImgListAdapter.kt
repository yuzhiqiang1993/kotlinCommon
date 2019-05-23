package com.yzq.kotlincommon.adapter


import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.extend.loadCenterCrop
import com.yzq.kotlincommon.data.BaiDuImgBean
import kotlinx.android.synthetic.main.item_img_list.view.*

class ImgListAdapter(layoutResId: Int, data: List<BaiDuImgBean.Data>) : BaseQuickAdapter<BaiDuImgBean.Data, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: BaiDuImgBean.Data) {

        val iv_img = helper.itemView.iv_img


        val layoutParams = iv_img.layoutParams

        layoutParams.width = ScreenUtils.getScreenWidth() / 3
        layoutParams.height = (300 + Math.random() * 200).toInt()


        iv_img.layoutParams = layoutParams


        iv_img.loadCenterCrop(item.thumbnailUrl)


    }


}