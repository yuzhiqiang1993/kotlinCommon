package com.yzq.kotlincommon.adapter

import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.img.ImageLoader
import com.yzq.common.img.ImgRequestListener
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.data.NewsBean
import kotlinx.android.synthetic.main.item_news_layout.view.*


class NewsAdapter(layoutResId: Int, data: List<NewsBean.Data>, val activity: BaseActivity) : BaseQuickAdapter<NewsBean.Data, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: NewsBean.Data) {

        helper.itemView.tv_title.text = item.title

        if (item.imgLoaded && !activity.isDestroyed) {

            Glide.with(activity).resumeRequests()

        }

        ImageLoader.loadCenterCropWithListener(activity, item.thumbnailPicS, helper.itemView.iv_img, radius = 15, listener = object : ImgRequestListener {
            /*加载成功后标记该Item为已加载状态*/
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                LogUtils.i("当前图片已加载：${helper.adapterPosition}")
                item.imgLoaded = true
                return false
            }
        })

        helper.addOnClickListener(R.id.iv_img)

    }


}