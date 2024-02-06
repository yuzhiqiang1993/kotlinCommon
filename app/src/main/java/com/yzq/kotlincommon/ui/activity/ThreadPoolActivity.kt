package com.yzq.kotlincommon.ui.activity

import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.data.TaskResult
import com.yzq.coroutine.data.TaskStatus
import com.yzq.coroutine.thread_pool.ThreadPoolManager
import com.yzq.coroutine.thread_pool.poolStatus
import com.yzq.coroutine.thread_pool.runAsyncTask
import com.yzq.coroutine.thread_pool.scheduleTask
import com.yzq.coroutine.thread_pool.supplyAsyncTask
import com.yzq.coroutine.thread_pool.supplyAsyncTasks
import com.yzq.coroutine.thread_pool.supplyAsyncTimeoutTask
import com.yzq.kotlincommon.databinding.ActivityThreadPoolBinding
import com.yzq.logger.Logger
import java.util.concurrent.Callable
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


/**
 * @description 线程池使用示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2023/1/11
 * @time    17:40
 */

@Route(path = RoutePath.Main.THREAD_POOL)
class ThreadPoolActivity : BaseActivity() {

    private val binding by viewbind(ActivityThreadPoolBinding::inflate)


    private val taskList = listOf(
        MyTask("task1", 200),
        MyTask("task2", 500),
        MyTask("task3", 100),
    )


    override fun initWidget() {
        with(binding) {

            initToolbar(includedToolbar.toolbar, "线程池")
            btnExecute.setOnClickListener {
                ThreadPoolManager.instance.ioThreadPoolExecutor.execute {
                    //由于线程池设置了异常处理器，所以这里task内部的异常会被捕获，不会导致程序崩溃
                    task("execute")
                }
            }

            btnSubmit.setOnClickListener {
                val future = ThreadPoolManager.instance.ioThreadPoolExecutor.submit {
                    task("submit")
                }
                /**
                 * 这里不调用get 内部task任务也会执行，有异常不会抛出 只是内部代码不继续执行了  调get时可以拿到返回数据并阻塞后面的代码
                 * 如果submit的任务是耗时操作没有执行完毕，get方法会阻塞住主线程，直到任务执行完毕
                 */
                future?.run {
                    //这里会阻塞住，直到submit的任务执行完毕，如果任务有异常，会抛出异常，所以这里需要try-catch
                    runCatching {
                        Logger.i("返回值：${get()}")
                    }.onFailure {
                        Logger.i("异常：${it.message}")
                    }
                }

                Logger.i("执行完毕")

            }

            btnInvokeAll.setOnClickListener {
                invokeAll()
            }


            btnRunAsync.setOnClickListener {
                ThreadPoolManager.instance.ioThreadPoolExecutor.runAsyncTask {
                    task("runAsync")
                }.thenAccept {
                    when (it) {
                        is TaskStatus.Failure -> Logger.i("失败：${it.exception.message}")
                        TaskStatus.Success -> Logger.i("成功")
                    }
                }
            }

            btnSupplyAsync.setOnClickListener {
                ThreadPoolManager.instance.ioThreadPoolExecutor.supplyAsyncTask {
                    task("submitAsync")
                }.thenAccept {
                    when (it) {
                        is TaskResult.Failure -> Logger.i("失败：${it.exception.message}")
                        is TaskResult.Success -> Logger.i("成功：${it.result}")
                        else -> {}
                    }
                }

                //上面的不会阻塞主线程
                Logger.i("btnSupplyAsync end")
            }

            btnCustomThreadPool.setOnClickListener {
                ThreadPoolManager.instance.newThreadPool(
                    5, 10, 1, TimeUnit.SECONDS, LinkedBlockingQueue()
                ).execute {
                    task("custom")
                }
            }


            btnAllOfTask.setOnClickListener {
                allOfTask(true)
            }

            btnAllOfTaskSync.setOnClickListener {
                allOfTask(false)
            }


            btnTimeout.setOnClickListener {
                submitWithTimeOut(true)
            }

            btnTimeoutAsync.setOnClickListener {
                submitWithTimeOut(false)
            }

            btnPoolStatus.setOnClickListener {
                Logger.i("${ThreadPoolManager.instance.ioThreadPoolExecutor.poolStatus()}")
            }

            var scheduleCount = 0
            btnSchedule.setOnClickListener {
                ThreadPoolManager.instance.scheduledExecutor.scheduleTask(0, 5, TimeUnit.SECONDS) {
                    Logger.i("执行定时任务:${scheduleCount++}")
                    if (scheduleCount > 3) {
                        throw Exception("模拟异常")
                    }
                }.thenAccept {
                    Logger.i("定时任务线程池结束了")
                    when (it) {
                        is TaskStatus.Failure -> Logger.i("有异常：${it.exception.message}")
                        TaskStatus.Success -> Logger.i("成功")
                    }
                }
            }
        }
    }


    private fun submitWithTimeOut(syncResult: Boolean) {
        val timeOutTask = ThreadPoolManager.instance.ioThreadPoolExecutor.supplyAsyncTimeoutTask(
            MyTask("Timeout", 3000), 2, TimeUnit.SECONDS, true
        )

        if (syncResult) {
            val taskResult = timeOutTask.get()
            printResult(taskResult)
        } else {
            timeOutTask.thenApply {
                printResult(it)
            }
        }

        Logger.i("submitWithTimeOut 结束了。。。")
    }

    private fun printResult(it: TaskResult<String>) {
        Logger.i("printResult")
        when (it) {
            is TaskResult.Failure -> {
                Logger.i("失败：${it.exception.message}")
            }

            is TaskResult.Success -> {
                Logger.i("成功：${it.result}")
            }

        }
    }


    /**
     *
     * @param asyncResult Boolean  是否异步处理结果
     */
    private fun allOfTask(asyncResult: Boolean) {

        Logger.i("开始执行")
        val allOfTask = ThreadPoolManager.instance.ioThreadPoolExecutor.supplyAsyncTasks(taskList)


        if (asyncResult) {
            allOfTask.thenAccept {
                Logger.i("thenAccept：${it}")
                it.forEach {
                    Logger.i("返回值：${it}")
                }
            }


        } else {
            val stringList = allOfTask.get()
            stringList.forEach {
                Logger.i("返回值：${it}")

            }
        }

        Logger.i("allOfTask 方法结束 ")
    }


    private fun task(tag: String): String {
        Logger.i("${tag},开始执行")
        TimeUnit.SECONDS.sleep(3)
        throw Exception("${tag},异常")
        Logger.i("${tag},执行完毕")
        return "${tag} 的返回值"
    }


    private fun invokeAll() {
        Logger.i("invokeAll 开始执行")

        /**
         * invokeAll 会阻塞住主线程，直到所有任务执行完毕
         */
        val futureList = ThreadPoolManager.instance.ioThreadPoolExecutor.invokeAll(taskList)

        //这里会阻塞住，直到invokeAll的任务执行完毕
        Logger.i("都结束了。。。")

        //当调用get时，会阻塞到主线程,并且会抛出异常
        futureList.forEach {
            runCatching {
                Logger.i("返回值：${it.get()}")
            }.onFailure {
                Logger.i("异常：${it.message}")
            }

        }


    }


    inner class MyTask(val taskName: String, val sleepTime: Long = 200) : Callable<String> {
        override fun call(): String {
            Logger.i("${Thread.currentThread().name} ${taskName}:开始执行")

            TimeUnit.MILLISECONDS.sleep(sleepTime)
            throw Exception("模拟异常")
            Logger.i("${taskName} 执行完毕了")
            return "${taskName}返回值"
        }

    }


}