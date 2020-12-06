package com.yzq.lib_base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


/**
 * @description: 基于ViewBinding的Fragment基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 22:12
 */

abstract class BaseViewBindingFragment<VB : ViewBinding> : BaseFragment() {

    protected lateinit var binding: VB

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB


    override fun initRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }


}