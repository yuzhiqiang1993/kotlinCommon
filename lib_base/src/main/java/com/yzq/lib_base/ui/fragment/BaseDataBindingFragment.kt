package com.yzq.lib_base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * @description: 基于DataBinding的Fragment
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 22:12
 */

abstract class BaseDataBindingFragment<DB : ViewDataBinding> : BaseFragment() {

    protected lateinit var binding: DB

    override fun initRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }


    abstract fun getContentLayoutId(): Int

}