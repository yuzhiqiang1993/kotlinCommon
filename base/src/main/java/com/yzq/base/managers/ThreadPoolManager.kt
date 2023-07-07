package com.yzq.base.managers

import com.yzq.logger.LogCat
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * @description 线程池管理  适合执行跟页面生命周期无关的任务
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/11
 * @time    16:48
 */

class ThreadPoolManager private constructor() {
    /*cpu密集型线程池*/
    private val cpuThreadPoolExecutor: ThreadPoolExecutor

    /*io 密集型线程池*/
    private val ioThreadPoolExecutor: ExecutorService

    //CPU 核数
    private val availableProcessors = Runtime.getRuntime().availableProcessors()

    /*核心线程数*/
    private val corePoolSize = 1

    //最大线程数 最多数量为cpu核心数-1
    private val maxPoolSize = Math.max(2, Math.min(availableProcessors - 1, 5))

    //线程空置回收时间
    private val keeupAliveSeconds = 5L

    //线程池队列 不限制数量
    private val poolWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    init {
        /*cpu任务占用太多会影响性能 所以要控制下并发数 以免主线程时间片减少*/
        cpuThreadPoolExecutor = ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keeupAliveSeconds,
            TimeUnit.SECONDS,
            poolWorkQueue,
            ThreadFactory {
                Thread(it).apply {
                    uncaughtExceptionHandler = ThreadPoolUncaughtExceptionHandler()
                }
            }
        )


        /*io 密集型的任务不会占用太多cpu时间片 承接任务可多一些 不做并发限制 */
        ioThreadPoolExecutor = Executors.newCachedThreadPool {
            Thread(it).apply {
                uncaughtExceptionHandler = ThreadPoolUncaughtExceptionHandler()
            }
        }
    }


    companion object {
        val instance by lazy {
            ThreadPoolManager()
        }
    }

    /**
     * 执行io密集型的任务  没有返回值  有异常会直接抛出
     *
     * @param task 要执行的任务
     * @receiver
     */
    fun executeIoTask(task: () -> Unit) {
        if (!ioThreadPoolExecutor.isShutdown) {
            ioThreadPoolExecutor.execute(task)
        }
    }

    /**
     * 执行cpu密集型的任务  没有返回值  有异常会直接抛出 由于内部做了异常处理  所以不会导致崩溃
     *
     * @param task 要执行的任务
     * @receiver
     */
    fun executeCpuTask(task: () -> Unit) {
        if (!cpuThreadPoolExecutor.isShutdown) {
            cpuThreadPoolExecutor.execute(task)
        }
    }


    /**
     * 提交一个io密集型任务，返回值为Future,适用于需要拿到返回值的场景，如果执行过程中有异常，会在future.get()时抛出，会导致崩溃
     *
     * @param V
     * @param task
     * @receiver
     * @return
     */
    fun <V> submitIoTask(task: () -> V): Future<V>? {
        if (!ioThreadPoolExecutor.isShutdown) {
            return ioThreadPoolExecutor.submit(task)
        }
        return null
    }

    /**
     * 提交一个cpu密集型任务，返回值为Future,适用于需要拿到返回值的场景，如果执行过程中有异常，会在future.get()时抛出，会导致崩溃
     *
     * @param V
     * @param task
     * @receiver
     * @return
     */
    fun <V> submitCpuTask(task: () -> V): Future<V>? {
        if (!cpuThreadPoolExecutor.isShutdown) {
            return cpuThreadPoolExecutor.submit(task)
        }
        return null
    }


    /*异常处理*/
    class ThreadPoolUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, t: Throwable) {
            LogCat.i("这里捕获异常：${t.message}")
            t.printStackTrace()
        }

    }

}