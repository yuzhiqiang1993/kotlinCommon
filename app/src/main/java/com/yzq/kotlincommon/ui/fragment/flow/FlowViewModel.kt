package com.yzq.kotlincommon.ui.fragment.flow

import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import com.yzq.logger.LogCat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart


/**
 * @description flow 冷流的使用示例
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/28
 * @time    17:35
 */

class FlowViewModel : BaseViewModel() {

    /**
     * 创建一个flow。冷流，在调collect的时候才会发送数据
     */
    val stringFlow = flow {
        LogCat.i("开始发送数据")
        emit("1")
        delay(1000)
        emit("2")
        delay(1000)
        emit("")
        delay(1000)
        emit("3")
        delay(2000)
        emit(null)

    }.filterNotNull()
        .map {
            /*中间操作符，还有很多其他的，用于处理数据*/
            "${it}:map"
        }
        .flowOn(Dispatchers.IO)//flowOn只会影响上游的线程，后续会自动切到原本的线程
        .onStart {
            /*发送数据前执行*/
            LogCat.i("onStart")
        }.onEmpty {
            /*当什么数据也没发送的时候执行*/
            LogCat.i("onEmpty")
        }.onEach {
            /*上次每次emit，这里都会执行*/
            LogCat.i("onEach:${it}")
        }.catch { t ->
            /*只能捕获上游的异常 catch执行后collect是不会执行的，可以在这里面再次emit让collect得到执行*/
            LogCat.i("catch")
            t.printStackTrace()
            emit("异常了")

        }.onCompletion { t ->
            /*执行完毕时执行或被取消时执行*/
            t?.printStackTrace()
            LogCat.i("onCompletion")
        }


    /**
     * 网络请求的flow示例
     * 其实flow并不适合在像网络请求这种一次性调用的场景下使用，再一个flow的collect本身不具备生命周期检测能力，也就是说页面处于非活跃状态下，也会执行collect
     * 虽然官方给我们提供了 repeatOnLifecycle 或者 flowWithLifecycle 来收集数据流，但是这个方式会造成页面前后台切换时多次执行，并不符合我们日常开发中网络请求相关的场景
     * 它更加适合用在数据流的情况下例如轮询、页面切换时获取最新位置这种跟生命周期绑定需要多次执行的场景。或者可以在上游使用flow，在ui层转成livedata观察也是可以的
     */
    val userFlow = flow {
        LogCat.i("2秒后开始请求")
        delay(2000)
        val userInfo = RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
        emit(userInfo)
    }.onStart {
        _uiStateFlow.value = UIState.ShowLoadingDialog("请求中...")
    }.catch { t ->
        _uiStateFlow.value = UIState.ShowDialog(t.message ?: "异常了")
    }.onCompletion {
        _uiStateFlow.value = UIState.DissmissLoadingDialog()
    }

}