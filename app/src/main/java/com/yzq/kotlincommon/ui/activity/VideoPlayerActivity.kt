package com.yzq.kotlincommon.ui.activity

import android.content.res.Configuration
import android.net.Uri
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_video_player.*
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


/**
 * @description: 播放器页面
 * @author : yzq
 * @date   : 2019/11/9
 * @time   : 10:37
 */
@Route(path = RoutePath.Main.EXO_PLAYER)
class VideoPlayerActivity : BaseActivity() {

    private lateinit var orientationUtils: OrientationUtils
    private var isPlay = false
    private var isPause = false


    override fun getContentLayoutId() = R.layout.activity_video_player


    override fun initWidget() {

        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, video_player)
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false)
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
            }).setLockClickListener(LockClickListener { view, lock ->

                orientationUtils.isEnable = !lock

            }).build(video_player)




        video_player.getFullscreenButton()
            .setOnClickListener(View.OnClickListener {
                //直接横屏
                orientationUtils.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                video_player.startWindowFullscreen(this, true, true)
            })

    }

    override fun initData() {


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //如果旋转了就全屏
        if (isPlay && !isPause) {
            video_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
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
        video_player.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }


    override fun onResume() {
        video_player.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;

    }


    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            video_player.currentPlayer.release()
        }

        orientationUtils.releaseListener();
    }

}
