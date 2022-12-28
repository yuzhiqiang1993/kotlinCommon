package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.common.net.RetrofitFactory
import com.yzq.common.net.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


/**
 * @description flow的使用
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date    2022/12/27
 * @time    15:15
 */

class FlowViewModel : BaseViewModel() {

    /**
     * 创建一个flow。冷流，在调collect的时候才会发送数据
     */
    val stringFlow = flow {
        LogUtils.i("开始发送数据")
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
            LogUtils.i("onStart")
        }.onEmpty {
            /*当什么数据也没发送的时候执行*/
            LogUtils.i("onEmpty")
        }.onEach {
            /*上次每次emit，这里都会执行*/
            LogUtils.i("onEach:${it}")
        }.catch { t ->
            /*只能捕获上游的异常 catch执行后collect是不会执行的，可以在这里面再次emit让collect得到执行*/
            LogUtils.i("catch")
            t.printStackTrace()
            emit("异常了")

        }.onCompletion { t ->
            /*执行完毕时执行或被取消时执行*/
            t?.printStackTrace()
            LogUtils.i("onCompletion")
        }


    /**
     * 网络请求的flow示例
     * 其实flow并不适合在像网络请求这种一次性调用的场景下使用，它更加适合用在数据流的情况下例如轮询，且不太适合跟ui状态相关的逻辑
     */
    val userFlow = flow {
        LogUtils.i("2秒后开始请求")
        delay(2000)
        val userInfo = RetrofitFactory.instance.getService(ApiService::class.java).userInfo()
        emit(userInfo)
    }.onStart {
        _uiState.value = UIState.ShowLoadingDialog("请求中...")
    }.catch { t ->
        _uiState.value = UIState.ShowDialog(t.message ?: "异常了")
    }.onCompletion {
        _uiState.value = UIState.DissmissLoadingDialog()
    }


}