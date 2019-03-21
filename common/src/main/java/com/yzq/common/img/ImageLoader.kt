package com.yzq.common.img

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.yzq.common.AppContext
import com.yzq.common.R


/**
 * @description: 基于Glide封装的图片加载
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 12:29
 *
 */

object ImageLoader {

    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param radius 圆角
     * @param placeHolderImg   占位图
     * @param errorImg  错误图片
     */
    fun loadCenterCrop(
            path: String,
            imgView: ImageView,
            radius: Int = 1,
            @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img,
            @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(path.trim()).centerCrop()
                //.placeholder(placeHolderImg).error(errorImg)
                .transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)


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
    fun loadCenterCrop(
            uri: Uri,
            imgView: ImageView,
            radius: Int = 1, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img,
            @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(uri)
                //.placeholder(placeHolderImg).error(errorImg)
                .centerCrop()
                .transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)
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
    fun loadCenterCrop(
            drawableRes: DrawableRes,
            imgView: ImageView,
            radius: Int = 1, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img,
            @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(drawableRes).centerCrop()
                //.placeholder(placeHolderImg).error(errorImg)
                .transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param path  图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(
            path: String, imgView: ImageView,
            @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img,
            @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(path.trim()).circleCrop()
                //.placeholder(placeHolderImg).error(errorImg)
                .transition(withCrossFade()).into(imgView)
    }


    /**
     * 将图片加载成圆形图片
     *
     * @param res  图片资源
     * @param imgView  图片控件
     * @param placeHolderImg 占位图
     * @param errorImg  加载错误时显示的图片
     */
    fun loadCircleCrop(
            @DrawableRes res: DrawableRes,
            imgView: ImageView, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img,
            @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(res).circleCrop()
                //.placeholder(placeHolderImg).error(errorImg)
                .transition(withCrossFade()).into(imgView)
    }


    /**
     * 以CenterCrop的方式加载图片
     *
     * @param path 图片路径
     * @param imgView  图片控件
     * @param placeHolderImg  占位图
     * @param errorImg  加载错误时要显示的图片
     */
    fun loadFitCenter(
            path: String,
            imgView: ImageView, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img
    ) {
        GlideApp.with(AppContext).load(path.trim()).fitCenter()
                //.placeholder(placeHolderImg).error(errorImg)
                .transition(withCrossFade()).into(imgView)
    }


}