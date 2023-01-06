package com.yzq.kotlincommon.polling

import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * @description 轮询管理  锁屏后过段时间 cpu休眠  不会执行
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/3
 * @time    17:48
 */

class PollingManager private constructor() {

    /*执行周期任务的线程池*/
    private val scheduledThreadPoolExecutor: ScheduledThreadPoolExecutor

    /*存放周期任务对象的集合*/
    private val taskFutureMap = mutableMapOf<String, ScheduledFuture<*>>()


    init {
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        LogUtils.i("init cpu数量:$availableProcessors")

        scheduledThreadPoolExecutor =
            ScheduledThreadPoolExecutor(if (availableProcessors > 4) 4 else availableProcessors)

        LogUtils.i("线程池状态:${scheduledThreadPoolExecutor}")
    }

    companion object {
        val instance: PollingManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PollingManager()
        }
    }


    fun interval(
        taskId: String,//任务的标记
        initialDelay: Long,//首次延时多久执行
        period: Long,//周期
        unit: TimeUnit,//单位
        command: Runnable,//具体的执行任务
    ) {

        try {
            if (scheduledThreadPoolExecutor.isShutdown) {
                return
            }
            if (hasTask(taskId)) {
                LogUtils.i("已存在相同的轮询任务了 不执行")
                return
            }

            val scheduleAtFixedRate =
                scheduledThreadPoolExecutor.scheduleAtFixedRate(command, initialDelay, period, unit)

            taskFutureMap.put(taskId, scheduleAtFixedRate)
        } catch (e: Throwable) {
            e.printStackTrace()
            cancleTaskById(taskId)
        }


    }

    fun cancleTaskById(taskId: String) {

        val scheduledFuture = taskFutureMap.get(taskId)

        scheduledFuture?.run {
            if (!isCancelled) {
                cancel(true)
                taskFutureMap.remove(taskId)
            }
            LogUtils.i("${taskId} 轮询任务取消")
        }
    }

    fun hasTask(taskId: String): Boolean {
        return taskFutureMap.containsKey(taskId)
    }


    /*一般来讲在App退出的时候调用*/
    fun shutDown() {
        scheduledThreadPoolExecutor.shutdownNow()
        taskFutureMap.clear()
        LogUtils.i("shutDown")
    }


}