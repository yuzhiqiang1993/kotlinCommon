package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.observeUIState
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBsDiffBinding
import com.yzq.kotlincommon.view_model.BsDiffViewModel

/**
 * @description: bsdiff 增量更新示例
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/3
 * @time : 8:04 下午
 */

@Route(path = RoutePath.Main.BS_DIFF)
class BsDiffActivity : BaseActivity() {

    private val binding by viewbind(ActivityBsDiffBinding::inflate)
    private val vm: BsDiffViewModel by viewModels()
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
        observeUIState(vm, loadingDialog)
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
