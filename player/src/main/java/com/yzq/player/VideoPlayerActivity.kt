package com.yzq.player

import android.view.View
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.therouter.router.Route
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.player.databinding.ActivityVideoPlayerBinding

@Route(path = RoutePath.Player.Player)
class VideoPlayerActivity : BaseActivity() {

    private val binding by viewbind(ActivityVideoPlayerBinding::inflate)

    var orientationUtils: OrientationUtils? = null

    override fun initWidget() {
        super.initWidget()
        val source1 =
            "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear3/prog_index.m3u8"
        binding.videoPlayer.apply {
            setUp(source1, true, "测试视频")
            //增加封面
            val imageView = ImageView(this@VideoPlayerActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageResource(R.drawable.xxx2)
            setThumbImageView(imageView)
            //设置旋转
            orientationUtils = OrientationUtils(this@VideoPlayerActivity, this)

            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener {
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                orientationUtils!!.resolveByClick()
                //finish();
            }

            //是否可以滑动调整
            setIsTouchWiget(true)


            //设置返回按键功能
            getBackButton().setOnClickListener(View.OnClickListener { onBackPressed() })


            startPlayLogic()
        }


    }
}