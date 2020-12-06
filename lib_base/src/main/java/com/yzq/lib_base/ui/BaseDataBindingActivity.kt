package com.yzq.lib_base.ui

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * @description:基于DataBinding封装的基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 18:28
 */

abstract class BaseDataBindingActivity<DB : ViewDataBinding> : BaseActivity() {


    protected lateinit var binding: DB

    override fun initContentView() {
        binding = DataBindingUtil.setContentView<DB>(this, getContentLayoutId())
    }

    abstract fun getContentLayoutId(): Int

}