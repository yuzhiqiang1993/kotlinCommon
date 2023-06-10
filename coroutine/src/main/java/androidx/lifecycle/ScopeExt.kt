/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.lifecycle

import com.yzq.coroutine.interval.Interval
import com.yzq.coroutine.safety_coroutine.scope.LifeSafetyScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * 在[ViewModel]被销毁时取消协程作用域
 */
fun ViewModel.lifeScope(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit,
): LifeSafetyScope? {
    val scope = LifeSafetyScope(dispatcher = dispatcher).launch(block)
    return setTagIfAbsent(scope.toString(), scope)

}


/** 轮询器根据ViewModel生命周期自动取消 */
fun Interval.life(viewModel: ViewModel) = apply {
    viewModel.setTagIfAbsent(toString(), this)
}
