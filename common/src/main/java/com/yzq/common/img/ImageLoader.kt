package com.yzq.common.img

import android.net.Uri
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.yzq.common.BaseApp
import com.yzq.common.R


/**
 * @description: 基于Glide封装的图片加载
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 12:29
 *
 */

object ImageLoader {


    /*CenterCrop*/
    fun loadCenterCrop(path: String, imgView: ImageView, radius: Int = 1, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img) {
        GlideApp.with(BaseApp.instance).load(path).placeholder(placeHolderImg).error(errorImg).centerCrop().transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)


    }


    /*CenterCrop*/
    fun loadCenterCrop(uri: Uri, imgView: ImageView, radius: Int = 1, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img) {
        GlideApp.with(BaseApp.instance).load(uri).placeholder(placeHolderImg).error(errorImg).centerCrop().transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)
    }


    /*CenterCrop*/
    fun loadCenterCrop(drawableRes: DrawableRes, imgView: ImageView, radius: Int = 1, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img) {
        GlideApp.with(BaseApp.instance).load(drawableRes).placeholder(placeHolderImg).error(errorImg).centerCrop().transform(RoundedCorners(radius)).transition(withCrossFade()).into(imgView)
    }


    /*CircleCrop*/
    fun loadCircleCrop(path: String, imgView: ImageView, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img) {
        GlideApp.with(BaseApp.instance).load(path).circleCrop().placeholder(placeHolderImg).error(errorImg).transition(withCrossFade()).into(imgView)
    }


    /*fitCenter*/
    fun loadFitCenter(path: String, imgView: ImageView, @DrawableRes placeHolderImg: Int = R.drawable.ic_placeholder_img, @DrawableRes errorImg: Int = R.drawable.ic_error_img) {
        GlideApp.with(BaseApp.instance).load(path).fitCenter().placeholder(placeHolderImg).error(errorImg).transition(withCrossFade()).into(imgView)
    }


}