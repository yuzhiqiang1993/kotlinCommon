package com.yzq.kotlincommon.ui.activity

import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.managers.ThreadPoolManager
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityThreadPoolBinding
import com.yzq.logger.Logger
import java.util.concurrent.Callable
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


    override fun initWidget() {

        binding.run {

            initToolbar(includedToolbar.toolbar, "线程池")

            btnExecuteIoTask.setOnThrottleTimeClick {
                ThreadPoolManager.instance.executeIoTask {
                    Logger.i("开始执行")
                    TimeUnit.SECONDS.sleep(3)
                    /*这里异常不会导致崩溃  内部做处理了*/
                    throw Exception("异常")
                    Logger.i("执行完毕")
                }
            }

            btnExecuteCpuTask.setOnThrottleTimeClick {
                ThreadPoolManager.instance.executeCpuTask {
                    Logger.i("开始执行")
                    TimeUnit.SECONDS.sleep(1)

                    Logger.i("执行完毕")
                }

            }


            btnSubmitIoTask.setOnThrottleTimeClick {
                val future = ThreadPoolManager.instance.submitIoTask {
                    Logger.i("开始执行")
                    TimeUnit.SECONDS.sleep(1)
                    Logger.i("执行完毕了")
                    "这里是返回值"
                }
                /*这里不调用get 内部task任务也会执行 有异常不会抛出 只是内部代码不执行了  调get时可以拿到返回数据并阻塞后面的代码*/
                future?.run {
                    Logger.i("返回值：${get()}")
                }

                Logger.i("都结束了。。。")

            }

            btnSubmitCpuTask.setOnThrottleTimeClick {
                val future = ThreadPoolManager.instance.submitCpuTask {
                    Logger.i("开始执行")
                    TimeUnit.SECONDS.sleep(1)
                    throw Exception("主动抛个异常")
                    Logger.i("执行完毕了")
                    "这里是返回值"
                }

                future?.run {
                    /*异常会在调用get()方法时才抛出，如果不做处理，则会崩溃*/
                    kotlin.runCatching { Logger.i("返回值：${get()}") }.onFailure {
                        it.printStackTrace()
                    }

                }

            }
            btnInvokeAll.setOnThrottleTimeClick {
                invokeAllTask()
            }

            btnSubmitAll.setOnThrottleTimeClick {
                submitAllTask()
            }


        }
    }

    private fun submitAllTask() {

        Logger.i("开始执行")
        val futureList = ThreadPoolManager.instance.submitMultipleIoTasks(
            listOf(
                MyTask("task1", 4),
                MyTask("task2", 2),
                MyTask("task3", 3),
            )
        )

        Logger.i("都结束了。。。")

        futureList.forEach {
            Logger.i("返回值：${it.get()}")
        }


    }

    private fun invokeAllTask() {
        Logger.i("开始执行")
        val futureList = ThreadPoolManager.instance.invokeAllIoTask(
            listOf(
                MyTask("task1"),
                MyTask("task2", 4),
                MyTask("task3", 2),
            )
        )

        Logger.i("都结束了。。。")

        futureList.forEach {
            Logger.i("返回值：${it.get()}")
        }
    }


    inner class MyTask(val taskName: String, val sleepTime: Long = 1) : Callable<String> {
        override fun call(): String {
            Logger.i("${Thread.currentThread().name} ${taskName}:开始执行")
            TimeUnit.SECONDS.sleep(sleepTime)
            Logger.i("${taskName} 执行完毕了")
            return "${taskName}返回值"
        }

    }

}