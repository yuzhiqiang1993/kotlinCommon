package com.yzq.base.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.yzq.base.databinding.ActivityImgPreviewBinding
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind

/**
 * @description: 图片查看界面（看大图）
 * @author : yzq
 * @date : 2018/7/19
 * @time : 15:01
 *
 */

class ImgPreviewActivity : BaseActivity() {

    private val binding by viewbind(ActivityImgPreviewBinding::inflate)
    private lateinit var imagePath: String

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAfterTransition()
        }

    }

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

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        BarUtils.setStatusBarColor(this, Color.BLACK)

        binding.photoView.setOnClickListener {
//            backPressedCallback.handleOnBackPressed()
        }
        Glide.with(this).load(imagePath).into(binding.photoView)
    }

}
