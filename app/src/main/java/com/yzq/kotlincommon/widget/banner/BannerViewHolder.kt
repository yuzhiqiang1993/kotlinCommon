package com.yzq.kotlincommon.widget.banner

import android.view.View
import com.rishabhharit.roundedimageview.RoundedImageView
import com.yzq.kotlincommon.R
import com.yzq.lib_img.load
import com.zhpan.bannerview.BaseViewHolder

/**
 * @description: BannerViewHolder
 * @author : XeonYu
 * @date   : 2019/12/1
 * @time   : 16:13
 */

class BannerViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
    override fun bindData(data: String, position: Int, pageSize: Int) {
        val bannerIv = findView<RoundedImageView>(R.id.iv_banner)
        bannerIv.load(data)
    }
}