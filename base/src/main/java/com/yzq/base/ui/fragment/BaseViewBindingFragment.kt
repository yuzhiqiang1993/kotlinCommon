package com.yzq.base.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding


/**
 * @description: 基于ViewBinding的Fragment基类
 * @author : XeonYu
 * @date   : 2020/12/6
 * @time   : 22:12
 */

abstract class BaseViewBindingFragment<VB : ViewBinding>(@LayoutRes contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {

    private var _binding: VB? = null
    protected lateinit var binding: VB

    abstract val bindingBind: (View) -> VB

    override fun initBinding(view: View) {
        binding = bindingBind(view)
        _binding = binding
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}