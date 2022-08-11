package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityMoshiBinding
import com.yzq.kotlincommon.mvvm.view_model.MoshiViewModel
import com.yzq.lib_base.extend.setOnThrottleTimeClick
import com.yzq.lib_base.ui.activity.BaseVbVmActivity

@Route(path = RoutePath.Main.MOSHI)
class MoshiActivity : BaseVbVmActivity<ActivityMoshiBinding, MoshiViewModel>() {

    override fun getViewModelClass() = MoshiViewModel::class.java

    override fun observeViewModel() {
    }

    override fun initWidget() {

        with(binding) {

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

    override fun getViewBinding() = ActivityMoshiBinding.inflate(layoutInflater)
}