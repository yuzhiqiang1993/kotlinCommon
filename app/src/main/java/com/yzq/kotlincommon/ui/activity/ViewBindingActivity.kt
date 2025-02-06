package com.yzq.kotlincommon.ui.activity

import com.hjq.toast.Toaster
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewBinding
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityViewBindingBinding

/**
 * @description: ViewBinding 示例
 * @author : XeonYu
 * @date : 2020/12/6
 * @time : 12:51
 */

@Route(path = RoutePath.Main.VIEW_BINDING)
class ViewBindingActivity : BaseActivity() {

    private val binding by viewBinding(ActivityViewBindingBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "ViewBinding")

        binding.btnClick.setOnClickListener {

            Toaster.showShort("点击了按钮")
        }
    }
}
