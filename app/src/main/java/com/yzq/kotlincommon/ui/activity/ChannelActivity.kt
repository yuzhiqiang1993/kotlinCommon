package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityChannelBinding
import com.yzq.kotlincommon.mvvm.view_model.ChannelViewModel
import com.yzq.lib_base.extend.setOnThrottleTimeClick
import com.yzq.lib_base.ui.activity.BaseVmActivity


/**
 * @description: Kotlin flow channel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date   : 2022/4/5
 * @time   : 20:26
 */

@Route(path = RoutePath.Main.CHANNEL)
class ChannelActivity : BaseVmActivity<ActivityChannelBinding, ChannelViewModel>() {


    override fun createBinding() = ActivityChannelBinding.inflate(layoutInflater)

    override fun getViewModelClass() = ChannelViewModel::class.java

    override fun observeViewModel() {
    }


    override fun initWidget() {


        binding.run {

            initToolbar(toolbar = toolbar.toolbar, "Channel")
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