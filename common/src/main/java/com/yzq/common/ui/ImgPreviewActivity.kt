package com.yzq.common.ui

import android.graphics.Color
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.yzq.common.R
import com.yzq.common.img.ImageLoader
import kotlinx.android.synthetic.main.activity_img_preview.*


/**
 * @description: 图片查看界面（看大图）
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 15:01
 *
 */

class ImgPreviewActivity : BaseActivity() {


    lateinit var imagePath: String
    lateinit var imageName: String


    companion object {
        val IMG_PATH = "imgPath"
        val IMG_NAME = "imgName"
    }


    override fun getContentLayoutId(): Int {

        return R.layout.activity_img_preview
    }

    override fun initArgs(extras: Bundle?) {
        imagePath = extras!!.getString(IMG_PATH)
        imageName = extras!!.getString(IMG_NAME)


    }


    override fun initWidget() {
        super.initWidget()

        BarUtils.setStatusBarColor(this, Color.BLACK)
        setAllowFastClick(true)
        initToolbar(toolbar, imageName)

        ImageLoader.loadFitCenter(imagePath, photoView)


        photoView.setOnClickListener { finish() }
    }

}
