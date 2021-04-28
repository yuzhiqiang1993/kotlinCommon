package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityDownloadBinding
import com.yzq.kotlincommon.mvvm.view_model.DownloadViewModel
import com.yzq.lib_base.ui.activity.BaseVbVmActivity
import com.yzq.lib_permission.getPermissions

@Route(path = RoutePath.Main.DOWNLOAD)
class DownloadActivity : BaseVbVmActivity<ActivityDownloadBinding, DownloadViewModel>() {

    override fun getViewBinding() = ActivityDownloadBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<DownloadViewModel> = DownloadViewModel::class.java


    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "网络进度")

        binding.btnDownload
            .setOnClickListener {
                getPermissions(PermissionConstants.STORAGE) {

                    LogUtils.i("有以下权限:${it}")
                    vm.downloadApk()
                }


            }

    }

    override fun observeViewModel() {


    }


}
