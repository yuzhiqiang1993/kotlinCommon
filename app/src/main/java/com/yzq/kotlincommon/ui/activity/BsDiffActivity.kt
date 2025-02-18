package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.core.observeUIState
import com.yzq.dialog.BubbleLoadingDialog
import com.yzq.kotlincommon.databinding.ActivityBsDiffBinding
import com.yzq.kotlincommon.view_model.BsDiffViewModel
import com.yzq.logger.Logger
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

/**
 * @description: bsdiff 增量更新示例
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2021/11/3
 * @time : 8:04 下午
 */

@Route(path = RoutePath.Main.BS_DIFF)
class BsDiffActivity : BaseActivity() {

    private val binding by viewBinding(ActivityBsDiffBinding::inflate)
    private val vm: BsDiffViewModel by viewModels()
    private val loadingDialog by lazy { BubbleLoadingDialog(this) }
    override fun initWidget() {

        binding.apply {

            initToolbar(toolbar = layoutToolbar.toolbar, "BsDiff")


            btnGetApk.setOnThrottleTimeClick {
                getApk()

            }

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


    /**
     * Get apk
     *
     */
    private fun getApk() {

//        getPermissions(Permission.REQUEST_INSTALL_PACKAGES)
//        val appPath = AppUtils.getAppPath()
//        Logger.i("appPath:${appPath}")


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            packageManager.getApplicationInfo(packageName, ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
//        }else{
        val applicationInfo = packageManager.getApplicationInfo(packageName, 0)

//        }
        Logger.i("application:${applicationInfo}")
        val apkPath = applicationInfo.sourceDir//这个路径就是apk的全路径
        Logger.i("apkPath = ${apkPath}")

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
