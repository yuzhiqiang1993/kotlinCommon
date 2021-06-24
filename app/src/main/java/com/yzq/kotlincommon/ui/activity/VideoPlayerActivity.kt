package com.yzq.kotlincommon.ui.activity

import android.content.res.Configuration
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityVideoPlayerBinding
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


/**
 * @description: 播放器页面
 * @author : yzq
 * @date   : 2019/11/9
 * @time   : 10:37
 */
@Route(path = RoutePath.Main.EXO_PLAYER)
class VideoPlayerActivity : BaseViewBindingActivity<ActivityVideoPlayerBinding>() {

    private lateinit var orientationUtils: OrientationUtils
    private var isPlay = false
    private var isPause = false


    override fun getViewBinding() = ActivityVideoPlayerBinding.inflate(layoutInflater)


    override fun initWidget() {

        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, binding.videoPlayer)
        //初始化不打开外部的旋转
        orientationUtils.isEnable = false
        /*视频地址*/
        val vedioUrl =
            "http://www.kangaijianshen.com//uploadfiles//2018//01//1516761517281689936.mp4"
        val gsyVideoOption = GSYVideoOptionBuilder()


        gsyVideoOption.setGSYVideoProgressListener { progress, secProgress, currentPosition, duration ->
            LogUtils.i("""progress：${progress}  secProgress${secProgress}  currentPosition:${currentPosition}  duration:$duration""")
        }

        gsyVideoOption
            .setIsTouchWiget(true)
            .setNeedShowWifiTip(true)
            .setRotateViewAuto(true)
            .setLockLand(true)
            .setHideKey(true)
            .setShowFullAnimation(false)
            .setAutoFullWithSize(true)
            .setNeedLockFull(true)
            .setUrl(vedioUrl)
            .setCacheWithPlay(false)
            .setVideoTitle("视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {


                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils.backToProtVideo()
                }
            }).setLockClickListener { view, lock ->

                orientationUtils.isEnable = !lock

            }.build(binding.videoPlayer)




        binding.videoPlayer.fullscreenButton
            .setOnClickListener {
                //直接横屏
                orientationUtils.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                binding.videoPlayer.startWindowFullscreen(this, true, true)
            }

    }

    override fun initData() {


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //如果旋转了就全屏
        if (isPlay && !isPause) {
            binding.videoPlayer.onConfigurationChanged(
                this,
                newConfig,
                orientationUtils,
                true,
                true
            )
        }
    }


    override fun onBackPressed() {
        orientationUtils.backToProtVideo()

        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }

        super.onBackPressed()
    }

    override fun onPause() {
        binding.videoPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }


    override fun onResume() {
        binding.videoPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false

    }


    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            binding.videoPlayer.currentPlayer.release()
        }

        orientationUtils.releaseListener()
    }


}
