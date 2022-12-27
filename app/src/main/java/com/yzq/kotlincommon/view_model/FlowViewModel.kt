package com.yzq.kotlincommon.view_model

import com.blankj.utilcode.util.LogUtils
import com.yzq.base.view_model.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


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
    val stringFlow = flow<String?> {
        LogUtils.i("开始发送数据")
        emit("1")
        delay(100)
        emit("2")
        delay(100)
        emit("")
        delay(100)
        emit("3")
        delay(200)
        emit(null)

    }

}