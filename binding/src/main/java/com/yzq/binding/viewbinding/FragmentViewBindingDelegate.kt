package com.yzq.binding.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yzq.binding.base.FragmentBindingDelegate
import kotlin.reflect.KProperty

/**
 * @description Fragment 的 viewbinding 代理
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/7
 * @time 17:56
 */

class FragmentViewBindingDelegate<VB : ViewBinding>(
    fragment: Fragment,
    val bind: (View) -> VB
) : FragmentBindingDelegate<VB>(fragment) {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        /*如果binding有值，直接返回*/
        binding?.let { return it }
        val viewBinding = bind.invoke(thisRef.requireView())
        binding = viewBinding
        return binding as VB
    }
}
