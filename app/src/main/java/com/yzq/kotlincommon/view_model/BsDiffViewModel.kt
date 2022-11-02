package com.yzq.kotlincommon.view_model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.xeon.bsdiff.utils.XeonBsDiffUtil
import com.yzq.base.view_model.BaseViewModel
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
    private val suffix = "apk"

    /*新文件*/
    private val newFile = File(PathUtils.getExternalAppCachePath(), "new.${suffix}")

    /*旧文件*/
    private val oldFile = File(PathUtils.getExternalAppCachePath(), "old.${suffix}")

    /*合并后的文件*/
    private val combineFile = File(PathUtils.getExternalAppCachePath(), "combine.${suffix}")

    /*生成的补丁文件*/
    private val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.${suffix}")

    /**
     * 生成差分包,非常的耗时且占用内存，一般都是在服务端进行
     */
    fun createDiffFile() {

        launchLoadingDialog(loadText = "正在生成差分包，请稍后...", checkNetWork = false) {

            val measureTimeMillis = measureTimeMillis {
                withContext(Dispatchers.Default) {
                    if (!oldFile.exists() || !newFile.exists()) {
                        ToastUtils.showShort("对比包缺失")
                        return@withContext
                    }

                    val fileDiff = XeonBsDiffUtil.bsdiff(
                        newFile.absolutePath,
                        oldFile.absolutePath,
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
                /*合并差分包是一个cpu密集型的任务*/
                withContext(Dispatchers.Default) {

                    if (!oldFile.exists()) {
                        ToastUtils.showShort("旧文件不存在")
                        return@withContext
                    }
                    if (!patchFile.exists()) {
                        ToastUtils.showShort("差分包不存在")
                        return@withContext
                    }

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

            ToastUtils.showLong("差分包合并完成，耗时:${measureTimeMillis}")

        }

    }

}