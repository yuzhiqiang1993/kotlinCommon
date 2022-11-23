package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
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
class ChannelActivity : BaseActivity() {

    private val binding by viewbind(ActivityChannelBinding::inflate)

    private val vm: ChannelViewModel by viewModels()

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
}
