package com.yzq.kotlincommon.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.managers.ThreadPoolManager
import java.util.concurrent.TimeUnit


/**
 * @description 普通的后台服务示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/12
 * @time    10:19
 */

class BackendService : Service() {


    /*只会执行一次*/
    override fun onCreate() {
        LogUtils.i("onCreate")
    }


    /**
     *  每次调 startService 时，都会执行，适合执行一些跟ui无关的任务，其实直接用线程池执行相同需求就行
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("onStartCommand")
        LogUtils.i("startId:$startId")

        ThreadPoolManager.instance.executeIoTask {
            intent?.run {
                intent.extras?.run {
                    LogUtils.i("key1", getString("key1"))
                    LogUtils.i("key2", getInt("key2"))
                }
            }

            TimeUnit.SECONDS.sleep(1)
            /*对于单次任务来说  在执行完毕后最好停止服务 以免浪费资源*/
            stopSelf(startId)
        }

        LogUtils.i("onStartCommand end")

        return super.onStartCommand(intent, flags, startId)
    }


    /**
     * 每次bindService时都会执行
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onDestroy() {
        LogUtils.i("onDestory")
    }
}