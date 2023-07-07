package com.yzq.kotlincommon.ui.fragment.flow

import androidx.lifecycle.viewModelScope
import com.yzq.base.view_model.BaseViewModel
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.logger.LogCat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * @description StateFlow ShareFlow 示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/29
 * @time    09:57
 */

class SharedFlowViewModel : BaseViewModel() {

    /**
     * SharedFlow是热流，也就是说即使没有消费者,生产者也可以生产数据,需要注意的是SharedFlow不会自动完成，也就是说没有complete回调
     * replay: Int = 0, 重播给新订阅者的值的数量（不能为负数，默认为零）。
     * extraBufferCapacity: Int = 0, 除了重放之外，缓冲的值的数量。SharedFlow 可缓存值的数量为replay+extraBufferCapacity,主要是为了处理背压问题，如果下游消费者消费速度低于生产速度，会优先将值存放在缓冲区
     * onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND 背压策略，默认是挂起，就是如果缓冲区满了，就不生产数据了(前提是存在订阅者，如果没有订阅者，只有最新的replay数量的数据会放到缓存区)，等消费者消费后，再继续生产，DROP_OLDEST 丢弃掉旧值  DROP_LATEST 丢弃掉新值
     */
    private val _sharedFlow = MutableSharedFlow<Int>(2, 5)
    val shareFlow = _sharedFlow as SharedFlow<Int>


    /**
     * StateFlow 是SharedFlow的子类，本质上就是一个特殊的SharedFlow
     * 他的replay值为1，extraBufferCapacity值为0，BufferOverflow值为BufferOverflow.DROP_OLDEST
     * 也就是说新的订阅者永远会收到最新的值，且值只会有一个
     */
    private val _stateFlow = MutableStateFlow("初始值")
    val stateFlow = _stateFlow as StateFlow<String>


    fun sharedFlowEmit() {
        LogCat.i("开始生产数据")


        viewModelScope.launchSafety {
            repeat(200) {
                delay(200)
                _sharedFlow.emit(it)
                LogCat.i("生产值:${it}")
            }
        }
    }


    fun changeStateFlow() {
        _stateFlow.value = "更新的值"
    }

}