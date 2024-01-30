package com.yzq.dialog.core


/**
 * @description: 弹窗回调接口，用于监听弹窗的显示和消失，一般用于弹窗的生命周期监听，比如弹窗显示时，可以暂停视频播放，弹窗消失时，可以继续播放视频
 * @author : yuzhiqiang
 */

interface BaseDialogCallback {
    fun onShow()
    fun onDissmiss()
}