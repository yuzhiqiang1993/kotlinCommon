package com.yzq.kotlincommon.ui.activity

import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBindingDelegateBinding
import com.yzq.dialog.showBaseDialog

@Route(path = RoutePath.Main.VIEW_BINDING_DELEGATE)
class BindingDelegateActivity : BaseActivity() {

    private val binding by viewbind(ActivityBindingDelegateBinding::inflate)
    override fun initWidget() {
        binding.run {
            initToolbar(includedToolbar.toolbar, "binding 委托")

            btnTest.setOnClickListener {
                showBaseDialog("提示", "binding生效了。。。")
            }
        }
    }

}
