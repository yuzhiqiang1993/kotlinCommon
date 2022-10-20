package com.yzq.kotlincommon.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityNetworkBinding
import com.yzq.kotlincommon.mvvm.view_model.NetworkViewModel
import com.yzq.lib_base.ui.activity.BaseVmActivity
import com.yzq.lib_network_status.NetworkUtil
import com.yzq.lib_permission.getPermissions

@Route(path = RoutePath.Main.NETWORK)
class NetworkActivity : BaseVmActivity<ActivityNetworkBinding, NetworkViewModel>() {
    override fun createBinding() = ActivityNetworkBinding.inflate(layoutInflater)

    override fun initWidget() {

        initToolbar(binding.toolbar.toolbar, "网络状态", showBackHint = true)
    }


    @SuppressLint("SetTextI18n")
    override fun initData() {
        getPermissions(Manifest.permission.READ_PHONE_STATE) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val networkType = NetworkUtil.getNetworkType()
                binding.tvNetworkStatus.text = "${networkType.code}--${networkType.desc}"
            }

        }


    }


    override fun getViewModelClass() = NetworkViewModel::class.java

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        vm.run {
            networkStatusLiveData.observe(this@NetworkActivity) {
                binding.tvNetworkStatus.text = "${it.code}--${it.desc}"
            }
        }
    }
}