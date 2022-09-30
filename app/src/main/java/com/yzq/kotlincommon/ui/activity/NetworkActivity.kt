package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityNetworkBinding
import com.yzq.kotlincommon.mvvm.view_model.NetworkViewModel
import com.yzq.lib_base.ui.activity.BaseVbVmActivity

@Route(path = RoutePath.Main.NETWORK)
class NetworkActivity : BaseVbVmActivity<ActivityNetworkBinding, NetworkViewModel>() {
    override fun getViewBinding() = ActivityNetworkBinding.inflate(layoutInflater)

    override fun initWidget() {


    }

    override fun initData() {


    }


    override fun getViewModelClass() = NetworkViewModel::class.java

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        with(vm) {
            networkStatusLiveData.observe(this@NetworkActivity) {
                binding.tvNetworkStatus.text = "${it.code}--${it.desc}"
            }
        }
    }
}