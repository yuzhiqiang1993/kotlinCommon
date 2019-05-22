package com.yzq.kotlincommon.adapter


import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzq.common.img.ImageLoader
import com.yzq.common.img.ImgRequestListener
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.data.BaiDuImgBean
import kotlinx.android.synthetic.main.item_img_list.view.*

class ImgListAdapter(layoutResId: Int, data: List<BaiDuImgBean.Data>, val activity: BaseActivity) : BaseQuickAdapter<BaiDuImgBean.Data, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: BaiDuImgBean.Data) {

        val iv_img = helper.itemView.iv_img


        val layoutParams = iv_img.layoutParams

        layoutParams.width = ScreenUtils.getScreenWidth() / 3
        layoutParams.height = (200 + Math.random() * 200).toInt()
        iv_img.layoutParams = layoutParams


        /*如果Glide是暂停加载状态，此时说明处于滚动中*/
        if (Glide.with(activity).isPaused) {
            if (item.imgLoaded) {
                /*如果加载过，则加载图片*/
                Glide.with(activity).resumeRequests()
            } else {
                /*否则不加载*/
                Glide.with(activity).pauseRequests()
            }
        }
//
        //  ImageLoader.loadCenterCrop(activity, item.thumbnailUrl, iv_img)


        ImageLoader.loadCenterCropWithListener(activity, item.thumbnailUrl, iv_img, listener = object : ImgRequestListener {
            /*加载成功后标记该Item为已加载状态*/
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                LogUtils.i("当前图片已加载：${helper.adapterPosition}")
                item.imgLoaded = true

                return false
            }
        })


    }


}