package com.yzq.kotlincommon.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityNetworkBinding
import com.yzq.network_status.NetworkType
import com.yzq.network_status.NetworkUtil
import com.yzq.network_status.OnNetworkStatusChangedListener
import com.yzq.permission.getPermissions

@Route(path = RoutePath.Main.NETWORK)
class NetworkActivity : BaseActivity(), OnNetworkStatusChangedListener {

    private val binding by viewbind(ActivityNetworkBinding::inflate)

    override fun initListener() {
        NetworkUtil.registerNetworkStatusChangedListener(this, this)
    }

    override fun initWidget() {
        initToolbar(binding.toolbar.toolbar, "网络状态")
    }

    override fun initData() {

        getPermissions(Manifest.permission.READ_PHONE_STATE) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val networkType = NetworkUtil.getNetworkType()
                binding.tvNetworkStatus.text = buildString {
                    append(networkType.code)
                    append("--")
                    append(networkType.desc)
                }
            }
        }
    }

    override fun onConnect(networkType: NetworkType) {
        binding.tvNetworkStatus.text = buildString {
            append(networkType.code)
            append("--")
            append(networkType.desc)
        }
    }

    override fun onDisconnected(networkType: NetworkType) {
        binding.tvNetworkStatus.text = buildString {
            append(networkType.code)
            append("--")
            append(networkType.desc)
        }
    }
}
