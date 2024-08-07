package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjq.toast.Toaster
import com.yzq.application.AppStorage
import com.yzq.base.extend.md5
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.bsdiff.BsDiffTool
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.coroutine.safety_coroutine.withDefault
import java.io.File
import kotlin.system.measureTimeMillis

/**
 * @description: BsDiff ViewModel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/4
 * @time : 3:34 下午
 */

class BsDiffViewModel : BaseViewModel() {

    val newFileMD5LiveData by lazy { MutableLiveData<String>() }
    val combineFileMD5LiveData by lazy { MutableLiveData<String>() }
    private val suffix = "apk"

    /*新文件*/
    private val newFile = File(AppStorage.External.Private.cachePath, "new.$suffix")

    /*旧文件*/
    private val oldFile = File(AppStorage.External.Private.cachePath, "old.$suffix")

    /*合并后的文件*/
    private val combineFile = File(AppStorage.External.Private.cachePath, "combine.$suffix")

    /*生成的补丁文件*/
    private val patchFile = File(AppStorage.External.Private.cachePath, "patch.$suffix")

    /**
     * 生成差分包,非常的耗时且占用内存，一般都是在服务端进行
     */
    fun createDiffFile() {
        viewModelScope.launchSafety {
            if (!oldFile.exists() || !newFile.exists()) {
                _uiStateFlow.value = UIState.ShowDialog("文件缺失...")
                return@launchSafety
            }
            _uiStateFlow.value = UIState.ShowLoading("正在生成补丁包...")

            val measureTimeMillis = measureTimeMillis {
                withDefault {
                    BsDiffTool.diff(
                        newFile.absolutePath,
                        oldFile.absolutePath,
                        patchFile.absolutePath
                    )
                }
            }

            _uiStateFlow.value = UIState.ShowToast("补丁包生成成功，耗时$measureTimeMillis")
        }.invokeOnCompletion {
            _uiStateFlow.value = UIState.DissmissLoadingDialog
        }
    }

    /*合并差分包*/
    fun combineFile() {

        viewModelScope.launchSafety {
            if (!oldFile.exists()) {
                _uiStateFlow.value = UIState.ShowToast("旧文件不存在")
                return@launchSafety
            }
            if (!patchFile.exists()) {
                Toaster.showShort("差分包不存在")
                _uiStateFlow.value = UIState.ShowToast("差分包不存在")
                return@launchSafety
            }
            _uiStateFlow.value = UIState.ShowLoadingDialog("正在合并补丁包...")
            val measureTimeMillis = measureTimeMillis {
                /*合并差分包是一个cpu密集型的任务*/
                withDefault {
                    BsDiffTool.patch(
                        oldFile.absolutePath,
                        patchFile.absolutePath,
                        combineFile.absolutePath
                    )
                }

                /*计算md5值*/
                newFileMD5LiveData.value = newFile.md5()
                combineFileMD5LiveData.value = combineFile.md5()
            }
            _uiStateFlow.value = UIState.ShowToast("差分包合并完成，耗时:$measureTimeMillis")
        }.invokeOnCompletion {
            _uiStateFlow.value = UIState.DissmissLoadingDialog
        }
    }
}
