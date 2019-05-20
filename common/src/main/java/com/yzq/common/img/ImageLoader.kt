package com.yzq.common.img

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.yzq.common.R
import com.yzq.common.ui.BaseActivity


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
    fun loadCenterCrop(activity: BaseActivity, path: String, imgView: ImageView, radius: Int = 1) {

        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(path.trim())
                    .apply(options)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .transition(withCrossFade())
                    .into(imgView)
        }


    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param radius 圆角
     * @param placeHolderImg   占位图
     * @param errorImg  错误图片
     */
    fun loadCenterCropWithListener(activity: BaseActivity, path: String, imgView: ImageView, radius: Int = 1, listener: ImgRequestListener) {

        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(path.trim())
                    .apply(options)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .transition(withCrossFade())
                    .listener(listener)
                    .into(imgView)
        }


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
    fun loadCenterCrop(activity: BaseActivity, uri: Uri, imgView: ImageView, radius: Int = 1) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(uri)
                    .apply(options)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .transition(withCrossFade())
                    .into(imgView)
        }
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
    fun loadCenterCrop(activity: BaseActivity, drawableRes: Int, imgView: ImageView, radius: Int = 1) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(drawableRes)
                    .apply(options)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .transition(withCrossFade())
                    .into(imgView)
        }
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(activity: BaseActivity, path: String, imgView: ImageView) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(path.trim())
                    .apply(options)
                    .circleCrop()
                    .transition(withCrossFade())
                    .into(imgView)
        }
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param res  图片资源
     * @param imgView  图片控件
     * @param placeHolderImg 占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(activity: BaseActivity, @DrawableRes res: Int, imgView: ImageView) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(res)
                    .apply(options)
                    .circleCrop()
                    .transition(withCrossFade())
                    .into(imgView)
        }

    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path 图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时要显示的图片
     */
    fun loadFitCenter(activity: BaseActivity, path: String, imgView: ImageView) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(path.trim())
                    .apply(options)
                    .fitCenter()
                    .transition(withCrossFade())
                    .into(imgView)
        }

    }

    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path 图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时要显示的图片
     */
    fun loadFitCenter(activity: BaseActivity, @DrawableRes res: Int, imgView: ImageView) {
        if (!activity.isDestroyed) {
            Glide.with(activity)
                    .load(res)
                    .apply(options)
                    .fitCenter()
                    .transition(withCrossFade())
                    .into(imgView)
        }

    }

}