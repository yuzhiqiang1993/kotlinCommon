package com.yzq.kotlincommon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.extend.loadWithOptions
import com.yzq.common.extend.loadWithThumbnail
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.data.NewsBean
import kotlinx.android.synthetic.main.item_news_layout.view.*


class NewsAdapter(layoutResId: Int, data: List<NewsBean.Data>) : BaseQuickAdapter<NewsBean.Data, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: NewsBean.Data) {

        helper.itemView.tv_title.text = item.title

        val iv_img = helper.itemView.iv_img

        iv_img.loadWithOptions(item.thumbnailPicS, 20)


        helper.addOnClickListener(R.id.iv_img)

    }


}