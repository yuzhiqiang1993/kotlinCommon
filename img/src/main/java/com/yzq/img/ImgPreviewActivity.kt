package com.yzq.img

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager2.widget.ViewPager2
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.img.databinding.ActivityImgPreviewBinding
import com.yzq.util.ext.statusBarColorRes

/**
 * @description: 图片查看界面（看大图）
 * @author : yzq
 *
 */

class ImgPreviewActivity : BaseActivity() {

    private val binding by viewBinding(ActivityImgPreviewBinding::inflate)
    private var imagePaths: List<String> = emptyList<String>()
    private var initialPosition: Int = 0

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAfterTransition()
        }

    }

    companion object {
        const val IMG_PATHS = "imgPaths"
        const val INITIAL_POSITION = "initialPosition"

        fun start(
            context: Context,
            imgPaths: ArrayList<String>,
            initialPosition: Int = 0,
            options: ActivityOptionsCompat? = null
        ) {
            val intent = Intent(context, ImgPreviewActivity::class.java)
            intent.putStringArrayListExtra(IMG_PATHS, imgPaths)
            intent.putExtra(INITIAL_POSITION, initialPosition)

            if (options != null) {
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }

    override fun initArgs(extras: Bundle?) {
        if (extras != null) {
            imagePaths = (extras.getStringArrayList(IMG_PATHS) ?: emptyList()).toMutableList()
            initialPosition = extras.getInt(INITIAL_POSITION, 0)
        }
    }

    override fun initWidget() {
        super.initWidget()

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        statusBarColorRes(android.R.color.black)

        // 设置 ViewPager2
        val adapter = ImagePagerAdapter(imagePaths)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(initialPosition, false)

        // 添加滑动监听，更新位置
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePositionText(position)
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun updatePositionText(position: Int) {
        val current = position + 1
        val total = imagePaths.size
        // 动画结束后更新文字
        binding.tvPosition.text = "$current/$total"
    }

}
