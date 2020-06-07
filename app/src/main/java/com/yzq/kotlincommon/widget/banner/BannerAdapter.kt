package com.yzq.kotlincommon.widget.banner

import android.view.View
import com.yzq.kotlincommon.R
import com.zhpan.bannerview.BaseBannerAdapter

class BannerAdapter : BaseBannerAdapter<String, BannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }

    override fun onBind(holder: BannerViewHolder, data: String, position: Int, pageSize: Int) {

        holder.bindData(data, position, pageSize)
    }

    override fun createViewHolder(itemView: View, viewType: Int): BannerViewHolder {

        return BannerViewHolder(itemView)
    }
}