package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBsDiffBinding
import com.yzq.kotlincommon.mvvm.view_model.BsDiffViewModel
import com.yzq.lib_base.extend.setOnThrottleTimeClick
import com.yzq.lib_base.ui.activity.BaseVbVmActivity

/**
 * @description: bsdiff 增量更新示例
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2021/11/3
 * @time   : 8:04 下午
 */

@Route(path = RoutePath.Main.BS_DIFF)
class BsDiffActivity : BaseVbVmActivity<ActivityBsDiffBinding, BsDiffViewModel>() {

    override fun getViewBinding() = ActivityBsDiffBinding.inflate(layoutInflater)
    override fun getViewModelClass() = BsDiffViewModel::class.java

    override fun initWidget() {

        binding.apply {

            initToolbar(toolbar = layoutToolbar.toolbar, "BsDiff")

            btnFileDiff.setOnThrottleTimeClick {
                /*生成差分包*/
                vm.createDiffFile()
            }

            btnFileCombine.setOnThrottleTimeClick {
                /*合并差分包*/
                vm.combineFile()
            }

        }
    }

    override fun observeViewModel() {

        vm.apply {

            newFileMD5LiveData.observe(this@BsDiffActivity) {
                binding.htvNewFileMd5.setContent(it)
            }
            combineFileMD5LiveData.observe(this@BsDiffActivity) {
                binding.htvCombineFileMd5.setContent(it)
            }

        }

    }

}