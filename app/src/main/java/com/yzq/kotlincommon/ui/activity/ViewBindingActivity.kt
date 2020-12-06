package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityViewBindingBinding
import com.yzq.lib_base.ui.BaseActivity
import com.yzq.lib_widget.databinding.ToolbarBinding


/**
 * @description: ViewBinding 示例
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 12:51
 */


@Route(path = RoutePath.Main.VIEW_BINDING)
class ViewBindingActivity : BaseActivity() {

    private lateinit var binding: ActivityViewBindingBinding

    private lateinit var toolbarBinding: ToolbarBinding


    override fun initContentView() {
        binding = ActivityViewBindingBinding.inflate(layoutInflater)
        toolbarBinding = binding.layoutToolbar
        setContentView(binding.root)

    }


    override fun initWidget() {

        initToolbar(binding.layoutToolbar.toolbar, "ViewBinding")

        binding.btn.setOnClickListener {

            ToastUtils.showShort("点击了按钮")
        }
    }
}