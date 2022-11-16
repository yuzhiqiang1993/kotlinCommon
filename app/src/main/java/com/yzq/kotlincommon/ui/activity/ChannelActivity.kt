package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityChannelBinding
import com.yzq.kotlincommon.view_model.ChannelViewModel

/**
 * @description: Kotlin flow channel
 * @author : yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date : 2022/4/5
 * @time : 20:26
 */

@Route(path = RoutePath.Main.CHANNEL)
class ChannelActivity : BaseVmActivity<ChannelViewModel>() {

    private val binding by viewbind(ActivityChannelBinding::inflate)

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
