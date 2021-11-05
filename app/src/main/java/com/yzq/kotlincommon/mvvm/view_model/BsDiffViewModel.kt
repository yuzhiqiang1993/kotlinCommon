package com.yzq.kotlincommon.mvvm.view_model

import com.yzq.lib_base.view_model.BaseViewModel

/**
 * @description: BsDiff ViewModel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/4
 * @time   : 3:34 下午
 */

class BsDiffViewModel : BaseViewModel() {

    /**
     * 生成差分包
     */
    suspend fun createDiffFile() {
        launchLoadingDialog() {

        }

    }

}