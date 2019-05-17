package com.yzq.common.img

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.yzq.common.R


/**
 * @description: 基于Glide封装的图片加载
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 12:29
 *
 */

object ImageLoader {


    val options = RequestOptions()
            .placeholder(R.color.gray_100)
            .error(R.color.gray_100)


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param radius 圆角
     * @param placeHolderImg   占位图
     * @param errorImg  错误图片
     */
    fun loadCenterCrop(context: Context, path: String, imgView: ImageView, radius: Int = 1) {

        Glide.with(context)
                .load(path.trim())
                .apply(options)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .transition(withCrossFade())
                .into(imgView)


    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param uri   图片uri
     * @param imgView  图片控件
     * @param radius  圆角
     * @param placeHolderImg  占位图片
     * @param errorImg  错误图片
     */
    fun loadCenterCrop(context: Context, uri: Uri, imgView: ImageView, radius: Int = 1) {
        Glide.with(context)
                .load(uri)
                .apply(options)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .transition(withCrossFade())
                .into(imgView)
    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param drawableRes  图片资源
     * @param imgView  图片控件
     * @param radius  圆角
     * @param placeHolderImg 占位图
     * @param errorImg  错误图显示
     */
    fun loadCenterCrop(context: Context, drawableRes: Int, imgView: ImageView, radius: Int = 1) {
        Glide.with(context)
                .load(drawableRes)
                .apply(options)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .transition(withCrossFade())
                .into(imgView)
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(context: Context, path: String, imgView: ImageView) {
        Glide.with(context)
                .load(path.trim())
                .apply(options)
                .circleCrop()
                .transition(withCrossFade())
                .into(imgView)
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param res  图片资源
     * @param imgView  图片控件
     * @param placeHolderImg 占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(context: Context, res: DrawableRes, imgView: ImageView) {
        Glide.with(context)
                .load(res)
                .apply(options)
                .circleCrop()
                .transition(withCrossFade())
                .into(imgView)
    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path 图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时要显示的图片
     */
    fun loadFitCenter(context: Context, path: String, imgView: ImageView) {
        Glide.with(context)
                .load(path.trim())
                .apply(options)
                .fitCenter()
                .transition(withCrossFade())
                .into(imgView)
    }


}