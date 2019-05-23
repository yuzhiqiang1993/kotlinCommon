package com.yzq.common.extend

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yzq.common.R


/**
 * @description: 基于Glide对ImageView的扩展
 * @author : yzq
 * @date   : 2019/5/23
 * @time   : 15:48
 *
 */


val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .format(DecodeFormat.PREFER_RGB_565)
//        .override(400, 400)
        .placeholder(R.color.gray_100)
        .error(R.color.gray_100)


interface ImgRequestListener : RequestListener<Drawable> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        return false
    }

}


fun ImageView.loadCenterCrop(path: String): ImageView {

    Glide.with(context).load(path.trim()).apply(options).centerCrop().into(this)

    return this
}


fun ImageView.loadFitCenter(path: String): ImageView {

    Glide.with(context).load(path.trim()).apply(options).fitCenter().into(this)

    return this
}


fun ImageView.loadCenterCrop(path: String, listener: ImgRequestListener) {
    Glide.with(context).load(path.trim()).apply(options).centerCrop().listener(listener).into(this)
}

