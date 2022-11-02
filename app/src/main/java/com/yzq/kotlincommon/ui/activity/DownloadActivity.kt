package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityDownloadBinding
import com.yzq.kotlincommon.view_model.DownloadViewModel
import com.yzq.permission.getPermissions

@Route(path = RoutePath.Main.DOWNLOAD)
class DownloadActivity : BaseVmActivity<ActivityDownloadBinding, DownloadViewModel>() {

    override fun createBinding() = ActivityDownloadBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<DownloadViewModel> = DownloadViewModel::class.java


    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "网络进度")

        binding.btnDownload
            .setOnClickListener {
                getPermissions(PermissionConstants.STORAGE) {

                    LogUtils.i("有以下权限:$it")
                    vm.downloadApk()
                }


            }

    }

    override fun observeViewModel() {


    }


}
