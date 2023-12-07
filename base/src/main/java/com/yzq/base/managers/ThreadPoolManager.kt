package com.yzq.base.managers

import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


/**
 * @description 线程池管理
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

class ThreadPoolManager private constructor() {
    //cpu密集型线程池
    private val cpuThreadPoolExecutor: ThreadPoolExecutor

    //io 密集型线程池
    private val ioThreadPoolExecutor: ExecutorService

    //CPU 核数
    private val availableProcessors = Runtime.getRuntime().availableProcessors()

    //核心线程数
    private val corePoolSize = 1

    //最大线程数 最多数量为cpu核心数-1
    private val maxPoolSize = Math.max(2, Math.min(availableProcessors - 1, 5))

    //线程空置回收时间
    private val keeupAliveSeconds = 5L

    //线程池队列 不限制数量
    private val poolWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    init {
        //cpu任务占用太多会影响性能 所以要控制下并发数 以免主线程时间片减少
        cpuThreadPoolExecutor = ThreadPoolExecutor(corePoolSize,
            maxPoolSize,
            keeupAliveSeconds,
            TimeUnit.SECONDS,
            poolWorkQueue,
            ThreadFactory {
                Thread(it).apply {
                    name = "CPU-Task-Thread-${threadId.incrementAndGet()}"
                    uncaughtExceptionHandler = ThreadPoolUncaughtExceptionHandler()
                }
            })


        //io 密集型的任务不会占用太多cpu时间片 承接任务可多一些 不做并发限制
        ioThreadPoolExecutor = Executors.newCachedThreadPool {
            Thread(it).apply {
                name = "IO-Task-Thread-${threadId.incrementAndGet()}"
                uncaughtExceptionHandler = ThreadPoolUncaughtExceptionHandler()
            }
        }

    }


    companion object {
        val instance by lazy {
            ThreadPoolManager()
        }
        private val threadId = AtomicInteger(0)
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


    /**
     * 执行多个 IO 任务，并返回 Future 列表，内部的任务是并发执行的，所有任务执行完毕后统一返回结果
     * 如果其中某个task出现异常，不会影响其它task执行，会在future.get()时抛出
     *
     * @param V
     * @param tasks
     * @return
     */
    fun <V> invokeAllIoTask(tasks: List<Callable<V>>): List<Future<V>> {
        return if (!ioThreadPoolExecutor.isShutdown) {
            ioThreadPoolExecutor.invokeAll(tasks)
        } else emptyList()
    }

    /**
     * 执行多个 CPU 任务，并返回 Future 列表，内部的任务是并发执行的，所有任务执行完毕后统一返回结果
     * 如果其中某个task出现异常，不会影响其它task执行，会在future.get()时抛出
     *
     * @param V
     * @param tasks
     * @return
     */
    fun <V> invokeAllCpuTask(tasks: List<Callable<V>>): List<Future<V>> {
        return if (!cpuThreadPoolExecutor.isShutdown) {
            cpuThreadPoolExecutor.invokeAll(tasks)
        } else emptyList()
    }


    /**
     * 提交多个 IO 任务，并返回 Future 列表，内部的任务是并发执行的
     * 跟 invokeAllIoTask 的区别是，在遍历List<Future<V>>时，是按照遍历顺序进行阻塞的，也就是说，如果第一个任务执行时间很长，那么后面的任务即使执行完毕，也会阻塞在future.get()处
     * 如果其中某个task出现异常，不会影响其它task执行，会在future.get()时抛出
     *
     * @param V
     * @param tasks
     * @return
     */
    fun <V> submitMultipleIoTasks(tasks: List<Callable<V>>): List<Future<V>> {
        return if (!ioThreadPoolExecutor.isShutdown) {
            tasks.map { ioThreadPoolExecutor.submit(it) }
        } else emptyList()
    }

    /**
     * 提交多个 CPU 任务，并返回 Future 列表，内部的任务是并发执行的
     * 跟 invokeAllCpuTask 的区别是，在遍历List<Future<V>>时，是按照遍历顺序进行阻塞的，也就是说，如果第一个任务执行时间很长，那么后面的任务即使执行完毕，也会阻塞在future.get()处
     * 如果其中某个task出现异常，不会影响其它task执行，会在future.get()时抛出
     *
     * @param V
     * @param tasks
     * @return
     */
    fun <V> submitMultipleCpuTasks(tasks: List<Callable<V>>): List<Future<V>> {
        return if (!cpuThreadPoolExecutor.isShutdown) {
            tasks.map { cpuThreadPoolExecutor.submit(it) }
        } else emptyList()
    }


    /**
     * 关闭线程池
     */
    fun shutdown() {
        try {
            // 关闭 IO 线程池
            ioThreadPoolExecutor.shutdown()
            // 等待 1 分钟，线程池没有关闭的话，强制关闭
            if (!ioThreadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                ioThreadPoolExecutor.shutdownNow()
            }
            // 关闭 CPU 线程池
            cpuThreadPoolExecutor.shutdown()
            // 等待 1 分钟，线程池没有关闭的话，强制关闭
            if (!cpuThreadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                cpuThreadPoolExecutor.shutdownNow()
            }
        } catch (e: InterruptedException) {
            // 中断线程
            Thread.currentThread().interrupt()
        }
    }

    /**
     * 线程池 uncaught exception 处理器
     */
    class ThreadPoolUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, t: Throwable) {
            t.printStackTrace()
        }

    }

}
