package com.yzq.kotlincommon.ui.activity

import android.Manifest
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.databinding.ActivityNetworkBinding
import com.yzq.network_status.NetworkStatus
import com.yzq.network_status.NetworkType
import com.yzq.network_status.OnNetworkStatusChangedListener
import com.yzq.permission.getPermissions
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

@Route(path = RoutePath.Main.NETWORK)
class NetworkActivity : BaseActivity(), OnNetworkStatusChangedListener {

    private val binding by viewBinding(ActivityNetworkBinding::inflate)

    override fun initListener() {
        NetworkStatus.registerNetworkStatusChangedListener(this, this)
    }

    override fun initWidget() {
        initToolbar(binding.toolbar.toolbar, "网络状态")
    }

    override fun initData() {
        getPermissions(Manifest.permission.READ_PHONE_STATE) {
            val networkType = NetworkStatus.getNetworkType()
            binding.tvNetworkStatus.text = buildString {
                append(networkType.code)
                append("--")
                append(networkType.desc)
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
