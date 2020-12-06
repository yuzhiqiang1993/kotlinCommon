package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.mvvm.view_model.DownloadViewModel
import com.yzq.lib_base.ui.BaseMvvmActivity
import com.yzq.lib_permission.getPermissions
import kotlinx.android.synthetic.main.activity_download.*

@Route(path = RoutePath.Main.DOWNLOAD)
class DownloadActivity : BaseMvvmActivity<DownloadViewModel>() {

    override fun initContentView() {
        setContentView(R.layout.activity_download)
    }

    override fun getViewModelClass(): Class<DownloadViewModel> = DownloadViewModel::class.java


    override fun initWidget() {


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "网络进度")

        getPermissions(PermissionConstants.STORAGE) {

            LogUtils.i("有以下权限:${it}")
        }

        btn_download.setOnClickListener {

            vm.downloadApk()

        }

    }

    override fun observeViewModel() {


    }


}
