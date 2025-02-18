package com.yzq.kotlincommon.ui.activity

import com.hjq.toast.Toaster
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.baseui.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.kotlincommon.databinding.ActivityBindingDelegateBinding
import com.yzq.router.RoutePath

@Route(path = RoutePath.Main.VIEW_BINDING_DELEGATE)
class BindingDelegateActivity : BaseActivity() {

    private val binding by viewBinding(ActivityBindingDelegateBinding::inflate)
    override fun initWidget() {
        binding.run {
            initToolbar(includedToolbar.toolbar, "binding 委托")

            btnTest.setOnClickListener {
                Toaster.showLong("binding生效了")
            }
        }
    }

}
