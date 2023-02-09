package com.yzq.kotlincommon.widget.banner

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.yzq.kotlincommon.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter : BaseBannerAdapter<String>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }

    override fun bindData(
        holder: BaseViewHolder<String>,
        data: String,
        position: Int,
        pageSize: Int,
    ) {

        val bannerIv = holder.findViewById<AppCompatImageView>(R.id.iv_banner)
        bannerIv.load(data)
    }
}
