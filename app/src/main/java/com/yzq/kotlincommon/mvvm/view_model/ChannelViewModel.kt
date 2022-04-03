package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @description: Channel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/3/3
 * @time   : 4:58 下午
 */

class ChannelViewModel : BaseViewModel() {

    /**
     * channel：管道的意思，有上下游的概念，本质上就是生产者和消费者模式。相当于把一个生产者，一个消费者，中间弄一个集合存储数据这种代码非封装了一下。、
     * channel本身是一个顶层函数，
     */

    /*这里可以避免暴露_channel*/
    val channel by ::_channel

    /**
     * Channel.RENDEZVOUS 是默认策略，表示发一个接收一个
     */
    private val _channel = Channel<Int>(Channel.RENDEZVOUS)

    fun testChannel() {

        viewModelScope.launch {
            (1..10).forEach {
                _channel.send(it)
                LogUtils.i("send:${it}")
                delay(200)

            }

            _channel.close()
        }


        viewModelScope.launch {
            for (i in _channel) {
                LogUtils.i("receive:${i}")
            }
        }

    }

    fun testChannelUnlimited() {

        viewModelScope.launch {

            /**
             * Channel.UNLIMITED 表示管道有无限个容量，等所有元素都收集完毕之后，一次性发出去
             * produce 会在数据执行完毕后自动关闭，不用下个channel手动去调close
             */
            val produce = produce(capacity = Channel.UNLIMITED) {
                (1..5).forEach {
                    send(it)
                    LogUtils.i("send:${it}")
                }
            }

            /**
             * 尽量不要使用reveive方法，因为可能出现channel close掉了，但是这边还在receive，会报错
             */
//            channel.receive()

            /**
             * 推荐直接对channel进行for循环或者使用 consumeEach 对数据进行消费，避免close导致的异常
             */
            produce.consumeEach {
                LogUtils.i("receive:${it}")
            }

        }
    }
}