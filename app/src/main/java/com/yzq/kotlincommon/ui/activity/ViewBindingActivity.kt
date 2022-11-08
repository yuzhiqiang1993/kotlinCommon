package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityViewBindingBinding


/**
 * @description: ViewBinding 示例
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 12:51
 */


@Route(path = RoutePath.Main.VIEW_BINDING)
class ViewBindingActivity : BaseActivity() {

    private val binding by viewbind(ActivityViewBindingBinding::inflate)

    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "ViewBinding")

        binding.btnClick.setOnClickListener {

            ToastUtils.showShort("点击了按钮")
        }
    }


}