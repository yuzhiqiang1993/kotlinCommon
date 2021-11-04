package com.yzq.lib_base.ui.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * @description:基于DataBinding封装的基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:28
 */

abstract class BaseDataBindingActivity<VDB : ViewDataBinding> : BaseActivity() {


    protected lateinit var binding: VDB

    override fun initContentView() {
        binding = DataBindingUtil.setContentView(this, getContentLayoutId())
    }

    abstract fun getContentLayoutId(): Int

}