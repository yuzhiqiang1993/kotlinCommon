package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.xeon.bsdiff.BsDiffUtil
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBsDiffBinding
import com.yzq.lib_base.extend.setOnThrottleTimeClick
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import java.io.File

/**
 * @description: bsdiff 增量更新示例
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/3
 * @time   : 8:04 下午
 */

@Route(path = RoutePath.Main.BS_DIFF)
class BsDiffActivity : BaseViewBindingActivity<ActivityBsDiffBinding>() {

    override fun getViewBinding() = ActivityBsDiffBinding.inflate(layoutInflater)

    override fun initWidget() {

        binding.apply {
            btnFileDiff.setOnThrottleTimeClick {
                /*生成差分包*/
                createDiffFile()
            }

            btnFileCombine.setOnThrottleTimeClick {
                /*合并差分包*/
                combineFile()
            }

        }
    }

    private fun combineFile() {

        val oldFile = File(PathUtils.getExternalAppCachePath(), "old.zip")
        val newFile = File(PathUtils.getExternalAppCachePath(), "combine.zip")
        val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.zip")
        if (!oldFile.exists()) {
            ToastUtils.showShort("旧文件不存在")
            return
        }
        if (!patchFile.exists()) {
            ToastUtils.showShort("差分包不存在")
            return
        }

        try {
            val fileCombine = BsDiffUtil.fileCombine(
                oldFile.absolutePath,
                newFile.absolutePath,
                patchFile.absolutePath
            )

            ToastUtils.showShort("合并差分包：${fileCombine}")
        } catch (e: Throwable) {
            e.printStackTrace()
            LogUtils.e("合并差分包异常")
        }

    }

    private fun createDiffFile() {
        val oldFile = File(PathUtils.getExternalAppCachePath(), "old.zip")
        val newFile = File(PathUtils.getExternalAppCachePath(), "new.zip")
        val patchFile = File(PathUtils.getExternalAppCachePath(), "patch.zip")

        if (!oldFile.exists() || !newFile.exists()) {
            ToastUtils.showShort("对比包缺失")
            return
        }

        try {

            val fileDiff = BsDiffUtil.fileDiff(
                oldFile.absolutePath,
                newFile.absolutePath,
                patchFile.absolutePath
            )

            ToastUtils.showShort("生成差分包：${fileDiff}")
        } catch (e: Throwable) {
            e.printStackTrace()
            LogUtils.e("生成差分包失败")
        }

    }
}