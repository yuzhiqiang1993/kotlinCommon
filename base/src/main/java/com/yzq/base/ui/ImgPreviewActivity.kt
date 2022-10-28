package com.yzq.base.ui

import android.graphics.Color
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.yzq.base.databinding.ActivityImgPreviewBinding
import com.yzq.base.ui.activity.BaseActivity


/**
 * @description: 图片查看界面（看大图）
 * @author : yzq
 * @date   : 2018/7/19
 * @time   : 15:01
 *
 */

class ImgPreviewActivity : BaseActivity<ActivityImgPreviewBinding>() {


    private lateinit var imagePath: String

    override fun createBinding(): ActivityImgPreviewBinding =
        ActivityImgPreviewBinding.inflate(layoutInflater)

    companion object {
        const val IMG_PATH = "imgPath"

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

        binding.photoView.setOnClickListener {
            onBackPressed()
        }
        Glide.with(this).load(imagePath).into(binding.photoView)

    }

    override fun onBackPressed() {
        finishAfterTransition()
    }


}
