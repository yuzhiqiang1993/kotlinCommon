package com.yzq.kotlincommon.ui.activity

import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_exo_player.*


/**
 * @description: 播放器页面
 * @author : yzq
 * @date   : 2019/11/9
 * @time   : 10:37
 */
@Route(path = RoutePath.Main.EXO_PLAYER)
class ExoPlayerActivity : BaseActivity() {
    override fun getContentLayoutId() = R.layout.activity_exo_player

    private val player by lazy { ExoPlayerFactory.newSimpleInstance(this) }


    override fun initWidget() {

        exo_player_view.player = player


    }

    override fun initData() {
        /*创建加载数据的工厂*/
        val dataSource =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, AppUtils.getAppName()))
        /*视频地址*/
        val vedioUrl =
            "http://www.kangaijianshen.com//uploadfiles//2018//01//1516761517281689936.mp4"
        val mp3Url = "http://www.kangaijianshen.com/uploadfiles/2019/04/1554776246936376791.mp3"
        val vedioUri = Uri.parse(vedioUrl)
        val mediaSource = ProgressiveMediaSource.Factory(dataSource).createMediaSource(vedioUri)

        player.prepare(mediaSource)

    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
