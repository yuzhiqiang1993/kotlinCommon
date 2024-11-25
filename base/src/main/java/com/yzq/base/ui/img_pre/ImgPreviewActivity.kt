package com.yzq.base.ui.img_pre

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.yzq.base.R
import com.yzq.base.databinding.ActivityImgPreviewBinding
import com.yzq.base.extend.statusBarColorRes
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
    private lateinit var imagePaths: MutableList<String>
    private var initialPosition: Int = 0

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAfterTransition()
        }

    }

    companion object {
        const val IMG_PATHS = "imgPaths"
        const val INITIAL_POSITION = "initialPosition"
    }

    override fun initArgs(extras: Bundle?) {
        if (extras != null) {
            imagePaths = (extras.getStringArrayList(IMG_PATHS) ?: emptyList()).toMutableList()
            imagePaths.add("http://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960")
            initialPosition = extras.getInt(INITIAL_POSITION, 0)
        }
    }

    override fun initWidget() {
        super.initWidget()

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        statusBarColorRes(R.color.black)

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
