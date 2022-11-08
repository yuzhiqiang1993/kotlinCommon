package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityMoshiBinding
import com.yzq.kotlincommon.view_model.MoshiViewModel

@Route(path = RoutePath.Main.MOSHI)
class MoshiActivity : BaseVmActivity<MoshiViewModel>() {

    private val binding by viewbind(ActivityMoshiBinding::inflate)

    override fun getViewModelClass() = MoshiViewModel::class.java

    override fun observeViewModel() {
    }

    override fun initWidget() {

        binding.run {

            initToolbar(toolbar = toolbar.toolbar, "Moshi")
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