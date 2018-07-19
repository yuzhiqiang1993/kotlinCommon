package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.img.ImageLoader
import com.yzq.kotlincommon.data.NewsBean
import kotlinx.android.synthetic.main.item_news_content.view.*

class NewsAdapter(layoutResId: Int, data: List<NewsBean.Data>) : BaseQuickAdapter<NewsBean.Data, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: NewsBean.Data) {

        helper.itemView.titleTv.text = item.title

        ImageLoader.loadCenterCrop(item.thumbnailPicS, helper.itemView.imgIv, radius = 15)
    }


}