package com.yzq.base.extend

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

/**
 * @description 收集flow的数据流
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/22
 * @time 14:50
 */

fun <T> Flow<T>.launchCollect(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>,
) {
    lifecycleOwner.lifecycleScope.launch {
        /**
         * 官方推荐使用 repeatOnLifecycle 或者 flowWithLifecycle 来收集数据流
         * 需要注意的是，每次符合生命周期规则时，都会收集一次数据
         */
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            collect(collector)
        }
    }
}
