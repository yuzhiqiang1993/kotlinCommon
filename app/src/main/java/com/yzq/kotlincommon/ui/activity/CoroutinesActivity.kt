package com.yzq.kotlincommon.ui.activity

import com.blankj.utilcode.util.LogUtils
import com.yzq.kotlincommon.R
import com.yzq.lib_base.ui.BaseActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoroutinesActivity : BaseActivity() {
    override fun getContentLayoutId(): Int = R.layout.activity_coroutines


    override fun initWidget() {
        super.initWidget()
    }


    override fun initData() {
        super.initData()

        runBlocking {

        }

        val job = GlobalScope.launch {
            LogUtils.i("开始协程")

            /*delay只会阻塞协程体后面的代码  不影响协程体之外的代码*/
            delay(3000)

            LogUtils.i("hello")

        }

        runBlocking {

            LogUtils.i("开始阻塞")
            delay(3000)
            LogUtils.i("阻塞3秒")
        }

        LogUtils.i("world")


    }


}

suspend fun main() {

    val time = measureTimeMillis {

        coroutineScope {
            val one = async { one() }
            val two = async { two() }

            println(one.await() + two.await())
        }


    }
    println("time:$time")
}

suspend fun one(): Int {
    delay(1000)
    println("one")

    return 1
}


suspend fun two(): Int {
    delay(1000)
    println("tow")

    return 2
}