package com.yzq.lib_base.ui

import android.graphics.Color
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.yzq.lib_base.R
import kotlinx.android.synthetic.main.activity_img_preview.*


/**
 * @description: 图片查看界面（看大图）
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 15:01
 *
 */

class ImgPreviewActivity : BaseActivity() {


    private lateinit var imagePath: String


    companion object {
        const val IMG_PATH = "imgPath"

    }


    override fun getContentLayoutId(): Int {
        return R.layout.activity_img_preview
    }

    override fun initArgs(extras: Bundle?) {
        if (extras != null) {
            imagePath = extras.getString(IMG_PATH)!!
        }
    }


    override fun initWidget() {
        super.initWidget()

        BarUtils.setStatusBarColor(this, Color.BLACK)
        allowFastClick()

        photo_view.setOnClickListener {

            finishAfterTransition()

        }
        Glide.with(this).load(imagePath).into(photo_view)

    }

    override fun onBackPressed() {
        finishAfterTransition()
    }


}
