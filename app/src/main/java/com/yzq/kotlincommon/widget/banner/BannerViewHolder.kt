package com.yzq.kotlincommon.widget.banner

import android.view.View
import com.rishabhharit.roundedimageview.RoundedImageView
import com.yzq.kotlincommon.R
import com.yzq.lib_img.load
import com.zhpan.bannerview.holder.ViewHolder


/**
 * @description: BannerViewHolder
 * @author : XeonYu
 * @date   : 2019/12/1
 * @time   : 16:13
 */

class BannerViewHolder : ViewHolder<String> {

    private lateinit var bannerIv: RoundedImageView

    override fun onBind(itemView: View, data: String, position: Int, size: Int) {
        bannerIv = itemView.findViewById(R.id.iv_banner)
        bannerIv.load(data)
    }

    override fun getLayoutId(): Int {
        return R.layout.item_banner
    }
}