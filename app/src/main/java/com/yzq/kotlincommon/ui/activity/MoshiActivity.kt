package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import com.therouter.router.Route
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.core.extend.setOnThrottleTimeClick
import com.yzq.kotlincommon.databinding.ActivityMoshiBinding
import com.yzq.kotlincommon.view_model.MoshiViewModel
import com.yzq.router.RoutePath
import com.yzq.util.ext.initToolbar

@Route(path = RoutePath.Main.MOSHI)
class MoshiActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMoshiBinding::inflate)
    private val vm: MoshiViewModel by viewModels()

    override fun observeViewModel() {
    }

    override fun initWidget() {

        binding.run {

            initToolbar(toolbar = includedToolbar.toolbar, "Moshi")
            btnSerialize.setOnThrottleTimeClick {
                vm.serialize()
            }

            btnDeserialize.setOnThrottleTimeClick {
                vm.deserialize()
            }

            btnApi.setOnThrottleTimeClick {
                vm.requestData()
            }
        }
    }
}
