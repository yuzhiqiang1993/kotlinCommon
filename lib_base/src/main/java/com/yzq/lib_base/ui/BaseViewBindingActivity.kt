package com.yzq.lib_base.ui

import androidx.viewbinding.ViewBinding


/**
 * @description: 基于ViewBinding的基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:29
 */

abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseActivity() {


    protected lateinit var binding: VB

    override fun initContentView() {
        binding = getViewBinding()

        setContentView(binding.root)
    }


    abstract fun getViewBinding(): VB


}