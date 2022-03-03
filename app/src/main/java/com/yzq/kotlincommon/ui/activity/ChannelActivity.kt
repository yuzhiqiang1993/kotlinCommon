package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityChannelBinding
import com.yzq.kotlincommon.mvvm.view_model.ChannelViewModel
import com.yzq.lib_base.extend.setOnThrottleTimeClick
import com.yzq.lib_base.ui.activity.BaseVbVmActivity

@Route(path = RoutePath.Main.CHANNEL)
class ChannelActivity : BaseVbVmActivity<ActivityChannelBinding, ChannelViewModel>() {

    override fun getViewModelClass() = ChannelViewModel::class.java

    override fun observeViewModel() {
    }

    override fun getViewBinding(): ActivityChannelBinding = ActivityChannelBinding.inflate(layoutInflater)

    override fun initWidget() {

        with(binding) {
            btnTestChannel.setOnThrottleTimeClick {
                vm.testChannel()
            }
            btnTestChannelUnlimited.setOnThrottleTimeClick {
                vm.testChannelUnlimited()
            }
        }

    }

    override fun initData() {

    }
}