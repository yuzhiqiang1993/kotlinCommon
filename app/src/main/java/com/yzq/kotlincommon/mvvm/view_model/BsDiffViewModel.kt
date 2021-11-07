package com.yzq.kotlincommon.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.xeon.bsdiff.BsDiffUtil
import com.yzq.lib_base.view_model.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.system.measureTimeMillis

/**
 * @description: BsDiff ViewModel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/4
 * @time   : 3:34 下午
 */

class BsDiffViewModel : BaseViewModel() {

    val newFileMD5LiveData by lazy { MutableLiveData<String>() }
    val combineFileMD5LiveData by lazy { MutableLiveData<String>() }


    val suffix = "apk"

    /**
     * 生成差分包,非常的耗时且占用内存，一般来京要放到服务端做
     */
    fun createDiffFile() {

        launchLoadingDialog(loadText = "正在生成差分包，请稍后...", checkNetWork = false) {

            val measureTimeMillis = measureTimeMillis {
                withContext(Dispatchers.IO) {


                    val oldFile = File(PathUtils.getExternalAppCachePath(), "old.${suffix}")
                    val newFile = File(PathUtils.getExternalAppCachePath(), "new.${suffix}")
                    val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.${suffix}")

                    if (!oldFile.exists() || !newFile.exists()) {
                        ToastUtils.showShort("对比包缺失")
                        return@withContext
                    }

                    val fileDiff = BsDiffUtil.fileDiff(
                            oldFile.absolutePath,
                            newFile.absolutePath,
                            patchFile.absolutePath
                    )

                }
            }


            ToastUtils.showLong("差分包生成成功，耗时${measureTimeMillis}")
        }
    }


    /*合并差分包*/
    fun combineFile() {

        launchLoadingDialog(loadText = "正在合并差分包，请稍后...", checkNetWork = false) {

            val measureTimeMillis = measureTimeMillis {
                withContext(Dispatchers.IO) {

                    val oldFile = File(PathUtils.getExternalAppCachePath(), "old.${suffix}")

                    val combineFile = File(PathUtils.getExternalAppCachePath(), "combine.${suffix}")
                    val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.${suffix}")
                    if (!oldFile.exists()) {
                        ToastUtils.showShort("旧文件不存在")
                        return@withContext
                    }
                    if (!patchFile.exists()) {
                        ToastUtils.showShort("差分包不存在")
                        return@withContext
                    }

                    BsDiffUtil.fileCombine(
                            oldFile.absolutePath,
                            combineFile.absolutePath,
                            patchFile.absolutePath
                    )

                }


                val newFilePath = "${PathUtils.getExternalAppCachePath()}/new.${suffix}"
                val combineFilePath = "${PathUtils.getExternalAppCachePath()}/combine.${suffix}"
                /*计算md5值*/

                newFileMD5LiveData.value = FileUtils.getFileMD5ToString(newFilePath)
                combineFileMD5LiveData.value = FileUtils.getFileMD5ToString(combineFilePath)
            }

            ToastUtils.showLong("差分包合并完成，耗时:${measureTimeMillis}")


        }


    }


}