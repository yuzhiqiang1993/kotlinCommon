package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.xeon.bsdiff.utils.XeonBsDiffUtil
import com.yzq.base.view_model.BaseViewModel
import com.yzq.base.view_model.UIState
import com.yzq.coroutine.scope.launchSafety
import com.yzq.coroutine.withDefault
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
    private val newFile = File(PathUtils.getExternalAppCachePath(), "new.$suffix")

    /*旧文件*/
    private val oldFile = File(PathUtils.getExternalAppCachePath(), "old.$suffix")

    /*合并后的文件*/
    private val combineFile = File(PathUtils.getExternalAppCachePath(), "combine.$suffix")

    /*生成的补丁文件*/
    private val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.$suffix")

    /**
     * 生成差分包,非常的耗时且占用内存，一般都是在服务端进行
     */
    fun createDiffFile() {
        viewModelScope.launchSafety {
            if (!oldFile.exists() || !newFile.exists()) {
                _uiState.value = UIState.ShowDialog("文件缺失...")
                return@launchSafety
            }
            _uiState.value = UIState.ShowLoading("正在生成补丁包...")

            val measureTimeMillis = measureTimeMillis {
                withDefault {
                    XeonBsDiffUtil.bsdiff(
                        newFile.absolutePath,
                        oldFile.absolutePath,
                        patchFile.absolutePath
                    )
                }
            }

            _uiState.value = UIState.ShowToast("补丁包生成成功，耗时$measureTimeMillis")
        }.invokeOnCompletion {
            _uiState.value = UIState.DissmissLoadingDialog()
        }
    }

    /*合并差分包*/
    fun combineFile() {

        viewModelScope.launchSafety {
            if (!oldFile.exists()) {
                _uiState.value = UIState.ShowToast("旧文件不存在")
                return@launchSafety
            }
            if (!patchFile.exists()) {
                ToastUtils.showShort("差分包不存在")
                _uiState.value = UIState.ShowToast("差分包不存在")
                return@launchSafety
            }
            _uiState.value = UIState.ShowLoadingDialog("正在合并补丁包...")
            val measureTimeMillis = measureTimeMillis {
                /*合并差分包是一个cpu密集型的任务*/
                withDefault {
                    XeonBsDiffUtil.bspatch(
                        oldFile.absolutePath,
                        patchFile.absolutePath,
                        combineFile.absolutePath
                    )
                }

                /*计算md5值*/
                newFileMD5LiveData.value = FileUtils.getFileMD5ToString(newFile)
                combineFileMD5LiveData.value = FileUtils.getFileMD5ToString(combineFile)
            }
            _uiState.value = UIState.ShowToast("差分包合并完成，耗时:$measureTimeMillis")
        }.invokeOnCompletion {
            _uiState.value = UIState.DissmissLoadingDialog()
        }
    }
}
